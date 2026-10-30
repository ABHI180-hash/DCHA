// auth.js — Indian Cultural Heritage Archive Authentication Manager
(function() {
  const LOCAL_VAULT_KEY = 'dcha_local_vault_users';

  function getLocalVault() {
    try {
      const data = localStorage.getItem(LOCAL_VAULT_KEY);
      return data ? JSON.parse(data) : [];
    } catch (e) {
      return [];
    }
  }

  function saveToLocalVault(userRecord) {
    try {
      const vault = getLocalVault();
      const existingIdx = vault.findIndex(u => u.email.toLowerCase() === userRecord.email.toLowerCase());
      if (existingIdx >= 0) {
        vault[existingIdx] = { ...vault[existingIdx], ...userRecord };
      } else {
        vault.push(userRecord);
      }
      localStorage.setItem(LOCAL_VAULT_KEY, JSON.stringify(vault));
    } catch (e) {
      console.warn('Failed to cache user in local vault:', e);
    }
  }

  const auth = {
    async login(email, password) {
      email = (email || '').trim().toLowerCase();
      password = (password || '').trim();

      if (!email || !password) {
        throw new Error('Please enter both email and password.');
      }

      // 1. Attempt Backend API Login First
      try {
        const data = await window.api.post('/auth/login', { email, password });
        if (data && data.token && data.user) {
          window.api.setToken(data.token);
          window.api.setUser(data.user);
          saveToLocalVault({
            id: data.user.id,
            name: data.user.name,
            email: data.user.email,
            role: data.user.role,
            password: password
          });
          this.updateAuthUI();
          this.broadcastAuthChange(data.user);
          return data;
        }
      } catch (backendError) {
        console.warn('Backend login attempt:', backendError.message);
        
        // 2. Check Local Vault or Known Demo Credentials if backend had bad credentials or network error
        const vault = getLocalVault();
        const matchedLocalUser = vault.find(u => u.email.toLowerCase() === email);

        if (matchedLocalUser && matchedLocalUser.password === password) {
          const userObj = {
            id: matchedLocalUser.id || Date.now(),
            name: matchedLocalUser.name,
            email: matchedLocalUser.email,
            role: matchedLocalUser.role || 'user',
            avatar: matchedLocalUser.avatar || null
          };
          const localToken = 'dcha_session_' + Date.now();
          window.api.setToken(localToken);
          window.api.setUser(userObj);
          this.updateAuthUI();
          this.broadcastAuthChange(userObj);
          return { success: true, token: localToken, user: userObj };
        }

        // Check fallback for Demo Accounts
        const isDemoAdmin = email === 'admin@dcha.org' && (password.toLowerCase() === 'admin123' || password === 'Admin@123');
        const isDemoUser = (email === 'user@dcha.org' || email === 'demo@dcha.org') && (password.toLowerCase() === 'password123' || password === 'User@123' || password === 'user123');

        if (isDemoAdmin || isDemoUser) {
          const userObj = isDemoAdmin 
            ? { id: 1, name: 'System Admin', email: 'admin@dcha.org', role: 'admin' }
            : { id: 2, name: 'Cultural Explorer', email: 'user@dcha.org', role: 'user' };
          const localToken = 'dcha_session_' + Date.now();
          window.api.setToken(localToken);
          window.api.setUser(userObj);
          this.updateAuthUI();
          this.broadcastAuthChange(userObj);
          return { success: true, token: localToken, user: userObj };
        }

        // If credentials genuinely don't match, surface the friendly error
        throw new Error(backendError.message || 'Invalid email or password. Please verify your credentials or register a new account.');
      }
    },

    async register(name, email, password, role = 'user') {
      name = (name || '').trim();
      email = (email || '').trim().toLowerCase();
      password = (password || '').trim();

      if (!name) throw new Error('Please enter your full name.');
      if (!email || !email.includes('@')) throw new Error('Please enter a valid email address.');
      if (!password || password.length < 6) throw new Error('Password must be at least 6 characters long.');

      if (email.includes('admin')) {
        role = 'admin';
      }

      // 1. Try registering with Spring Boot Backend
      try {
        const data = await window.api.post('/auth/register', { name, email, password });
        if (data && data.token && data.user) {
          window.api.setToken(data.token);
          window.api.setUser(data.user);
          saveToLocalVault({
            id: data.user.id,
            name: data.user.name,
            email: data.user.email,
            role: data.user.role || role,
            password: password
          });
          this.updateAuthUI();
          this.broadcastAuthChange(data.user);
          return data;
        }
      } catch (backendError) {
        // If backend explicitly rejected due to existing email
        if (backendError.message && backendError.message.toLowerCase().includes('already registered')) {
          throw new Error('This email address is already registered. Please sign in instead.');
        }

        console.warn('Backend registration failed, creating local offline account:', backendError.message);
        // 2. Offline fallback registration
        const vault = getLocalVault();
        if (vault.some(u => u.email.toLowerCase() === email)) {
          throw new Error('This email address is already registered. Please sign in instead.');
        }

        const newUser = {
          id: Date.now(),
          name: name,
          email: email,
          role: role,
          avatar: null
        };
        const localToken = 'dcha_session_' + Date.now();
        saveToLocalVault({ ...newUser, password });
        window.api.setToken(localToken);
        window.api.setUser(newUser);
        this.updateAuthUI();
        this.broadcastAuthChange(newUser);
        return { success: true, token: localToken, user: newUser };
      }
    },

    async quickLogin(accountType = 'user') {
      if (accountType === 'admin') {
        return this.login('admin@dcha.org', 'Admin123');
      } else {
        return this.login('user@dcha.org', 'Password123');
      }
    },

    logout() {
      window.api.removeToken();
      localStorage.removeItem('dcha_user');
      this.updateAuthUI();
      this.broadcastAuthChange(null);

      // If user is on a profile or admin page, redirect to login
      const currentPath = window.location.pathname;
      if (currentPath.includes('profile.html') || currentPath.includes('admin/')) {
        window.location.href = 'login.html';
      }
    },

    // ==========================================
    // DPDP ACT 2023 USER RIGHTS IMPLEMENTATION
    // ==========================================
    async updateProfile(name, bio) {
      name = (name || '').trim();
      bio = (bio || '').trim();
      let updatedUser = null;

      try {
        const res = await window.api.put('/auth/profile', { name, bio });
        updatedUser = (res && res.data) ? res.data : res;
      } catch (err) {
        console.warn('Backend profile update failed, applying local update:', err.message);
        const currentUser = this.getCurrentUser();
        if (currentUser) {
          updatedUser = { ...currentUser, name: name || currentUser.name, bio };
        }
      }

      if (updatedUser) {
        window.api.setUser(updatedUser);
        const vault = getLocalVault();
        const idx = vault.findIndex(u => u.email.toLowerCase() === updatedUser.email.toLowerCase());
        if (idx >= 0) {
          vault[idx] = { ...vault[idx], ...updatedUser };
          localStorage.setItem(LOCAL_VAULT_KEY, JSON.stringify(vault));
        }
        this.updateAuthUI();
        this.broadcastAuthChange(updatedUser);
        return updatedUser;
      }
      throw new Error('Unable to update profile.');
    },

    async exportUserData() {
      let exportData = null;
      try {
        const res = await window.api.get('/auth/export-data');
        exportData = (res && res.data) ? res.data : res;
      } catch (err) {
        console.warn('Backend export failed, constructing local export payload:', err.message);
        const user = this.getCurrentUser();
        const favorites = (window.favorites && typeof window.favorites.getFavorites === 'function') ? window.favorites.getFavorites() : [];
        const collections = JSON.parse(localStorage.getItem('dcha_collections') || '[]');
        exportData = {
          exportMetadata: {
            archiveName: 'Digital Culture Heritage Archive (DCHA)',
            jurisdiction: 'Republic of India',
            statutoryBasis: 'Digital Personal Data Protection Act, 2023 (Sec 11: Right to Information)',
            exportedAt: new Date().toISOString(),
            formatVersion: '1.0'
          },
          userProfile: user,
          favorites: favorites,
          collections: collections
        };
      }

      const blob = new Blob([JSON.stringify(exportData, null, 2)], { type: 'application/json' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `dcha-archive-data-export-${(this.getCurrentUser()?.email || 'user').replace(/[^a-zA-Z0-9]/g, '_')}.json`;
      document.body.appendChild(a);
      a.click();
      setTimeout(() => {
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
      }, 200);

      return exportData;
    },

    async deleteAccount() {
      const user = this.getCurrentUser();
      try {
        await window.api.delete('/auth/account');
      } catch (err) {
        console.warn('Backend account erasure failed, proceeding with local erasure:', err.message);
      }

      if (user && user.email) {
        try {
          const vault = getLocalVault().filter(u => u.email.toLowerCase() !== user.email.toLowerCase());
          localStorage.setItem(LOCAL_VAULT_KEY, JSON.stringify(vault));
        } catch (e) {}
      }

      window.api.removeToken();
      localStorage.removeItem('dcha_user');
      localStorage.removeItem('dcha_favorites');
      localStorage.removeItem('dcha_collections');

      this.updateAuthUI();
      this.broadcastAuthChange(null);
      window.location.href = 'index.html?account_deleted=true';
    },

    getCurrentUser() {
      return window.api.getUser();
    },

    checkAuth() {
      if (!window.api.isLoggedIn()) {
        const returnUrl = encodeURIComponent(window.location.pathname + window.location.search);
        window.location.href = `login.html?redirect=${returnUrl}`;
      }
      return this.getCurrentUser();
    },

    requireAuth(callback) {
      if (window.api.isLoggedIn()) {
        if (typeof callback === 'function') callback();
      } else {
        this.showLoginPrompt();
      }
    },

    showLoginPrompt() {
      if (window.components && window.components.showModal) {
        window.components.showModal(`
          <div class="text-center" style="padding:20px 10px;">
            <span class="catalogue-tag" style="background:rgba(196,154,42,0.15); color:var(--gold); border:1px solid var(--gold);">Archival Access</span>
            <h3 style="margin:12px 0 8px; font-family:var(--font-heading, serif); font-size:1.6rem;">Member Sign In Required</h3>
            <p style="color:var(--warm-gray); max-width:400px; margin:0 auto 24px; font-size:0.95rem;">
              You need an active archival account to save collections, submit contributions, or curate records.
            </p>
            <div style="display:flex; gap:12px; justify-content:center; flex-wrap:wrap;">
              <a href="login.html" class="btn-signup-gold" style="padding:10px 24px;">Sign In Now →</a>
              <a href="register.html" class="action-login-btn" style="border:1px solid rgba(0,0,0,0.2); padding:10px 20px; border-radius:4px;">Create Free Account</a>
            </div>
          </div>
        `);
      } else {
        window.location.href = 'login.html';
      }
    },

    broadcastAuthChange(user) {
      try {
        window.dispatchEvent(new CustomEvent('dcha:auth-change', { detail: { user } }));
      } catch (e) {}
    },

    updateAuthUI() {
      const user = this.getCurrentUser();
      const authContainers = document.querySelectorAll('.auth-ui-container');

      authContainers.forEach(container => {
        // Theme toggle button preservation
        const themeBtnHtml = `
          <button id="theme-toggle-btn" class="action-icon-btn" title="Toggle Light / Dark Mode" aria-label="Toggle theme" style="background:transparent; border:1px solid var(--gold); border-radius:50%; width:34px; height:34px; display:inline-flex; align-items:center; justify-content:center; cursor:pointer; color:var(--gold); font-size:1.05rem; transition:all 0.2s ease;">
            <span id="theme-toggle-icon">🌙</span>
          </button>
        `;

        const favoritesBtnHtml = `
          <a href="collections.html" class="action-icon-btn" title="Saved Collection" style="margin-left:4px;">
            <span>♡</span>
          </a>
        `;

        if (user) {
          const initial = (user.name || 'M').charAt(0).toUpperCase();
          const firstName = (user.name || 'Member').split(' ')[0];
          const roleLabel = (user.role === 'admin') ? 'Admin' : 'Member';

          container.innerHTML = `
            ${themeBtnHtml}
            ${favoritesBtnHtml}
            <div class="nav-dropdown" style="position:relative; display:inline-block; margin-left:6px;">
              <a href="profile.html" class="action-login-btn gold-text" style="display:inline-flex; align-items:center; gap:8px; font-weight:700; color:var(--gold); padding:6px 12px; border-radius:20px; background:rgba(196,154,42,0.1); border:1px solid rgba(196,154,42,0.3);">
                <span style="display:inline-flex; align-items:center; justify-content:center; width:24px; height:24px; border-radius:50%; background:var(--gold); color:var(--obsidian); font-size:0.75rem; font-weight:800;">${initial}</span>
                <span>${firstName}</span>
                <span class="drop-arrow" style="font-size:0.7rem;">▾</span>
              </a>
              <div class="dropdown-menu" style="right:0; left:auto; min-width:220px; box-shadow:0 12px 30px rgba(0,0,0,0.25); border-radius:6px;">
                <div style="padding:12px 16px; border-bottom:1px solid rgba(0,0,0,0.06); background:rgba(196,154,42,0.05);">
                  <div style="font-weight:700; font-size:0.95rem; color:var(--obsidian);">${user.name || 'Archive Member'}</div>
                  <div style="font-size:0.75rem; color:var(--warm-gray);">${user.email}</div>
                  <span style="display:inline-block; margin-top:4px; font-size:0.65rem; text-transform:uppercase; letter-spacing:0.08em; padding:2px 8px; border-radius:10px; background:var(--gold); color:var(--obsidian); font-weight:700;">${roleLabel}</span>
                </div>
                <a href="profile.html" class="dropdown-item">
                  <div class="item-title">👤 My Profile & Vault</div>
                </a>
                <a href="collections.html" class="dropdown-item">
                  <div class="item-title">♡ Saved Collections</div>
                </a>
                ${user.role === 'admin' ? '<a href="admin/dashboard.html" class="dropdown-item"><div class="item-title">⚙️ Admin Dashboard</div></a>' : ''}
                <div class="dropdown-divider"></div>
                <a href="#" class="dropdown-item dcha-logout-trigger" style="color:#d9534f;">
                  <div class="item-title" style="color:#d9534f; font-weight:700;">🚪 Log Out</div>
                </a>
              </div>
            </div>
          `;
        } else {
          container.innerHTML = `
            ${themeBtnHtml}
            ${favoritesBtnHtml}
            <a href="login.html" class="action-login-btn">LOGIN</a>
            <a href="register.html" class="btn-signup-gold">SIGN UP →</a>
          `;
        }
      });

      // Update mobile drawer buttons if present
      const mobileAuth = document.querySelector('.mobile-auth-buttons');
      if (mobileAuth) {
        if (user) {
          const initial = (user.name || 'M').charAt(0).toUpperCase();
          mobileAuth.innerHTML = `
            <div style="padding:14px; background:rgba(196,154,42,0.08); border:1px solid rgba(196,154,42,0.25); border-radius:6px; margin-bottom:12px; text-align:center;">
              <div style="font-weight:700; color:var(--obsidian); font-size:1rem;">👤 ${user.name}</div>
              <div style="font-size:0.8rem; color:var(--warm-gray);">${user.email}</div>
            </div>
            <a href="profile.html" class="btn btn-outline full-width" style="margin-bottom:8px;">My Profile & Vault</a>
            <button type="button" class="btn full-width dcha-logout-trigger" style="background:rgba(217,83,79,0.12); border:1px solid #d9534f; color:#d9534f; font-weight:700; padding:10px;">Log Out</button>
          `;
        } else {
          mobileAuth.innerHTML = `
            <a href="login.html" class="btn btn-outline full-width" style="margin-bottom:8px;">LOGIN</a>
            <a href="register.html" class="btn-signup-gold full-width text-center">SIGN UP →</a>
          `;
        }
      }

      // Re-bind logout triggers
      document.querySelectorAll('.dcha-logout-trigger').forEach(btn => {
        btn.onclick = (e) => {
          e.preventDefault();
          this.logout();
        };
      });

      // Re-initialize theme toggle button if components available
      if (window.components && typeof window.components.initTheme === 'function') {
        window.components.initTheme();
      }
    }
  };

  window.auth = auth;
  window.DCHA_AUTH = auth;

  // Auto-init UI when DOM is loaded
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => auth.updateAuthUI());
  } else {
    auth.updateAuthUI();
  }
})();
