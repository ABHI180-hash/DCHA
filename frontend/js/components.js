// components.js - Reusable Museum & Archive UI Components
(function() {
  const components = {
    createNav(activePage = '') {
      const navContainer = document.getElementById('nav-container');
      const existingHeader = document.querySelector('.main-header-wrapper') || document.querySelector('.main-nav');
      
      const wrapper = document.createElement('div');
      wrapper.className = 'main-header-wrapper';
      wrapper.innerHTML = `
        <a href="#main-content" class="skip-link">Skip to main content</a>
        <!-- MAIN NAVIGATION HEADER -->
        <header class="main-nav" id="main-nav">
          <div class="header-container">
            <div class="nav-inner">

              <!-- BRAND LOGO & INSTITUTIONAL TITLE -->
              <a href="index.html" class="nav-logo-group">
                <div class="logo-emblem">
                  <svg width="36" height="36" viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="50" cy="50" r="45" stroke="#C6A15B" stroke-width="2.5" stroke-dasharray="6 3"/>
                    <circle cx="50" cy="50" r="36" stroke="rgba(198,161,91,0.4)" stroke-width="1"/>
                    <path d="M50 16 L58 36 L78 36 L62 48 L68 68 L50 56 L32 68 L38 48 L22 36 L42 36 Z" fill="#C6A15B"/>
                    <circle cx="50" cy="50" r="9" fill="#F5F1E8"/>
                    <circle cx="50" cy="50" r="4" fill="#2A2118"/>
                  </svg>
                </div>
                <div class="logo-text-block">
                  <div class="logo-title-full">
                    INDIAN CULTURAL <span class="gold-text">HERITAGE ARCHIVE</span>
                  </div>
                </div>
              </a>

              <!-- CENTER NAVIGATION LINKS -->
              <nav class="nav-links" id="nav-links">
                <a href="index.html" class="nav-link ${activePage === 'home' ? 'active' : ''}">
                  HOME
                </a>

                <div class="nav-dropdown">
                  <a href="explore.html" class="nav-link ${activePage === 'explore' ? 'active' : ''}">
                    EXPLORE <span class="drop-arrow">▾</span>
                  </a>
                  <div class="dropdown-menu">
                    <a href="explore.html" class="dropdown-item">
                      <div class="item-title">Discover India</div>
                      <div class="item-sub">Overview of cultural landscapes</div>
                    </a>
                    <a href="heritage.html" class="dropdown-item">
                      <div class="item-title">Heritage Sites</div>
                      <div class="item-sub">Monuments, forts, & temples</div>
                    </a>
                    <a href="traditions.html" class="dropdown-item">
                      <div class="item-title">Cultural Traditions</div>
                      <div class="item-sub">Rituals, customs, & lore</div>
                    </a>
                    <a href="festivals.html" class="dropdown-item">
                      <div class="item-title">Festivals & Celebrations</div>
                      <div class="item-sub">Regional and national events</div>
                    </a>
                    <a href="food.html" class="dropdown-item">
                      <div class="item-title">Culinary Traditions</div>
                      <div class="item-sub">Regional cuisines & heritage recipes</div>
                    </a>
                    <a href="culture.html" class="dropdown-item">
                      <div class="item-title">Dress & Textiles</div>
                      <div class="item-sub">Weaves, costumes, & crafts</div>
                    </a>
                  </div>
                </div>

                <div class="nav-dropdown">
                  <a href="states.html" class="nav-link ${activePage === 'states' ? 'active' : ''}">
                    STATES & UTs <span class="drop-arrow">▾</span>
                  </a>
                  <div class="dropdown-menu dropdown-menu-wide">
                    <a href="states.html" class="dropdown-item">
                      <div class="item-title">All 36 States & UTs</div>
                      <div class="item-sub">Complete archive directory</div>
                    </a>
                    <div class="dropdown-divider"></div>
                    <div class="dropdown-region-grid">
                      <a href="states.html?region=North" class="dropdown-item">North India</a>
                      <a href="states.html?region=South" class="dropdown-item">South India</a>
                      <a href="states.html?region=East" class="dropdown-item">East India</a>
                      <a href="states.html?region=West" class="dropdown-item">West India</a>
                      <a href="states.html?region=Northeast" class="dropdown-item">Northeast India</a>
                      <a href="states.html?region=Central" class="dropdown-item">Central India</a>
                      <a href="states.html?region=Islands" class="dropdown-item">Islands & UTs</a>
                    </div>
                  </div>
                </div>

                <a href="heritage.html" class="nav-link ${activePage === 'heritage' ? 'active' : ''}">
                  HERITAGE
                </a>

                <div class="nav-dropdown">
                  <a href="culture.html" class="nav-link ${activePage === 'culture' ? 'active' : ''}">
                    CULTURE <span class="drop-arrow">▾</span>
                  </a>
                  <div class="dropdown-menu">
                    <a href="culture.html#dance" class="dropdown-item">
                      <div class="item-title">Performing Arts & Dance</div>
                    </a>
                    <a href="audio.html" class="dropdown-item">
                      <div class="item-title">Music & Folk Instruments</div>
                    </a>
                    <a href="culture.html#crafts" class="dropdown-item">
                      <div class="item-title">Craftsmanship & Painting</div>
                    </a>
                    <a href="stories.html" class="dropdown-item">
                      <div class="item-title">Folk Stories & Legends</div>
                    </a>
                    <a href="culture.html#languages" class="dropdown-item">
                      <div class="item-title">Languages & Scripts</div>
                    </a>
                  </div>
                </div>

                <a href="map.html" class="nav-link ${activePage === 'map' ? 'active' : ''}">MAP</a>
                <a href="timeline.html" class="nav-link ${activePage === 'timeline' ? 'active' : ''}">TIMELINE</a>

                <div class="nav-dropdown">
                  <a href="museum.html" class="nav-link ${activePage === 'museum' ? 'active' : ''}">
                    MUSEUM <span class="drop-arrow">▾</span>
                  </a>
                  <div class="dropdown-menu">
                    <a href="museum.html" class="dropdown-item">
                      <div class="item-title">Virtual Museum</div>
                      <div class="item-sub">Curated digital artifacts</div>
                    </a>
                    <a href="audio.html" class="dropdown-item">
                      <div class="item-title">Audio Archive</div>
                      <div class="item-sub">Oral histories & songs</div>
                    </a>
                    <a href="calendar.html" class="dropdown-item">
                      <div class="item-title">Cultural Calendar</div>
                      <div class="item-sub">Upcoming heritage festivals</div>
                    </a>
                    <a href="at-risk.html" class="dropdown-item">
                      <div class="item-title">Heritage at Risk</div>
                      <div class="item-sub">Endangered monuments & arts</div>
                    </a>
                    <a href="compare.html" class="dropdown-item">
                      <div class="item-title">Compare Regions</div>
                      <div class="item-sub">Side-by-side analysis</div>
                    </a>
                  </div>
                </div>
              </nav>

              <!-- SEARCH FIELD -->
              <form class="nav-search-form" action="search.html" method="GET">
                <div class="nav-search-wrapper">
                  <span class="search-icon">🔍</span>
                  <input type="text" name="q" placeholder="Search heritage, states, festivals..." class="nav-search-input" aria-label="Search archive">
                </div>
              </form>

              <!-- USER ACTIONS -->
              <div class="nav-actions-group auth-ui-container">
                <button id="theme-toggle-btn" class="action-icon-btn" title="Toggle Light / Dark Mode" aria-label="Toggle theme" style="background:transparent; border:1px solid var(--gold); border-radius:50%; width:34px; height:34px; display:flex; align-items:center; justify-content:center; cursor:pointer; color:var(--gold); font-size:1.05rem; transition:all 0.2s ease;">
                  <span id="theme-toggle-icon">🌙</span>
                </button>
                <a href="collections.html" class="action-icon-btn" title="Saved Collection">
                  <span>♡</span>
                </a>
                <a href="login.html" class="action-login-btn">LOGIN</a>
                <a href="register.html" class="btn-signup-gold">SIGN UP →</a>
              </div>

              <!-- MOBILE HAMBURGER TOGGLE -->
              <button class="mobile-toggle" id="mobile-toggle" aria-label="Toggle navigation">
                <span></span><span></span><span></span>
              </button>

            </div>
          </div>
        </header>

        <!-- MOBILE SLIDE DRAWER -->
        <div class="mobile-drawer" id="mobile-drawer">
          <div class="mobile-drawer-content">
            <form action="search.html" method="GET" class="mobile-search-form">
              <input type="text" name="q" placeholder="Search heritage, states, festivals..." class="nav-search-input" style="width:100%; margin-bottom:15px;">
            </form>
            <div class="mobile-links-list">
              <a href="index.html" class="mobile-link">HOME</a>
              <a href="explore.html" class="mobile-link">EXPLORE ARCHIVE</a>
              <a href="states.html" class="mobile-link">28 STATES & 8 UTs</a>
              <a href="heritage.html" class="mobile-link">HERITAGE SITES</a>
              <a href="culture.html" class="mobile-link">CULTURE & TRADITIONS</a>
              <a href="map.html" class="mobile-link">INTERACTIVE MAP</a>
              <a href="timeline.html" class="mobile-link">HERITAGE TIMELINE</a>
              <a href="museum.html" class="mobile-link">VIRTUAL MUSEUM</a>
              <a href="audio.html" class="mobile-link">AUDIO ARCHIVE</a>
              <a href="calendar.html" class="mobile-link">CULTURAL CALENDAR</a>
              <a href="at-risk.html" class="mobile-link">HERITAGE AT RISK</a>
              <a href="compare.html" class="mobile-link">COMPARE REGIONS</a>
              <a href="contribute.html" class="mobile-link">CONTRIBUTE</a>
            </div>
            <div class="mobile-auth-buttons">
              <a href="login.html" class="btn btn-outline full-width">Login</a>
              <a href="register.html" class="btn-signup-gold full-width text-center">SIGN UP →</a>
            </div>
          </div>
        </div>
      `;

      if (navContainer) {
        navContainer.innerHTML = '';
        navContainer.appendChild(wrapper);
      } else if (existingHeader) {
        existingHeader.replaceWith(wrapper);
      } else {
        document.body.prepend(wrapper);
      }

      this.initMobileNav();
      this.initStickyHeader();
      this.initTheme();
      this.initNavSearchAutocomplete();
      if (window.auth && typeof window.auth.updateAuthUI === 'function') {
        window.auth.updateAuthUI();
      }
    },

    initNavSearchAutocomplete() {
      const searchForms = document.querySelectorAll('.nav-search-form, .mobile-search-form');
      searchForms.forEach(form => {
        const input = form.querySelector('.nav-search-input');
        if (!input) return;

        const wrapper = form.querySelector('.nav-search-wrapper') || form;
        wrapper.style.position = 'relative';

        let dropdown = form.querySelector('.nav-search-results-dropdown');
        if (!dropdown) {
          dropdown = document.createElement('div');
          dropdown.className = 'nav-search-results-dropdown';
          dropdown.style.cssText = `
            display: none;
            position: absolute;
            top: 100%;
            left: 0;
            right: 0;
            margin-top: 6px;
            background: #FFFFFF;
            border: 1.5px solid var(--gold, #C6A15B);
            border-radius: 8px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.25);
            max-height: 380px;
            overflow-y: auto;
            z-index: 9999;
            text-align: left;
          `;
          wrapper.appendChild(dropdown);
        }

        const normalizeText = (text) => {
          if (!text) return '';
          let str = String(text).toLowerCase();
          const aliases = {
            'puduicherry': 'puducherry', 'pondicherry': 'puducherry', 'pondicherri': 'puducherry',
            'pondy': 'puducherry', 'orissa': 'odisha', 'calcutta': 'kolkata', 'bombay': 'mumbai',
            'madras': 'chennai', 'trivandrum': 'thiruvananthapuram', 'banaras': 'varanasi'
          };
          for (const [k, v] of Object.entries(aliases)) {
            if (str.includes(k)) str = str.replace(new RegExp(k, 'g'), v);
          }
          return str;
        };

        const renderDropdownResults = () => {
          const rawQ = input.value.trim();
          const normQ = normalizeText(rawQ);
          if (!normQ || normQ.length < 1) {
            dropdown.style.display = 'none';
            return;
          }

          const tokens = normQ.split(/\s+/).filter(Boolean);
          const pool = window.components.getGlobalSearchPool();

          const matches = pool.filter(item => {
            const fieldsText = normalizeText([
              item.title || item.name || '',
              item.description || '',
              item.state || '',
              item.city || '',
              item.capital || '',
              item.category || '',
              item.type || '',
              item.cultural_significance || '',
              Array.isArray(item.languages) ? item.languages.join(' ') : (item.languages || ''),
              Array.isArray(item.highlights) ? item.highlights.join(' ') : (item.highlights || '')
            ].join(' '));
            return tokens.every(token => fieldsText.includes(token));
          }).slice(0, 8);

          if (matches.length === 0) {
            dropdown.innerHTML = `
              <div style="padding:14px; text-align:center; color:#555555; font-size:0.85rem;">
                No instant matches for "${rawQ}".
                <div style="margin-top:6px;"><a href="search.html?q=${encodeURIComponent(rawQ)}" style="color:var(--gold, #C6A15B); font-weight:600; text-decoration:none;">Search full archive →</a></div>
              </div>
            `;
            dropdown.style.display = 'block';
            return;
          }

          dropdown.innerHTML = matches.map(m => {
            const targetUrl = m.targetUrl || (m.itemType === 'state' ? `state-detail.html?id=${m.id}` : `state-detail.html?id=${m.stateId || m.id}`);
            const stateClickId = m.stateId || (m.itemType === 'state' ? m.id : '');
            const onclickAttr = stateClickId ? `onclick="sessionStorage.setItem('selected_state_id', '${stateClickId}');"` : '';
            const imgUrl = m.image || m.image_url || 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=800&q=80';
            const catTag = m.category || m.type || (m.itemType === 'state' ? 'State' : 'Archive');
            const loc = m.state || m.city || m.capital || 'India';

            return `
              <a href="${targetUrl}" ${onclickAttr} style="display:flex; align-items:center; gap:12px; padding:10px 14px; text-decoration:none; border-bottom:1px solid rgba(0,0,0,0.06); transition:background 0.15s ease;" onmouseover="this.style.background='rgba(196,161,91,0.12)'" onmouseout="this.style.background='transparent'">
                <img src="${imgUrl}" alt="${m.title}" style="width:42px; height:42px; object-fit:cover; border-radius:4px; flex-shrink:0;" onerror="this.onerror=null; this.src='https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=800&q=80';">
                <div style="flex:1; min-width:0;">
                  <div style="font-size:0.88rem; font-weight:600; color:#1A1A1A; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">${m.title}</div>
                  <div style="font-size:0.75rem; color:#666666; display:flex; gap:8px;">
                    <span style="color:var(--gold, #C6A15B); font-weight:600;">${catTag}</span>
                    <span>📍 ${loc}</span>
                  </div>
                </div>
              </a>
            `;
          }).join('') + `
            <a href="search.html?q=${encodeURIComponent(rawQ)}" style="display:block; padding:10px; text-align:center; background:#1A1A1A; color:var(--gold, #C6A15B); font-size:0.82rem; font-weight:600; text-decoration:none; border-top:1px solid var(--gold, #C6A15B);">
              View all results for "${rawQ}" →
            </a>
          `;
          dropdown.style.display = 'block';
        };

        input.addEventListener('input', window.components?.debounce ? window.components.debounce(renderDropdownResults, 150) : renderDropdownResults);
        input.addEventListener('focus', renderDropdownResults);

        document.addEventListener('click', (e) => {
          if (!form.contains(e.target)) {
            dropdown.style.display = 'none';
          }
        });
      });
    },

    getGlobalSearchPool() {
      const data = window.DCHA_DATA || {};
      const stateDetails = window.STATE_DETAILS || {};
      const pool = [];
      const seenNames = new Set();

      // 1. States & UTs
      (data.STATES || []).forEach(s => {
        pool.push({
          itemType: 'state',
          id: s.id,
          slug: s.slug || s.id,
          name: s.name,
          title: s.name,
          capital: s.capital,
          region: s.region,
          type: s.type,
          description: s.description || s.cultural_significance || '',
          image: s.image,
          targetUrl: `state-detail.html?id=${encodeURIComponent(s.id)}`,
          category: s.type === 'union_territory' ? 'Union Territory' : 'State',
          state: s.name,
          stateId: s.id
        });
        seenNames.add(s.name.toLowerCase());
      });

      // 2. Index all items from STATE_DETAILS (Dresses, Foods, Museums, Tourist Places, Heritage Places, Festivals)
      Object.entries(stateDetails).forEach(([stateId, details]) => {
        const stateBase = (data.STATES || []).find(s => s.id === stateId) || { id: stateId, name: details.name || stateId };
        const stateName = stateBase.name || details.name || 'India';

        // Traditional Dresses
        (details.traditional_dresses || []).forEach(d => {
          if (d.name) {
            pool.push({
              itemType: 'textile',
              id: stateId,
              stateId: stateId,
              name: d.name,
              title: d.name,
              state: stateName,
              category: d.type || 'Traditional Dress',
              description: d.description || '',
              image: d.image,
              targetUrl: `state-detail.html?id=${encodeURIComponent(stateId)}&tab=tab-dress`
            });
            seenNames.add(d.name.toLowerCase());
          }
        });

        // Traditional Foods
        (details.traditional_foods || []).forEach(f => {
          if (f.name) {
            pool.push({
              itemType: 'food',
              id: stateId,
              stateId: stateId,
              name: f.name,
              title: f.name,
              state: stateName,
              category: f.category || 'Traditional Food',
              description: f.description || '',
              image: f.image,
              targetUrl: `state-detail.html?id=${encodeURIComponent(stateId)}&tab=tab-food`
            });
            seenNames.add(f.name.toLowerCase());
          }
        });

        // Museums
        (details.museums || []).forEach(m => {
          if (m.name) {
            pool.push({
              itemType: 'museum',
              id: stateId,
              stateId: stateId,
              name: m.name,
              title: m.name,
              state: stateName,
              city: m.city || '',
              category: 'Museum & Gallery',
              description: m.highlight || m.description || '',
              image: m.image,
              targetUrl: `state-detail.html?id=${encodeURIComponent(stateId)}&tab=tab-museums`
            });
            seenNames.add(m.name.toLowerCase());
          }
        });

        // Tourist Places
        (details.tourist_places || []).forEach(tp => {
          if (tp.name) {
            pool.push({
              itemType: 'heritage',
              id: stateId,
              stateId: stateId,
              name: tp.name,
              title: tp.name,
              state: stateName,
              category: tp.type || 'Tourist Destination',
              description: tp.description || '',
              image: tp.image,
              targetUrl: `state-detail.html?id=${encodeURIComponent(stateId)}&tab=tab-tourist`
            });
            seenNames.add(tp.name.toLowerCase());
          }
        });

        // Heritage Sites
        (details.heritage_places || []).forEach(hp => {
          if (hp.name) {
            pool.push({
              itemType: 'heritage',
              id: stateId,
              stateId: stateId,
              name: hp.name,
              title: hp.name,
              state: stateName,
              category: hp.category || 'Heritage Site',
              description: hp.description || '',
              image: hp.image,
              targetUrl: `state-detail.html?id=${encodeURIComponent(stateId)}&tab=tab-heritage`
            });
            seenNames.add(hp.name.toLowerCase());
          }
        });

        // Festivals
        (details.festivals || []).forEach(fest => {
          if (fest.name) {
            pool.push({
              itemType: 'festival',
              id: stateId,
              stateId: stateId,
              name: fest.name,
              title: fest.name,
              state: stateName,
              category: fest.category || 'Festival & Fair',
              description: fest.description || '',
              image: fest.image,
              targetUrl: `state-detail.html?id=${encodeURIComponent(stateId)}&tab=tab-festivals`
            });
            seenNames.add(fest.name.toLowerCase());
          }
        });
      });

      // 3. Add remaining items from global data collections if not already present
      const addGlobalItems = (items, defaultType, defaultPage) => {
        (items || []).forEach(item => {
          const itemName = item.name || item.title;
          if (itemName && !seenNames.has(itemName.toLowerCase())) {
            pool.push({
              ...item,
              itemType: item.itemType || defaultType,
              title: itemName,
              targetUrl: item.targetUrl || `${defaultPage}.html?id=${item.id || 1}`
            });
            seenNames.add(itemName.toLowerCase());
          }
        });
      };

      addGlobalItems(data.FEATURED_HERITAGE, 'heritage', 'heritage-detail');
      addGlobalItems(data.FOOD_ITEMS, 'food', 'food');
      addGlobalItems(data.TEXTILE_ITEMS, 'textile', 'culture');
      addGlobalItems(data.DANCE_MUSIC_ITEMS, 'dance', 'culture');
      addGlobalItems(data.ART_CRAFT_ITEMS, 'art', 'culture');
      addGlobalItems(data.LANGUAGE_ITEMS, 'language', 'culture');
      addGlobalItems(data.HERITAGE_MUSIC_ITEMS, 'music', 'culture');
      addGlobalItems(data.FOLK_INSTRUMENT_ITEMS, 'instrument', 'culture');
      addGlobalItems(data.INDIAN_MUSEUMS, 'museum', 'museum');
      addGlobalItems(data.FESTIVALS, 'festival', 'festivals');

      return pool;
    },

    createFooter() {
      const footerContainer = document.getElementById('footer-container');
      const footer = document.createElement('footer');
      footer.className = 'footer';
      footer.setAttribute('role', 'contentinfo');
      footer.innerHTML = `
        <div class="container">
          <div class="footer-grid" style="display:grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 36px;">
            <div>
              <h3 class="footer-heading" style="font-family:var(--font-brand, serif); font-size:1.2rem; letter-spacing:0.04em;">DCHA</h3>
              <p style="color: var(--muted-gray); font-size: 0.85rem; line-height: 1.7; margin-bottom:14px;">
                Digital Culture Heritage Archive is an open-access cultural humanities initiative safeguarding India's civilizational wealth across all 28 states and 8 union territories.
              </p>
              <span style="font-size:0.75rem; color:var(--gold); display:inline-block; border:1px solid rgba(198,161,91,0.3); padding:3px 10px; border-radius:12px;">
                Open Access · Non-Commercial
              </span>
            </div>
            <div>
              <h4 class="footer-heading">Explore Archives</h4>
              <ul class="footer-links">
                <li><a href="states.html">28 States & 8 UTs</a></li>
                <li><a href="heritage.html">Monuments & Sites</a></li>
                <li><a href="festivals.html">Festivals & Rituals</a></li>
                <li><a href="food.html">Culinary Traditions</a></li>
                <li><a href="culture.html">Textiles & Arts</a></li>
              </ul>
            </div>
            <div>
              <h4 class="footer-heading">Interactive Modules</h4>
              <ul class="footer-links">
                <li><a href="map.html">Interactive India Map</a></li>
                <li><a href="museum.html">Virtual Museum & Vault</a></li>
                <li><a href="timeline.html">Heritage Timeline</a></li>
                <li><a href="calendar.html">Cultural Calendar</a></li>
                <li><a href="audio.html">Audio Recordings</a></li>
              </ul>
            </div>
            <div>
              <h4 class="footer-heading">Governance & Privacy</h4>
              <ul class="footer-links">
                <li><a href="privacy.html">Privacy Policy (DPDP)</a></li>
                <li><a href="terms.html">Terms & Conditions</a></li>
                <li><a href="cookies.html">Cookie Policy</a></li>
                <li><a href="data-rights.html">Data Rights & Export</a></li>
                <li><a href="contact.html">Institutional & Grievance</a></li>
                <li><a href="accessibility.html">Accessibility Statement</a></li>
                <li><a href="attributions.html">Media Attributions</a></li>
                <li><a href="refunds.html">Service Fee Statement</a></li>
              </ul>
            </div>
          </div>
          <div class="footer-bottom" style="margin-top:40px; padding-top:20px; border-top:1px solid rgba(198,161,91,0.18); display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:16px;">
            <p style="margin:0; font-size:0.8rem; color:var(--muted-gray);">
              &copy; ${new Date().getFullYear()} Digital Culture Heritage Archive (DCHA). Licensed under CC BY-SA 4.0 International.
            </p>
            <div style="display:flex; gap:14px; flex-wrap:wrap; font-size:0.78rem;">
              <a href="privacy.html" style="color:var(--muted-gray); text-decoration:none;">Privacy</a>
              <span style="color:rgba(198,161,91,0.4);">·</span>
              <a href="terms.html" style="color:var(--muted-gray); text-decoration:none;">Terms</a>
              <span style="color:rgba(198,161,91,0.4);">·</span>
              <a href="data-rights.html" style="color:var(--muted-gray); text-decoration:none;">Data Rights</a>
              <span style="color:rgba(198,161,91,0.4);">·</span>
              <button type="button" id="footer-cookie-pref-btn" style="background:none; border:none; padding:0; color:var(--gold); font-size:0.78rem; cursor:pointer; text-decoration:underline;">
                ⚙️ Cookie Preferences
              </button>
            </div>
          </div>
        </div>
      `;
      if (footerContainer) {
        footerContainer.appendChild(footer);
      } else if (!document.querySelector('.footer')) {
        document.body.append(footer);
      }

      // Attach event listener to Cookie Preferences button
      const prefBtn = footer.querySelector('#footer-cookie-pref-btn');
      if (prefBtn) {
        prefBtn.addEventListener('click', () => {
          this.openCookiePreferences();
        });
      }
    },

    handleImageError(imgEl, title = 'Indian Heritage Record', location = 'India Archive') {
      if (!imgEl) return;
      imgEl.onerror = null;
      const safeTitle = encodeURIComponent(String(title).replace(/['"]/g, ''));
      const safeLoc = encodeURIComponent(String(location).replace(/['"]/g, ''));
      imgEl.src = `data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="800" height="500" viewBox="0 0 800 500"><rect width="100%" height="100%" fill="%23151715"/><rect x="30" y="30" width="740" height="440" fill="none" stroke="%23C6A15B" stroke-width="2" stroke-dasharray="6 3"/><text x="50%" y="42%" dominant-baseline="middle" text-anchor="middle" font-family="serif" font-size="26" font-weight="bold" fill="%23F4F0E8">${safeTitle}</text><text x="50%" y="58%" dominant-baseline="middle" text-anchor="middle" font-family="sans-serif" font-size="14" fill="%239D7B43">📍 ${safeLoc}</text></svg>`;
    },

    createCard(data, type = 'heritage') {
      const titleName = data.name || data.title || 'Indian Heritage Record';
      const categoryTag = data.category || data.type || (type === 'state' ? 'State Archive' : 'Heritage');
      const locationText = data.state || data.location || (data.capital ? `Capital: ${data.capital}` : 'India');
      const targetPage = type === 'state' ? 'state-detail' 
        : type === 'heritage' ? 'heritage-detail' 
        : type === 'museum' ? 'museum' 
        : type === 'festival' ? 'festivals' 
        : type === 'food' ? 'food' 
        : (type === 'textile' || type === 'dance' || type === 'art') ? 'culture' 
        : 'explore';
      const rawTargetId = data.id !== undefined ? data.id : (data.slug || 1);
      const targetId = typeof rawTargetId === 'number' ? rawTargetId : `'${rawTargetId}'`;
      const attribution = data.image_credit || data.source || data.source_name || 'ASI / Cultural Heritage Archive';
      const imageUrl = data.image || data.image_url || 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=800&q=80';
      
      const safeAlt = titleName.replace(/"/g, '&quot;');
      const escapedTitle = titleName.replace(/'/g, "\\'");
      const escapedLoc = locationText.replace(/'/g, "\\'");

      const isFav = window.favorites ? window.favorites.isFavorited(rawTargetId, type) : false;

      const targetUrl = data.targetUrl || `${targetPage}.html?id=${rawTargetId}`;
      const stateClickId = data.stateId || (type === 'state' ? rawTargetId : '');
      const onclickAttr = stateClickId ? `onclick="sessionStorage.setItem('selected_state_id', '${stateClickId}');"` : '';

      return `
        <div class="archival-card" data-item-id="${rawTargetId}" data-item-type="${type}">
          <div class="card-media-wrapper" style="position:relative;">
            <img src="${imageUrl}" alt="${safeAlt}" class="card-media" loading="lazy" onerror="window.components.handleImageError(this, '${escapedTitle}', '${escapedLoc}');">
            <span class="catalogue-badge">${categoryTag}</span>
            <div class="card-action-overlay" style="position:absolute; top:10px; right:10px; display:flex; gap:6px; z-index:5;">
              <button type="button" class="card-action-btn fav-btn" onclick="event.preventDefault(); event.stopPropagation(); window.favorites?.toggleFavorite('${rawTargetId}', '${type}');" title="Favorite" style="background:rgba(255,255,255,0.92); border:1px solid rgba(0,0,0,0.15); border-radius:50%; width:34px; height:34px; display:flex; align-items:center; justify-content:center; cursor:pointer; font-size:0.9rem; box-shadow:0 2px 6px rgba(0,0,0,0.2); transition:transform 0.15s ease;">
                ${isFav ? '❤️' : '🤍'}
              </button>
              <button type="button" class="card-action-btn col-btn" onclick="event.preventDefault(); event.stopPropagation(); window.components?.openAddToCollectionModal('${type}', ${targetId}, '${escapedTitle}');" title="Add to Collection" style="background:rgba(255,255,255,0.92); border:1px solid rgba(0,0,0,0.15); border-radius:50%; width:34px; height:34px; display:flex; align-items:center; justify-content:center; cursor:pointer; font-size:0.9rem; box-shadow:0 2px 6px rgba(0,0,0,0.2); transition:transform 0.15s ease;">
                📁
              </button>
            </div>
          </div>
          <div class="card-content">
            <h3><a href="${targetUrl}" ${onclickAttr}>${titleName}</a></h3>
            <div class="meta-location">📍 ${locationText}</div>
            <p class="description">${(data.description || '').substring(0, 115)}...</p>
            <div class="attribution-bar">
              <span title="${attribution}">📷 ${attribution.substring(0, 32)}</span>
              <a href="${targetUrl}" ${onclickAttr} class="view-link">View Archive →</a>
            </div>
          </div>
        </div>
      `;
    },

    showToast(message, type = 'info') {
      let container = document.querySelector('.toast-container');
      if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
      }

      const toast = document.createElement('div');
      toast.className = `toast toast-${type}`;
      toast.innerHTML = `
        <span>${message}</span>
        <button class="toast-close">&times;</button>
      `;

      container.appendChild(toast);
      toast.querySelector('.toast-close').addEventListener('click', () => toast.remove());
      setTimeout(() => { if (document.body.contains(toast)) toast.remove(); }, 4000);
    },

    showModal(content, options = {}) {
      const overlay = document.createElement('div');
      overlay.className = 'modal-overlay';
      overlay.innerHTML = `
        <div class="modal">
          <div class="modal-header">
            <h3>${options.title || ''}</h3>
            <button class="modal-close">&times;</button>
          </div>
          <div class="modal-body">${content}</div>
        </div>
      `;
      
      document.body.appendChild(overlay);
      setTimeout(() => overlay.classList.add('active'), 10);
      
      const closeFn = () => {
        overlay.classList.remove('active');
        setTimeout(() => overlay.remove(), 300);
      };
      
      overlay.querySelector('.modal-close').addEventListener('click', closeFn);
      overlay.addEventListener('click', (e) => { if (e.target === overlay) closeFn(); });
    },

    showLoading(containerSelector) {
      const container = document.querySelector(containerSelector);
      if (container) {
        container.innerHTML = `
          <div class="editorial-grid-3">
            <div class="skeleton-card"><div class="skeleton skeleton-image"></div><div class="skeleton skeleton-text mt-3"></div><div class="skeleton skeleton-text"></div></div>
            <div class="skeleton-card"><div class="skeleton skeleton-image"></div><div class="skeleton skeleton-text mt-3"></div><div class="skeleton skeleton-text"></div></div>
            <div class="skeleton-card"><div class="skeleton skeleton-image"></div><div class="skeleton skeleton-text mt-3"></div><div class="skeleton skeleton-text"></div></div>
          </div>
        `;
      }
    },

    createEmptyState(message, icon = '🔍') {
      return `
        <div class="empty-state">
          <div style="font-size:3rem; margin-bottom:1rem;">${icon}</div>
          <h3>No Archival Entries Found</h3>
          <p>${message || 'Try adjusting your search criteria or category filter.'}</p>
        </div>
      `;
    },

    initMobileNav() {
      const toggle = document.getElementById('mobile-toggle');
      const drawer = document.getElementById('mobile-drawer');
      if (toggle && drawer) {
        toggle.addEventListener('click', () => {
          drawer.classList.toggle('open');
          toggle.classList.toggle('open');
        });
      }
    },

    initStickyHeader() {
      window.addEventListener('scroll', () => {
        const wrapper = document.querySelector('.main-header-wrapper');
        if (wrapper) {
          if (window.scrollY > 30) {
            wrapper.classList.add('scrolled');
          } else {
            wrapper.classList.remove('scrolled');
          }
        }
      });
    },

    
    
    generateLocalAiResponse(queryText) {
      let raw = (queryText || '').toLowerCase().trim();

      // 1. Greetings & Meta Queries
      if (/^(hi|hello|namaste|hey|hola|greetings|pranam|kaise\s*ho)(\s+|$)/i.test(raw) || /^good\s*(morning|afternoon|evening)/i.test(raw)) {
        return `
          <div style="line-height:1.6;">
            <p><strong>🙏 Namaste! Welcome to the DCHA AI Heritage Assistant.</strong></p>
            <p>I am your interactive digital curator for Indian Cultural Heritage. You can ask me about anything across all 36 Indian States & UTs:</p>
            <ul style="margin: 8px 0; padding-left: 20px;">
              <li>🏛️ <strong>Monuments & Forts</strong> (e.g. <em>"Taj Mahal"</em>, <em>"Tawang Monastery"</em>, <em>"Ita Fort"</em>, <em>"Hampi"</em>)</li>
              <li>🎭 <strong>Classical & Folk Dances</strong> (e.g. <em>"Dances of Odisha"</em>, <em>"Sattriya dance"</em>)</li>
              <li>🍛 <strong>Regional Cuisines</strong> (e.g. <em>"Masor Tenga"</em>, <em>"Andhra thali"</em>, <em>"Pitha"</em>)</li>
              <li>🎉 <strong>Festivals & Fairs</strong> (e.g. <em>"Festivals of West Bengal"</em>,  sustained <em>"Losar festival"</em>)</li>
              <li>👗 <strong>Traditional Attire & Handlooms</strong> (e.g. <em>"Mekhela Chador"</em>, <em>"Kalamkari saree"</em>)</li>
            </ul>
            <p style="font-size:0.85rem; color:#666;">Try typing any monument name, state, or cultural topic below!</p>
          </div>
        `;
      }

      if (/who\s*are\s*you|what\s*can\s*you\s*do|help|about\s*you/.test(raw)) {
        return `
          <div>
            <p><strong>🏛️ DCHA AI Heritage Assistant</strong></p>
            <p>I am trained on comprehensive historical, architectural, and ethnographic archives covering all 28 States and 8 Union Territories of India, traditional musical instruments, premier museums, and 5,000 years of cultural history!</p>
          </div>
        `;
      }

      // DCHA Platform & Project Brain
      if (/dcha|project|website|platform|what\s*can\s*you\s*do|features|kaam\s*karta|about\s*dcha/.test(raw)) {
        return `
          <div style="line-height:1.6;">
            <h4 style="margin:0 0 8px 0; color:#C6A15B; font-family:serif;">🏛️ Digital Culture Heritage Archive (DCHA)</h4>
            <p style="font-size:0.9rem; margin-bottom:10px;">DCHA is India's premier digital cultural repository preserving 5,000 years of civilization across all 28 States and 8 Union Territories.</p>
            <p style="font-size:0.85rem; font-weight:700; margin-bottom:4px; color:#2A2118;">✨ Core Modules Available on this Platform:</p>
            <ul style="font-size:0.85rem; padding-left:18px; margin-bottom:10px;">
              <li>🌐 <a href="explore.html" style="color:#C6A15B; font-weight:600;">Explore Archive</a> — Filter across 100+ monuments by state, dynasty, and era.</li>
              <li>🗺️ <a href="states.html" style="color:#C6A15B; font-weight:600;">36 States & UTs Directory</a> — Deep encyclopedic dossiers on regional arts, food, and culture.</li>
              <li>🏛️ <a href="heritage.html" style="color:#C6A15B; font-weight:600;">Heritage Sites</a> — 42+ UNESCO monuments with high-res photography and GPS mapping.</li>
              <li>🎭 <a href="culture.html" style="color:#C6A15B; font-weight:600;">Culture & Traditions</a> — Classical dances, folk paintings, handlooms, and oral legends.</li>
              <li>🎵 <a href="audio.html" style="color:#C6A15B; font-weight:600;">Cultural Music & Audio</a> — Integrated player for classical ragas and folk instruments.</li>
              <li>🏺 <a href="museum.html" style="color:#C6A15B; font-weight:600;">Museums & Virtual Vault</a> — 12 national museums and 3D/2D digitized artifacts.</li>
              <li>⏳ <a href="timeline.html" style="color:#C6A15B; font-weight:600;">Subcontinent Timeline</a> — 5,000-year chronological spine (Harappa to Modern India).</li>
              <li>🗺️ <a href="map.html" style="color:#C6A15B; font-weight:600;">Interactive Map</a> — Visual spatial exploration of monuments and cultural hubs.</li>
            </ul>
          </div>
        `;
      }

      // Traditional Musical Instruments
      if (/instrument|instruments|music|sitar|veena|tabla|mridangam|shehnai|sarod|santoor|bansuri|flute|sarangi|pakhawaj|ghatam|raga|carnatic|hindustani|gana|geet|vadya/.test(raw)) {
        return `
          <div style="line-height:1.6;">
            <h4 style="margin:0 0 8px 0; color:#C6A15B; font-family:serif;">🎵 Indian Classical & Folk Musical Instruments</h4>
            <p style="font-size:0.88rem; margin-bottom:8px;">Rooted in the ancient Vedic <em>Natya Shastra</em>, Indian instruments are categorized into four acoustical families (<em>Chaturvidha Vadya</em>):</p>
            <ul style="font-size:0.85rem; padding-left:18px; margin-bottom:8px;">
              <li>🎻 <strong>Tata Vadya (Strings)</strong>: <em>Saraswati & Rudra Veena</em> (sacred classical lutes), <em>Sitar</em> (fretted with sympathetic strings), <em>Sarod</em> (fretless teak body with steel fingerboard), <em>Santoor</em> (100-string Kashmiri dulcimer), and <em>Sarangi</em> (bowed instrument imitating the human voice).</li>
              <li>🥁 <strong>Avanaddha Vadya (Percussion)</strong>: <em>Mridangam</em> (double-headed Carnatic rhythmic backbone), <em>Tabla</em> (Dayan & Bayan twin drums of Hindustani music), and <em>Pakhawaj</em> (temple Dhrupad drum).</li>
              <li>🪈 <strong>Sushira Vadya (Wind)</strong>: <em>Bansuri</em> (bamboo flute of Pt. Hariprasad Chaurasia), <em>Shehnai</em> (auspicious double-reed oboe of Ustad Bismillah Khan), and <em>Nadaswaram</em> (majestic South Indian temple instrument).</li>
              <li>🔔 <strong>Ghana Vadya (Solid Idiophones)</strong>: <em>Ghatam</em> (acoustic tuned clay pot), <em>Manjira</em>, and <em>Kartal</em>.</li>
            </ul>
            <p style="font-size:0.82rem; color:#666;">👉 Listen to real audio recordings on our <a href="audio.html" style="color:#C6A15B; text-decoration:underline; font-weight:600;">Cultural Music & Audio Archive</a>.</p>
          </div>
        `;
      }

      // Museums of India & Virtual Relics Vault
      if (/museum|museums|salar\s*jung|national\s*museum|indian\s*museum|csmvs|calico|artifact|artifacts|relic|relics|chola\s*bronze|nataraja|dancing\s*girl|veiled\s*rebecca|katar|vault/.test(raw)) {
        return `
          <div style="line-height:1.6;">
            <h4 style="margin:0 0 8px 0; color:#C6A15B; font-family:serif;">🏺 Museums of India & Virtual Artifact Vault</h4>
            <p style="font-size:0.88rem; margin-bottom:8px;">India's premier institutions preserve over 5,000 years of sculptures, illuminated codices, and royal antiquities:</p>
            <ul style="font-size:0.85rem; padding-left:18px; margin-bottom:8px;">
              <li>🏛️ <strong>National Museum (New Delhi)</strong>: 200,000+ relics including the Harappan Gallery and Buddhist relic caskets.</li>
              <li>🏛️ <strong>Indian Museum (Kolkata, Estd. 1814)</strong>: 9th oldest museum in the world, famed for Egyptian mummies and Ashokan casts.</li>
              <li>🏛️ <strong>Salar Jung Museum (Hyderabad)</strong>: World's largest one-man collection, home to the famous <em>Veiled Rebecca</em> marble statue and 19th-century Musical Clock.</li>
              <li>🏛️ <strong>CSMVS (Mumbai)</strong>: Indo-Saracenic landmark preserving Indus artifacts and Himalayan bronzes.</li>
              <li>🏛️ <strong>Government Museum (Chennai)</strong>: World's greatest collection of bronze Chola sculptures.</li>
            </ul>
            <p style="font-size:0.85rem; font-weight:700; margin-bottom:4px; color:#2A2118;">✨ Virtual Relics in DCHA Vault:</p>
            <ul style="font-size:0.82rem; padding-left:18px; margin-bottom:8px;">
              <li>• <strong>Dancing Girl of Mohenjo-daro</strong> (2300 BCE Lost-wax bronze)</li>
              <li>• <strong>Chola Nataraja Bronze</strong> (10th Century Cosmic Dance of Shiva)</li>
              <li>• <strong>Veiled Rebecca Marble</strong> (1876 CE translucent marble carving)</li>
              <li>• <strong>Damascened Wootz Steel Rajput Katar</strong> (17th Century gold koftgari dagger)</li>
            </ul>
            <p style="font-size:0.82rem; color:#666;">👉 Explore interactive 3D/2D views on the <a href="museum.html" style="color:#C6A15B; text-decoration:underline; font-weight:600;">Museums & Virtual Vault Page</a>.</p>
          </div>
        `;
      }

      // Classical & Folk Dances
      if (/dance|dances|nritya|classical\s*dance|kathak|bharatanatyam|kathakali|odissi|kuchipudi|manipuri|mohiniyattam|sattriya|garba|bhangra|chhau|ghoomar|lavani/.test(raw)) {
        return `
          <div style="line-height:1.6;">
            <h4 style="margin:0 0 8px 0; color:#C6A15B; font-family:serif;">🎭 Classical & Folk Dances of India</h4>
            <p style="font-size:0.88rem; margin-bottom:8px;">India's Sangeet Natak Akademi officially recognizes <strong>8 Classical Dance Traditions</strong>:</p>
            <ul style="font-size:0.85rem; padding-left:18px; margin-bottom:8px;">
              <li>1. <strong>Bharatanatyam (Tamil Nadu)</strong>: Temple dance of geometric precision, crisp footwork (<em>adavus</em>), and expressive <em>abhinaya</em>.</li>
              <li>2. <strong>Kathak (Uttar Pradesh & North India)</strong>: Nomadic storytelling art famous for rapid spins (<em>chakkars</em>) and rhythmic <em>tatkar</em>.</li>
              <li>3. <strong>Kathakali (Kerala)</strong>: Grand dance-drama with stylized face paint (<em>Paccha, Kathi</em>) and expressive mudras.</li>
              <li>4. <strong>Odissi (Odisha)</strong>: Sensual temple sculpture dance famed for the three-bend <em>Tribhangi</em> posture.</li>
              <li>5. <strong>Kuchipudi (Andhra Pradesh)</strong>: Dynamic dance-drama famous for dancing on a brass plate (<em>Tarangam</em>).</li>
              <li>6. <strong>Manipuri (Manipur)</strong>: Graceful serpentine devotional dance depicting Radha-Krishna <em>Raas Leela</em>.</li>
              <li>7. <strong>Mohiniyattam (Kerala)</strong>: Wave-like dance of the enchantress in cream-gold Kasavu silk.</li>
              <li>8. <strong>Sattriya (Assam)</strong>: 15th-century Vaishnavite monastic tradition founded by Saint Srimanta Sankardev.</li>
            </ul>
            <p style="font-size:0.85rem;">🎉 <strong>Celebrated Folk Dances:</strong> Garba (Gujarat), Bhangra (Punjab), Ghoomar (Rajasthan), Chhau (Jharkhand/Bengal), Lavani (Maharashtra), and Bihu (Assam).</p>
            <p style="font-size:0.82rem; color:#666;">👉 Explore multimedia galleries on the <a href="culture.html#dance" style="color:#C6A15B; text-decoration:underline; font-weight:600;">Culture & Performing Arts Page</a>.</p>
          </div>
        `;
      }

      // 5,000-Year Subcontinent Timeline
      if (/timeline|chronology|ancient|civilization|harappa|vedic|maurya|ashoka|gupta|mughal|chola\s*empire|vijayanagara|independence|5000\s*years/.test(raw)) {
        return `
          <div style="line-height:1.6;">
            <h4 style="margin:0 0 8px 0; color:#C6A15B; font-family:serif;">⏳ Subcontinent Heritage Timeline (5,000 Years)</h4>
            <p style="font-size:0.88rem; margin-bottom:8px;">Chronological epochs preserving India's civilizational continuum:</p>
            <ul style="font-size:0.85rem; padding-left:18px; margin-bottom:8px;">
              <li>🏛️ <strong>2500 – 1500 BCE: Indus Valley Civilization</strong> (Harappa, Mohenjo-daro, Lothal dockyard, urban planning).</li>
              <li>📜 <strong>1500 – 500 BCE: Vedic & Epic Era</strong> (Composition of Vedas, Upanishads, and emergence of Mahajanapadas).</li>
              <li>🦁 <strong>322 – 185 BCE: Mauryan Empire</strong> (Emperor Ashoka's edicts, Sanchi Stupa, Ahimsa diplomacy).</li>
              <li>🎨 <strong>320 – 550 CE: Gupta Golden Age</strong> (Classical Sanskrit arts, Aryabhata mathematics, Ajanta frescoes).</li>
              <li>⛵ <strong>9th – 13th Century CE: Imperial Chola Maritime Empire</strong> (Brihadeeswarar temple, lost-wax bronzes).</li>
              <li>🏰 <strong>1336 – 1646 CE: Vijayanagara Empire</strong> (Splendid capital of Hampi on the Tungabhadra river).</li>
              <li>🕌 <strong>1526 – 1707 CE: Mughal Architectural Epoch</strong> (Taj Mahal, Red Fort, Fatehpur Sikri).</li>
              <li>🇮🇳 <strong>1857 – 1947 CE: Freedom Struggle & Modern Republic</strong> (National renaissance, Ahimsa, and 1947 Independence).</li>
            </ul>
            <p style="font-size:0.82rem; color:#666;">👉 Walk through the interactive golden spine on our <a href="timeline.html" style="color:#C6A15B; text-decoration:underline; font-weight:600;">Heritage Timeline Page</a>.</p>
          </div>
        `;
      }

      // 2. Strip Conversational Stop Words & Intent Prefixes
      let cleaned = raw
        .replace(/\b(tell\s*me\s*about|tell\s*me|what\s*is|where\s*is|show\s*me|details\s*of|information\s*about|info\s*on|give\s*me|history\s*of|famous|popular|best|top|the|ke\s*bare\s*me|kya\s*hai|batao)\b/gi, '')
        .replace(/[^\w\s]/g, '')
        .trim();

      const q = cleaned || raw;

      // 3. Known World-Famous Indian Landmarks Dictionary
      const famousMonuments = {
        'taj mahal': {
          name: 'Taj Mahal',
          location: 'Agra, Uttar Pradesh',
          category: 'UNESCO World Heritage Site',
          description: 'An ivory-white marble mausoleum on the right bank of the river Yamuna, commissioned in 1631 by Mughal Emperor Shah Jahan for his favorite wife, Mumtaz Mahal. Famed worldwide as the jewel of Muslim art in India and an enduring symbol of eternal love.'
        },
        'red fort': {
          name: 'Red Fort (Lal Qila)',
          location: 'Old Delhi',
          category: 'Mughal Architectural Fortress',
          description: 'Historic red sandstone fortress built by Emperor Shah Jahan in 1638. Serves as the symbolic site of India’s Independence Day celebrations every year.'
        },
        'qutub minar': {
          name: 'Qutub Minar',
          location: 'New Delhi',
          category: '73-meter UNESCO Victory Tower',
          description: 'World’s tallest brick minaret built in 1192 CE by Qutb-ud-din Aibak, featuring intricate Indo-Islamic carvings and the 1600-year-old rust-resistant Iron Pillar of Chandragupta II.'
        },
        'hampi': {
          name: 'Group of Monuments at Hampi',
          location: 'Vijayanagara, Karnataka',
          category: 'UNESCO Ancient Capital Ruins',
          description: 'Capital of the medieval Vijayanagara Empire featuring magnificent stone chariots, Virupaksha temple, and monolithic shrines along the Tungabhadra river.'
        },
        'konark': {
          name: 'Konark Sun Temple',
          location: 'Puri, Odisha',
          category: '13th-Century UNESCO Sun Chariot Temple',
          description: 'Built by King Narasimhadeva I of the Eastern Ganga Dynasty in the shape of a massive 24-wheeled solar chariot pulled by seven horses.'
        },
        'golden temple': {
          name: 'Sri Harmandir Sahib (Golden Temple)',
          location: 'Amritsar, Punjab',
          category: 'Holiest Sikh Gurdwara',
          description: 'Spiritual center of Sikhism surrounded by the sacred Amrit Sarovar lake, gilded with pure gold foil, offering free mega-community kitchen (Langar) to thousands daily.'
        },
        'hawa mahal': {
          name: 'Hawa Mahal (Palace of Winds)',
          location: 'Jaipur, Rajasthan',
          category: 'Rajput Pink Sandstone Palace',
          description: 'Five-story honeycomb structure with 953 jharokha latticed windows built by Maharaja Sawai Pratap Singh in 1799.'
        },
        'ajanta': {
          name: 'Ajanta & Ellora Caves',
          location: 'Chhatrapati Sambhajinagar, Maharashtra',
          category: 'UNESCO Rock-Cut Cave Shrines',
          description: '30 rock-cut Buddhist cave monuments at Ajanta renowned for 2nd-century BCE fresco paintings, and 34 caves at Ellora including the monolithic Kailash Temple.'
        },
        'khajuraho': {
          name: 'Khajuraho Group of Monuments',
          location: 'Chhatarpur, Madhya Pradesh',
          category: 'Nagara Style Temple Complex',
          description: 'Medieval Chandela dynasty temples built between 950 and 1050 CE, renowned for exquisite Nagara architecture and intricate sculptures.'
        },
        'charminar': {
          name: 'Charminar',
          location: 'Hyderabad, Telangana',
          category: '1591 CE Qutb Shahi Monument',
          description: 'Iconic four-arched mosque built by Muhammad Quli Qutb Shah celebrating the end of a deadly plague in the city.'
        },
        'sanchi': {
          name: 'Sanchi Stupa',
          location: 'Raisen, Madhya Pradesh',
          category: '3rd-Century BCE Buddhist Stupa',
          description: 'Oldest stone structure in India commissioned by Emperor Ashoka the Great, featuring carved torana gateways detailing Jataka tales.'
        },
        'victoria memorial': {
          name: 'Victoria Memorial',
          location: 'Kolkata, West Bengal',
          category: 'Indo-Saracenic Marble Museum',
          description: 'Grand white Makrana marble building opened in 1921 dedicated to Queen Victoria, housing royal portraits and colonial artifacts.'
        },
        'meenakshi': {
          name: 'Meenakshi Amman Temple',
          location: 'Madurai, Tamil Nadu',
          category: 'Dravidian Gopuram Complex',
          description: 'Historic Hindu temple complex dedicated to Goddess Meenakshi featuring 14 majestic colorful gopuram towers and 33,000 sculptures.'
        }
      };

      for (const key in famousMonuments) {
        if (q.includes(key) || key.includes(q)) {
          const item = famousMonuments[key];
          return `
            <div style="line-height:1.6;">
              <h4 style="margin:0 0 6px 0; color:#C6A15B; font-family:serif;">🏛️ ${item.name}</h4>
              <p style="font-size:0.85rem; color:#888; margin-bottom:8px;">📍 <strong>${item.location}</strong> | 🏷️ <em>${item.category}</em></p>
              <p style="font-size:0.88rem; margin-bottom:10px;">${item.description}</p>
            </div>
          `;
        }
      }

      // 4. Search State Level Data (state_details_data.js)
      const stateKeys = Object.keys(window.STATE_DETAILS || {});
      let matchedStateData = null;

      for (const sk of stateKeys) {
        const s = window.STATE_DETAILS[sk];
        if (s && s.name && (q.includes(s.name.toLowerCase()) || q === sk)) {
          matchedStateData = s;
          break;
        }
      }

      if (!matchedStateData && window.DCHA_DATA && window.DCHA_DATA.STATES) {
        const sObj = window.DCHA_DATA.STATES.find(st => q.includes(st.name.toLowerCase()));
        if (sObj && window.STATE_DETAILS) {
          matchedStateData = window.STATE_DETAILS[sObj.id];
        }
      }

      if (matchedStateData) {
        const s = matchedStateData;
        let html = `
          <div style="line-height:1.6;">
            <h4 style="margin:0 0 6px 0; color:#C6A15B; font-family:serif;">🏛️ ${s.name} (${s.capital || ''})</h4>
            <p style="font-size:0.88rem; margin-bottom:8px;"><strong>Geography & Location:</strong> ${s.location_geography || 'Rich cultural region in India.'}</p>
            <p style="font-size:0.88rem; margin-bottom:10px;"><strong>Primary Languages:</strong> ${Array.isArray(s.languages) ? s.languages.join(', ') : (s.languages || 'Regional languages')}</p>
        `;

        if (s.tourist_places && s.tourist_places.length > 0) {
          html += `<div style="margin-bottom:8px;"><strong>📍 Recommended Tourist Places:</strong><ul style="margin:4px 0; padding-left:18px; font-size:0.85rem;">`;
          s.tourist_places.slice(0, 4).forEach(tp => {
            html += `<li><strong>${tp.name}</strong> (${tp.type || 'Attraction'}) - ${tp.description || ''}</li>`;
          });
          html += `</ul></div>`;
        }

        if (s.heritage_places && s.heritage_places.length > 0) {
          html += `<div style="margin-bottom:8px;"><strong>🏰 Heritage & Historical Sites:</strong><ul style="margin:4px 0; padding-left:18px; font-size:0.85rem;">`;
          s.heritage_places.slice(0, 3).forEach(hp => {
            html += `<li><strong>${hp.name}</strong> - ${hp.description || ''}</li>`;
          });
          html += `</ul></div>`;
        }

        if (s.festivals && s.festivals.length > 0) {
          html += `<div style="margin-bottom:8px;"><strong>🎉 Major Festivals:</strong><ul style="margin:4px 0; padding-left:18px; font-size:0.85rem;">`;
          s.festivals.slice(0, 3).forEach(f => {
            html += `<li><strong>${f.name}</strong> (${f.month || ''}) - ${f.description || ''}</li>`;
          });
          html += `</ul></div>`;
        }

        if (s.traditional_foods && s.traditional_foods.length > 0) {
          html += `<div style="margin-bottom:8px;"><strong>🍛 Traditional Cuisine:</strong><ul style="margin:4px 0; padding-left:18px; font-size:0.85rem;">`;
          s.traditional_foods.slice(0, 3).forEach(food => {
            html += `<li><strong>${food.name}</strong> - ${food.description || ''}</li>`;
          });
          html += `</ul></div>`;
        }

        html += `<p style="font-size:0.8rem; color:#777; margin-top:10px;">👉 Explore full details on the <a href="state-detail.html?id=${s.id}" style="color:#C6A15B; text-decoration:underline;">${s.name} Heritage Page</a>.</p></div>`;
        return html;
      }

      // 5. Item Level Deep Matching (Monuments, Tourist Places, Foods, Festivals across all 36 States)
      let itemMatches = [];
      stateKeys.forEach(sk => {
        const s = window.STATE_DETAILS[sk];
        if (!s) return;
        const collections = [
          { list: s.heritage_places, cat: 'Heritage Site' },
          { list: s.tourist_places, cat: 'Tourist Attraction' },
          { list: s.festivals, cat: 'Festival' },
          { list: s.traditional_foods, cat: 'Cuisine' },
          { list: s.traditional_dresses, cat: 'Traditional Attire' }
        ];

        collections.forEach(col => {
          if (col.list && Array.isArray(col.list)) {
            col.list.forEach(item => {
              const nameLower = (item.name || '').toLowerCase();
              if (nameLower.includes(q) || q.includes(nameLower)) {
                itemMatches.push({ ...item, stateName: s.name, stateId: s.id, cat: col.cat });
              }
            });
          }
        });
      });

      if (itemMatches.length > 0) {
        let html = `<div><p>🔍 Found <strong>${itemMatches.length}</strong> heritage record(s) matching "<em>${queryText}</em>":</p><ul style="padding-left:18px; font-size:0.88rem; line-height:1.6;">`;
        itemMatches.slice(0, 5).forEach(m => {
          html += `<li style="margin-bottom:8px;"><strong>${m.name}</strong> (${m.stateName} • <em>${m.cat}</em>)<br><span style="font-size:0.82rem; color:#555;">${m.description || ''}</span></li>`;
        });
        html += `</ul></div>`;
        return html;
      }

      // 6. Topic Searches (Food / Cuisine)
      if (/food|cuisine|thali|dish|dishes|tasty|eating|sweet|sweets|porridge|curry/.test(q)) {
        let matches = [];
        stateKeys.forEach(sk => {
          const s = window.STATE_DETAILS[sk];
          if (s && s.traditional_foods) {
            s.traditional_foods.forEach(f => {
              matches.push({ ...f, stateName: s.name, stateId: s.id });
            });
          }
        });

        if (matches.length > 0) {
          let html = `<div><p><strong>🍛 Iconic Regional Cuisines of India:</strong></p><ul style="padding-left:18px; font-size:0.88rem; line-height:1.6;">`;
          matches.slice(0, 5).forEach(m => {
            html += `<li style="margin-bottom:6px;"><strong>${m.name}</strong> (${m.stateName})<br><span style="font-size:0.82rem; color:#555;">${m.description || ''}</span></li>`;
          });
          html += `</ul></div>`;
          return html;
        }
      }

      // 7. Topic Searches (Festivals)
      if (/festival|festivals|fair|celebration|mela|puja|diwali|bihu|losar|holi/.test(q)) {
        let matches = [];
        stateKeys.forEach(sk => {
          const s = window.STATE_DETAILS[sk];
          if (s && s.festivals) {
            s.festivals.forEach(f => {
              matches.push({ ...f, stateName: s.name, stateId: s.id });
            });
          }
        });

        if (matches.length > 0) {
          let html = `<div><p><strong>🎉 Vibrant Cultural & Seasonal Festivals:</strong></p><ul style="padding-left:18px; font-size:0.88rem; line-height:1.6;">`;
          matches.slice(0, 5).forEach(m => {
            html += `<li style="margin-bottom:6px;"><strong>${m.name}</strong> (${m.stateName} • ${m.month || ''})<br><span style="font-size:0.82rem; color:#555;">${m.description || ''}</span></li>`;
          });
          html += `</ul></div>`;
          return html;
        }
      }

      // 8. Topic Searches (Monuments / Forts / Temples / Heritage)
      if (/monument|fort|temple|palace|stupa|citadel|archaeological|heritage|tourist|place|visit|destination|attraction/.test(q)) {
        let matches = [];
        stateKeys.forEach(sk => {
          const s = window.STATE_DETAILS[sk];
          if (s && s.heritage_places) {
            s.heritage_places.forEach(h => matches.push({ ...h, stateName: s.name, stateId: s.id }));
          }
          if (s && s.tourist_places) {
            s.tourist_places.forEach(t => matches.push({ ...t, stateName: s.name, stateId: s.id }));
          }
        });

        if (matches.length > 0) {
          let html = `<div><p><strong>🏰 Famous Monuments & Heritage Landmarks:</strong></p><ul style="padding-left:18px; font-size:0.88rem; line-height:1.6;">`;
          matches.slice(0, 5).forEach(m => {
            html += `<li style="margin-bottom:6px;"><strong>${m.name}</strong> (${m.stateName})<br><span style="font-size:0.82rem; color:#555;">${m.description || ''}</span></li>`;
          });
          html += `</ul></div>`;
          return html;
        }
      }

      // Fallback
      return `
        <div style="line-height:1.6;">
          <p>✨ <strong>DCHA Heritage Knowledge Assistant:</strong></p>
          <p>I have scanned the DCHA repository for "<em>${queryText}</em>". You can ask about:</p>
          <ul style="padding-left:18px; font-size:0.85rem; margin:8px 0;">
            <li>🏛️ <strong>States & UTs</strong> (e.g. <em>"Punjab"</em>, <em>"Tamil Nadu"</em>, <em>"Assam"</em>)</li>
            <li>🎵 <strong>Musical Instruments</strong> (e.g. <em>"Sitar"</em>, <em>"Veena"</em>, <em>"Tabla"</em>, <em>"Shehnai"</em>)</li>
            <li>🏺 <strong>Museums & Relics</strong> (e.g. <em>"Salar Jung"</em>, <em>"National Museum"</em>, <em>"Chola Nataraja"</em>)</li>
            <li>⏳ <strong>5,000-Year Timeline</strong> (e.g. <em>"Harappan Civilization"</em>, <em>"Chola Empire"</em>)</li>
            <li>🎭 <strong>Classical Dances</strong> (e.g. <em>"Kathakali"</em>, <em>"Bharatanatyam"</em>, <em>"Odissi"</em>)</li>
          </ul>
        </div>
      `;
    },

    formatAiMarkdown(text) {
      if (!text) return '';
      if (text.includes('<div') || text.includes('<p>') || text.includes('<h4>') || text.includes('<ul>')) {
        return text;
      }
      let html = text
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');

      // Bold & Italic
      html = html.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
      html = html.replace(/\*(.*?)\*/g, '<em>$1</em>');
      // Markdown links [text](url)
      html = html.replace(/\[(.*?)\]\((.*?)\)/g, '<a href="$2" style="color:#C6A15B; font-weight:600; text-decoration:underline;">$1</a>');
      // Inline Code
      html = html.replace(/`(.*?)`/g, '<code style="background:rgba(0,0,0,0.06); padding:2px 6px; border-radius:4px; font-size:0.85em;">$1</code>');
      // Horizontal Rule
      html = html.replace(/^---$/gm, '<hr style="border:none; border-top:1px solid rgba(0,0,0,0.12); margin:12px 0;">');
      // Bullet lists
      html = html.replace(/^[•\-\*]\s+(.*)$/gm, '<li style="margin-bottom:4px;">$1</li>');
      html = html.replace(/((?:<li style="margin-bottom:4px;">.*?<\/li>\s*)+)/gs, '<ul style="margin:8px 0; padding-left:20px; line-height:1.5;">$1</ul>');
      // Paragraphs
      html = html.split('\n\n').map(p => {
        p = p.trim();
        if (!p) return '';
        if (p.startsWith('<ul') || p.startsWith('<hr') || p.startsWith('<div')) return p;
        return `<p style="margin-bottom:8px; line-height:1.6;">${p.replace(/\n/g, '<br>')}</p>`;
      }).join('');

      return html;
    },

    initAiAssistantFloatingButton() {
      if (document.getElementById('ai-assistant-floating-btn')) return;

      const btn = document.createElement('button');
      btn.id = 'ai-assistant-floating-btn';
      btn.className = 'ai-assistant-fab';
      btn.innerHTML = `<span>✨ Ask AI Heritage Assistant</span>`;
      btn.style.cssText = `
        position: fixed;
        bottom: 24px;
        right: 24px;
        z-index: 9990;
        background: linear-gradient(135deg, #1C1B18 0%, #C6A15B 100%);
        color: #FFFFFF;
        border: 1.5px solid #C6A15B;
        border-radius: 30px;
        padding: 12px 22px;
        font-family: inherit;
        font-size: 0.88rem;
        font-weight: 700;
        cursor: pointer;
        box-shadow: 0 10px 30px rgba(0,0,0,0.4);
        transition: transform 0.2s ease, box-shadow 0.2s ease;
      `;

      btn.addEventListener('mouseenter', () => btn.style.transform = 'translateY(-2px)');
      btn.addEventListener('mouseleave', () => btn.style.transform = 'translateY(0)');
      btn.addEventListener('click', () => this.openAiAssistantModal());

      document.body.appendChild(btn);
    },

    openAiAssistantModal(initialPrompt = '') {
      const modalContent = `
        <div class="ai-chat-container" style="display:flex; flex-direction:column; height:500px; max-width:620px;">
          <!-- QUICK SUGGESTIONS -->
          <div class="ai-chat-suggestions" style="margin-bottom:12px; display:flex; gap:6px; flex-wrap:wrap; max-height:85px; overflow-y:auto; padding-bottom:4px;">
            <button class="chip-btn" data-query="What is DCHA project and features?">🏛️ About DCHA Project</button>
            <button class="chip-btn" data-query="Traditional musical instruments in India">🎵 Musical Instruments</button>
            <button class="chip-btn" data-query="Famous museums of India and Salar Jung">🏺 Museums & Vault</button>
            <button class="chip-btn" data-query="Classical dance forms of India">🎭 Classical Dances</button>
            <button class="chip-btn" data-query="5,000-year history and timeline of India">⏳ 5000-Yr Timeline</button>
            <button class="chip-btn" data-query="Punjab ke bare me batao">🌾 Punjab Heritage</button>
            <button class="chip-btn" data-query="Culture and heritage of Tamil Nadu">🛕 Tamil Nadu</button>
            <button class="chip-btn" data-query="Cultural traditions of Gujarat">🌊 Gujarat Heritage</button>
          </div>

          <!-- MESSAGES BOX -->
          <div id="ai-chat-messages" style="flex:1; overflow-y:auto; padding:14px; background:#FDFBF7; border:1px solid #E6DEC9; border-radius:8px; margin-bottom:12px; font-size:0.92rem;">
            <div class="chat-msg ai-msg" style="background:#F2ECE1; border:1px solid rgba(198,161,91,0.25); padding:12px 16px; border-radius:8px; margin-bottom:12px; color:#1C1B18; line-height:1.6;">
              🙏 <strong>Namaste!</strong> I am the <strong>DCHA AI Heritage Assistant</strong>. Ask me anything about India's <strong>all 36 States & UTs</strong>, traditional <strong>musical instruments</strong> (Veena, Sitar, Mridangam), <strong>museums of India</strong>, <strong>UNESCO monuments</strong>, or <strong>5,000 years of civilization</strong>!
            </div>
          </div>

          <!-- INPUT FORM -->
          <form id="ai-chat-form" style="display:flex; gap:8px;">
            <input type="text" id="ai-chat-input" class="form-control" placeholder="Ask about states, instruments, museums, dance, food, timeline..." required style="flex:1; padding:12px 16px; border-radius:6px; border:1px solid #C6A15B; font-size:0.92rem; outline:none; background:#FFFFFF;">
            <button type="submit" id="ai-chat-submit" class="btn btn-primary" style="padding:12px 22px; border-radius:6px; font-weight:700; background:linear-gradient(135deg, #D4AF37 0%, #AA8222 100%); color:#0D0E0D; border:none; cursor:pointer;">Ask AI →</button>
          </form>

          <!-- AI PROVENANCE & TRANSPARENCY DISCLAIMER -->
          <div class="ai-provenance-bar" style="margin-top:10px; padding-top:8px; border-top:1px solid rgba(198,161,91,0.25); display:flex; align-items:center; justify-content:space-between; font-size:0.75rem; color:#776E62;">
            <span>🤖 <strong>AI Provenance:</strong> Curatorial synthesis based on Indian historical sources. Non-binding archival guidance.</span>
            <a href="terms.html#ai-disclaimer" style="color:#C6A15B; text-decoration:underline;">AI Terms</a>
          </div>
        </div>
      `;

      this.showModal(modalContent, { title: '✨ DCHA AI Heritage Assistant & Knowledge Engine' });

      const form = document.getElementById('ai-chat-form');
      const input = document.getElementById('ai-chat-input');
      const msgBox = document.getElementById('ai-chat-messages');

      const sendQuery = async (queryText) => {
        if (!queryText) return;

        // User query message
        const userDiv = document.createElement('div');
        userDiv.style.cssText = 'background:#1C1B18; color:#F5F1E8; padding:10px 16px; border-radius:8px; margin-bottom:12px; font-size:0.92rem; align-self:flex-end; text-align:right; border-left:3px solid #C6A15B;';
        userDiv.textContent = queryText;
        msgBox.appendChild(userDiv);

        // Typing indicator
        const typingDiv = document.createElement('div');
        typingDiv.style.cssText = 'background:#F2ECE1; color:#776E62; padding:12px 16px; border-radius:8px; margin-bottom:12px; font-size:0.88rem; font-style:italic; border:1px solid rgba(198,161,91,0.2);';
        typingDiv.textContent = '✨ Consulting Indian Cultural Heritage Knowledge Engine...';
        msgBox.appendChild(typingDiv);
        msgBox.scrollTop = msgBox.scrollHeight;

        let responseText = null;
        try {
          if (window.api && typeof window.api.post === 'function') {
            const res = await window.api.post('/ai/chat', { prompt: queryText });
            responseText = (res && (res.response || (res.data && res.data.response)));
          }
        } catch (err) {
          responseText = null;
        }

        if (!responseText) {
          responseText = this.generateLocalAiResponse(queryText);
        }

        typingDiv.style.fontStyle = 'normal';
        typingDiv.style.color = '#1C1B18';
        typingDiv.innerHTML = this.formatAiMarkdown(responseText);
        msgBox.scrollTop = msgBox.scrollHeight;
      };

      form?.addEventListener('submit', (e) => {
        e.preventDefault();
        const text = input.value.trim();
        input.value = '';
        sendQuery(text);
      });

      document.querySelectorAll('.chip-btn').forEach(btn => {
        btn.style.cssText = 'background:#EAE4D6; border:1px solid rgba(198,161,91,0.4); border-radius:15px; padding:5px 12px; font-size:0.75rem; font-weight:600; cursor:pointer; color:#2A2118; transition:all 0.15s; white-space:nowrap;';
        btn.addEventListener('mouseenter', () => {
          btn.style.background = '#C6A15B';
          btn.style.color = '#0D0E0D';
        });
        btn.addEventListener('mouseleave', () => {
          btn.style.background = '#EAE4D6';
          btn.style.color = '#2A2118';
        });
        btn.addEventListener('click', () => {
          sendQuery(btn.dataset.query);
        });
      });

      if (initialPrompt) {
        input.value = initialPrompt;
        sendQuery(initialPrompt);
      }
    },

    async openAddToCollectionModal(itemType, itemId, itemTitle) {
      if (!window.api || !window.api.isLoggedIn()) {
        const notAuthContent = `
          <div style="text-align:center; padding: 15px 10px;">
            <div style="font-size:3rem; margin-bottom:12px;">📁</div>
            <h3 style="margin-bottom:8px; font-family:var(--font-heading, serif);">Save to Heritage Collection</h3>
            <p style="color:var(--warm-gray); font-size:0.95rem; margin-bottom:20px; line-height:1.6;">
              Please log in to save items to a collection and manage your custom archival anthologies.
            </p>
            <div style="display:flex; gap:12px; justify-content:center;">
              <a href="login.html" class="btn btn-primary btn-sm">Log In</a>
              <a href="register.html" class="btn btn-secondary btn-sm">Sign Up</a>
            </div>
          </div>
        `;
        this.showModal(notAuthContent, { title: 'Log In Required' });
        return;
      }

      const modalId = 'add-to-col-modal-' + Date.now();
      const content = `
        <div id="${modalId}" style="padding:5px;">
          <p style="font-size:0.95rem; color:var(--charcoal); margin-bottom:16px;">
            Select a collection to add <strong>${itemTitle}</strong>:
          </p>
          <div id="modal-collections-list" style="max-height:220px; overflow-y:auto; margin-bottom:20px; border:1px solid #EAEAEA; border-radius:6px; padding:8px;">
            <div style="text-align:center; padding:20px; color:#666;">Loading collections...</div>
          </div>
          <hr style="border:0; border-top:1px solid #EAEAEA; margin:16px 0;">
          <h4 style="font-size:1rem; margin-bottom:10px;">+ Create New Collection</h4>
          <form id="modal-create-col-form" style="display:flex; flex-direction:column; gap:10px;">
            <input type="text" id="modal-col-name" class="form-control" placeholder="Collection Name (e.g. Temple Architecture)" required style="padding:8px 12px; border:1px solid #CCC; border-radius:4px; font-size:0.9rem;">
            <textarea id="modal-col-desc" class="form-control" placeholder="Optional description..." rows="2" style="padding:8px 12px; border:1px solid #CCC; border-radius:4px; font-size:0.85rem;"></textarea>
            <button type="submit" class="btn btn-primary btn-sm" style="align-self:flex-start;">Create & Add Item</button>
          </form>
        </div>
      `;

      this.showModal(content, { title: `📁 Add to Collection` });

      // Fetch user collections
      try {
        const res = await window.api.get('/collections');
        const listContainer = document.getElementById('modal-collections-list');
        const collections = (res && res.data) ? res.data : (Array.isArray(res) ? res : []);

        if (listContainer) {
          if (!collections || collections.length === 0) {
            listContainer.innerHTML = `
              <div style="text-align:center; padding:15px; color:var(--muted-gray); font-size:0.9rem;">
                No custom collections created yet. Create one below!
              </div>
            `;
          } else {
            listContainer.innerHTML = collections.map(c => `
              <div style="display:flex; justify-content:space-between; align-items:center; padding:10px; border-bottom:1px solid #F0F0F0;">
                <div>
                  <strong style="font-size:0.95rem; display:block;">${c.name}</strong>
                  <span style="font-size:0.8rem; color:var(--warm-gray);">${c.description || 'Custom Anthology'}</span>
                </div>
                <button type="button" class="btn btn-secondary btn-sm modal-add-item-btn" data-col-id="${c.id}" style="padding:4px 12px; font-size:0.8rem;">
                  + Add
                </button>
              </div>
            `).join('');

            // Add event listener to each add button
            listContainer.querySelectorAll('.modal-add-item-btn').forEach(btn => {
              btn.addEventListener('click', async () => {
                const colId = btn.dataset.colId;
                btn.disabled = true;
                btn.textContent = 'Adding...';
                try {
                  const numericItemId = typeof itemId === 'number' ? itemId : parseInt(itemId, 10) || 1;
                  await window.api.post(`/collections/${colId}/items`, {
                    itemType: itemType,
                    itemId: numericItemId
                  });
                  window.components.showToast('Added to collection.', 'success');
                  const overlay = btn.closest('.modal-overlay');
                  if (overlay) overlay.remove();
                } catch (err) {
                  btn.disabled = false;
                  btn.textContent = '+ Add';
                  const msg = (err && err.message) ? err.message : 'Failed to add item to collection';
                  if (msg.toLowerCase().includes('already')) {
                    window.components.showToast('Already in this collection.', 'warning');
                  } else {
                    window.components.showToast(msg, 'error');
                  }
                }
              });
            });
          }
        }
      } catch (err) {
        const listContainer = document.getElementById('modal-collections-list');
        if (listContainer) {
          listContainer.innerHTML = `<div style="color:var(--danger); padding:10px; font-size:0.85rem;">Failed to load collections: ${err.message}</div>`;
        }
      }

      // Handle Form Submit
      const createForm = document.getElementById('modal-create-col-form');
      if (createForm) {
        createForm.addEventListener('submit', async (e) => {
          e.preventDefault();
          const nameInput = document.getElementById('modal-col-name');
          const descInput = document.getElementById('modal-col-desc');
          const submitBtn = createForm.querySelector('button[type="submit"]');

          if (!nameInput.value.trim()) return;

          submitBtn.disabled = true;
          submitBtn.textContent = 'Creating...';

          try {
            const newColRes = await window.api.post('/collections', {
              name: nameInput.value.trim(),
              description: descInput.value.trim() || 'Custom Heritage Collection',
              isPublic: true
            });

            const newCol = newColRes.data || newColRes;
            if (newCol && newCol.id) {
              const numericItemId = typeof itemId === 'number' ? itemId : parseInt(itemId, 10) || 1;
              await window.api.post(`/collections/${newCol.id}/items`, {
                itemType: itemType,
                itemId: numericItemId
              });
              window.components.showToast('Collection created and item added.', 'success');
              const overlay = createForm.closest('.modal-overlay');
              if (overlay) overlay.remove();
            }
          } catch (err) {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Create & Add Item';
            window.components.showToast(err.message || 'Failed to create collection', 'error');
          }
        });
      }
    },

    debounce(func, wait) {
      let timeout;
      return function executedFunction(...args) {
        const later = () => { clearTimeout(timeout); func(...args); };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
      };
    },

    initTheme() {
      const savedTheme = localStorage.getItem('dcha_theme') || 'light';
      document.documentElement.setAttribute('data-theme', savedTheme);
      this.updateThemeToggleIcon(savedTheme);

      const toggleBtn = document.getElementById('theme-toggle-btn');
      if (toggleBtn && !toggleBtn.dataset.themeBound) {
        toggleBtn.dataset.themeBound = 'true';
        toggleBtn.addEventListener('click', (e) => {
          e.preventDefault();
          const currentTheme = document.documentElement.getAttribute('data-theme') || 'light';
          const newTheme = currentTheme === 'light' ? 'dark' : 'light';
          document.documentElement.setAttribute('data-theme', newTheme);
          localStorage.setItem('dcha_theme', newTheme);
          this.updateThemeToggleIcon(newTheme);
          this.showToast(`Switched to ${newTheme === 'dark' ? 'Dark 🌙' : 'Light ☀️'} Mode`, 'info');
        });
      }
    },

    updateThemeToggleIcon(theme) {
      const icon = document.getElementById('theme-toggle-icon');
      if (icon) {
        icon.textContent = theme === 'dark' ? '☀️' : '🌙';
      }
      const toggleBtn = document.getElementById('theme-toggle-btn');
      if (toggleBtn) {
        toggleBtn.title = `Switch to ${theme === 'dark' ? 'Light' : 'Dark'} Mode`;
      }
    },

    handleImageError(imgEl, fallbackTitle = 'Heritage', region = 'India') {
      if (!imgEl) return;
      imgEl.onerror = null;
      imgEl.src = 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=800&q=80';
    },

    // ==========================================
    // ACCESSIBILITY (WCAG 2.2 AA) LIVE ANNOUNCER
    // ==========================================
    initA11yAnnouncer() {
      if (document.getElementById('dcha-a11y-announcer')) return;
      const announcer = document.createElement('div');
      announcer.id = 'dcha-a11y-announcer';
      announcer.className = 'sr-only';
      announcer.setAttribute('aria-live', 'polite');
      announcer.setAttribute('aria-atomic', 'true');
      document.body.appendChild(announcer);
    },

    announce(message) {
      if (!message) return;
      let announcer = document.getElementById('dcha-a11y-announcer');
      if (!announcer) {
        this.initA11yAnnouncer();
        announcer = document.getElementById('dcha-a11y-announcer');
      }
      if (announcer) {
        announcer.textContent = '';
        setTimeout(() => {
          announcer.textContent = message;
        }, 50);
      }
    },

    // ==========================================
    // PRIVACY & COOKIE CONSENT MANAGER (DPDP ACT 2023)
    // ==========================================
    COOKIE_CONSENT_KEY: 'dcha_cookie_consent',

    getCookieConsent() {
      try {
        const stored = localStorage.getItem(this.COOKIE_CONSENT_KEY);
        if (!stored) return null;
        return JSON.parse(stored);
      } catch (e) {
        return null;
      }
    },

    saveCookieConsent(prefs) {
      const consentRecord = {
        essential: true,
        analytics: Boolean(prefs && prefs.analytics),
        personalization: Boolean(prefs && prefs.personalization),
        timestamp: new Date().toISOString(),
        version: '1.0'
      };
      try {
        localStorage.setItem(this.COOKIE_CONSENT_KEY, JSON.stringify(consentRecord));
      } catch (e) {
        console.warn('Storage quota exceeded or private browsing active:', e);
      }

      const banner = document.getElementById('dcha-cookie-banner');
      if (banner) {
        banner.remove();
      }

      const prefModal = document.getElementById('cookie-preferences-modal');
      if (prefModal) {
        const overlay = prefModal.closest('.modal-overlay');
        if (overlay) overlay.remove();
      }

      this.announce('Your cookie and privacy preferences have been updated.');
      this.showToast('Cookie & privacy preferences saved', 'success');

      window.dispatchEvent(new CustomEvent('dcha-consent-updated', { detail: consentRecord }));
      return consentRecord;
    },

    renderCookieBanner() {
      if (this.getCookieConsent() || document.getElementById('dcha-cookie-banner')) return;

      const banner = document.createElement('aside');
      banner.id = 'dcha-cookie-banner';
      banner.className = 'cookie-banner-wrapper';
      banner.setAttribute('role', 'region');
      banner.setAttribute('aria-label', 'Privacy and Cookie Consent');

      banner.innerHTML = `
        <div class="cookie-banner-content">
          <div class="cookie-banner-text">
            <div class="cookie-banner-title">
              <span class="cookie-icon" aria-hidden="true">🍪</span>
              <span>Archival Storage & Privacy Consent</span>
            </div>
            <p class="cookie-banner-desc">
              The Indian Cultural Heritage Archive uses essential local storage for authentication, security, and theme settings. We do not use third-party advertising trackers or sell personal data. You may choose whether to enable optional analytical and personalization storage in accordance with the <strong>Digital Personal Data Protection Act, 2023</strong>. Learn more in our <a href="cookies.html" class="cookie-link">Cookie Policy</a>.
            </p>
          </div>
          <div class="cookie-banner-actions">
            <button type="button" id="cookie-btn-accept-all" class="btn btn-primary btn-sm">Accept All</button>
            <button type="button" id="cookie-btn-reject" class="btn btn-secondary btn-sm">Reject Non-Essential</button>
            <button type="button" id="cookie-btn-customize" class="btn btn-outline btn-sm">Customize</button>
          </div>
        </div>
      `;

      document.body.appendChild(banner);

      document.getElementById('cookie-btn-accept-all')?.addEventListener('click', () => {
        this.saveCookieConsent({ analytics: true, personalization: true });
      });

      document.getElementById('cookie-btn-reject')?.addEventListener('click', () => {
        this.saveCookieConsent({ analytics: false, personalization: false });
      });

      document.getElementById('cookie-btn-customize')?.addEventListener('click', () => {
        this.openCookiePreferences();
      });
    },

    openCookiePreferences() {
      const current = this.getCookieConsent() || { essential: true, analytics: false, personalization: false };

      const modalHtml = `
        <div id="cookie-preferences-modal" class="cookie-pref-container" style="max-width:540px;">
          <p style="font-size:0.9rem; color:var(--text-secondary); margin-bottom:16px; line-height:1.6;">
            Manage how the Indian Cultural Heritage Archive stores information on your device. Essential storage is required to maintain your active login and site theme; optional categories can be freely enabled or disabled.
          </p>

          <div class="cookie-pref-list" style="display:flex; flex-direction:column; gap:14px; margin-bottom:20px;">
            <!-- ESSENTIAL -->
            <div class="cookie-pref-item" style="display:flex; justify-content:space-between; align-items:flex-start; padding:12px; background:var(--bg-secondary); border-radius:8px; border:1px solid var(--border-color);">
              <div style="flex:1; padding-right:12px;">
                <div style="font-weight:700; color:var(--text-primary); font-size:0.95rem;">
                  1. Essential Archival Operations <span class="cookie-badge-req" style="font-size:0.7rem; background:#C6A15B; color:#1C1B18; padding:2px 6px; border-radius:4px; font-weight:800; margin-left:6px;">Always Required</span>
                </div>
                <div style="font-size:0.82rem; color:var(--text-muted); margin-top:4px; line-height:1.4;">
                  Manages your authentication token, encrypted session state, CSRF protection, and light/dark theme preference. Cannot be turned off.
                </div>
              </div>
              <input type="checkbox" checked disabled aria-label="Essential storage (Always required)" style="margin-top:4px; accent-color:#C6A15B; transform:scale(1.2);">
            </div>

            <!-- ANALYTICS -->
            <div class="cookie-pref-item" style="display:flex; justify-content:space-between; align-items:flex-start; padding:12px; background:var(--bg-secondary); border-radius:8px; border:1px solid var(--border-color);">
              <div style="flex:1; padding-right:12px;">
                <label for="pref-analytics-toggle" style="font-weight:700; color:var(--text-primary); font-size:0.95rem; cursor:pointer;">
                  2. Analytical & Performance Insights
                </label>
                <div style="font-size:0.82rem; color:var(--text-muted); margin-top:4px; line-height:1.4;">
                  Helps our curatorial team understand which heritage monographs and state archives are most visited, allowing us to allocate preservation resources effectively. No commercial tracking.
                </div>
              </div>
              <input type="checkbox" id="pref-analytics-toggle" ${current.analytics ? 'checked' : ''} style="margin-top:4px; accent-color:#C6A15B; transform:scale(1.2); cursor:pointer;">
            </div>

            <!-- PERSONALIZATION -->
            <div class="cookie-pref-item" style="display:flex; justify-content:space-between; align-items:flex-start; padding:12px; background:var(--bg-secondary); border-radius:8px; border:1px solid var(--border-color);">
              <div style="flex:1; padding-right:12px;">
                <label for="pref-personalization-toggle" style="font-weight:700; color:var(--text-primary); font-size:0.95rem; cursor:pointer;">
                  3. Personalization & Reading Bookmarks
                </label>
                <div style="font-size:0.82rem; color:var(--text-muted); margin-top:4px; line-height:1.4;">
                  Stores your recently viewed exhibits, timeline filters, audio playback positions, and custom curated bookmarks locally on your device.
                </div>
              </div>
              <input type="checkbox" id="pref-personalization-toggle" ${current.personalization ? 'checked' : ''} style="margin-top:4px; accent-color:#C6A15B; transform:scale(1.2); cursor:pointer;">
            </div>
          </div>

          <div style="display:flex; justify-content:flex-end; gap:10px; margin-top:16px;">
            <button type="button" id="cookie-modal-accept-all" class="btn btn-outline btn-sm">Accept All</button>
            <button type="button" id="cookie-modal-save" class="btn btn-primary btn-sm">Save Preferences</button>
          </div>
        </div>
      `;

      this.showModal(modalHtml, { title: '⚙️ Privacy & Archival Storage Settings' });

      document.getElementById('cookie-modal-accept-all')?.addEventListener('click', () => {
        this.saveCookieConsent({ analytics: true, personalization: true });
      });

      document.getElementById('cookie-modal-save')?.addEventListener('click', () => {
        const analytics = document.getElementById('pref-analytics-toggle')?.checked || false;
        const personalization = document.getElementById('pref-personalization-toggle')?.checked || false;
        this.saveCookieConsent({ analytics, personalization });
      });
    },

    initCookieConsent() {
      window.openCookiePreferences = () => this.openCookiePreferences();

      if (!this.getCookieConsent()) {
        setTimeout(() => {
          this.renderCookieBanner();
        }, 750);
      }
    }
  };

  window.components = components;
  window.DCHA_COMPONENTS = components;

  // Immediate theme initialization to prevent Flash of Unstyled Content
  (function() {
    const saved = localStorage.getItem('dcha_theme') || 'light';
    document.documentElement.setAttribute('data-theme', saved);
  })();

  document.addEventListener('DOMContentLoaded', () => {
    if (window.components) {
      window.components.initTheme();
      window.components.initAiAssistantFloatingButton();
      window.components.initA11yAnnouncer();
      window.components.initCookieConsent();
    }
  });
})();
