// collections.js - Custom Archival Collections Management
(function() {
  const collectionsPage = {
    activeCollectionId: null,
    userCollections: [],

    init() {
      this.render();
    },

    async render() {
      const container = document.getElementById('user-collections-section');
      if (!container) return;

      if (!window.api || !window.api.isLoggedIn()) {
        container.innerHTML = `
          <div style="background:var(--cream); border:1px dashed var(--gold); padding:32px; text-align:center; border-radius:8px; margin-bottom:40px;">
            <div style="font-size:2.5rem; margin-bottom:12px;">📁</div>
            <h3 style="font-family:var(--font-heading, serif); color:var(--obsidian); margin-bottom:8px;">Your Personal Heritage Collections</h3>
            <p style="color:var(--warm-gray); max-width:550px; margin:0 auto 20px;">
              Log in to build custom archival anthologies, organize favorite heritage sites, regional traditions, and curate personal collections.
            </p>
            <div style="display:flex; gap:12px; justify-content:center;">
              <a href="login.html" class="btn btn-primary btn-sm">Log In to Your Account</a>
              <a href="register.html" class="btn btn-secondary btn-sm">Create New Account</a>
            </div>
          </div>
        `;
        return;
      }

      container.innerHTML = `
        <div style="margin-bottom:30px; display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:16px;">
          <div>
            <h2 style="font-family:var(--font-heading, serif); margin-bottom:4px; color:var(--obsidian);">My Custom Archival Collections</h2>
            <p style="color:var(--warm-gray); font-size:0.95rem; margin:0;">Organize and curate your personal selections of Indian heritage.</p>
          </div>
          <button id="btn-create-collection" class="btn btn-primary btn-sm">+ Create New Collection</button>
        </div>

        <div style="display:grid; grid-template-columns: 280px 1fr; gap:30px;" class="collections-main-grid">
          <div class="collections-sidebar" style="background:var(--white); border:1px solid rgba(0,0,0,0.08); padding:20px; border-radius:6px; height:fit-content;">
            <h4 style="font-size:1.1rem; border-bottom:2px solid var(--gold); padding-bottom:8px; margin-bottom:16px;">Your Collections</h4>
            <div id="collections-nav-list" style="display:flex; flex-direction:column; gap:8px;">
              <div style="color:var(--muted-gray); font-size:0.85rem; padding:10px; text-align:center;">Loading...</div>
            </div>
          </div>

          <div class="collections-content-area" id="collections-content-area">
            <div style="background:var(--white); border:1px solid rgba(0,0,0,0.08); padding:30px; text-align:center; border-radius:6px;">
              <p style="color:var(--muted-gray);">Select a collection on the left or create a new collection to get started.</p>
            </div>
          </div>
        </div>
      `;

      document.getElementById('btn-create-collection')?.addEventListener('click', () => this.openCreateCollectionModal());

      await this.loadCollections();
    },

    async loadCollections() {
      try {
        const res = await window.api.get('/collections');
        this.userCollections = (res && res.data) ? res.data : (Array.isArray(res) ? res : []);
        const navList = document.getElementById('collections-nav-list');

        if (!navList) return;

        if (this.userCollections.length === 0) {
          navList.innerHTML = `
            <div style="color:var(--warm-gray); font-size:0.85rem; padding:12px; text-align:center; background:#FAF8F5; border-radius:4px;">
              No custom collections yet. Click "+ Create New Collection" above!
            </div>
          `;
          document.getElementById('collections-content-area').innerHTML = `
            <div style="background:var(--white); border:1px solid rgba(0,0,0,0.08); padding:40px; text-align:center; border-radius:6px;">
              <div style="font-size:3rem; margin-bottom:12px;">📁</div>
              <h3>No Collections Created Yet</h3>
              <p style="color:var(--warm-gray); max-width:450px; margin:0 auto 20px;">Start curating by creating your first collection or browsing heritage sites and clicking "📁 Add to Collection".</p>
              <button onclick="window.collectionsPage.openCreateCollectionModal()" class="btn btn-primary btn-sm">+ Create First Collection</button>
            </div>
          `;
          return;
        }

        navList.innerHTML = this.userCollections.map((col, idx) => `
          <button class="col-nav-btn ${this.activeCollectionId === col.id || (!this.activeCollectionId && idx === 0) ? 'active' : ''}" 
                  data-id="${col.id}"
                  style="text-align:left; padding:12px 14px; border:1px solid ${this.activeCollectionId === col.id || (!this.activeCollectionId && idx === 0) ? 'var(--gold)' : 'rgba(0,0,0,0.08)'}; background:${this.activeCollectionId === col.id || (!this.activeCollectionId && idx === 0) ? 'var(--cream)' : 'var(--white)'}; border-radius:4px; cursor:pointer; font-family:inherit; transition:all 0.2s;">
            <strong style="display:block; font-size:0.95rem; color:var(--obsidian);">${col.name}</strong>
            <span style="font-size:0.75rem; color:var(--warm-gray);">${col.description || 'Collection'}</span>
          </button>
        `).join('');

        navList.querySelectorAll('.col-nav-btn').forEach(btn => {
          btn.addEventListener('click', () => {
            const colId = parseInt(btn.dataset.id, 10);
            this.selectCollection(colId);
          });
        });

        if (!this.activeCollectionId && this.userCollections.length > 0) {
          this.selectCollection(this.userCollections[0].id);
        } else if (this.activeCollectionId) {
          this.selectCollection(this.activeCollectionId);
        }
      } catch (err) {
        console.error("Failed to load user collections:", err);
      }
    },

    async selectCollection(colId) {
      this.activeCollectionId = colId;
      const collection = this.userCollections.find(c => c.id === colId);
      
      const navBtns = document.querySelectorAll('.col-nav-btn');
      navBtns.forEach(btn => {
        const isCurrent = parseInt(btn.dataset.id, 10) === colId;
        btn.style.background = isCurrent ? 'var(--cream)' : 'var(--white)';
        btn.style.borderColor = isCurrent ? 'var(--gold)' : 'rgba(0,0,0,0.08)';
      });

      const contentArea = document.getElementById('collections-content-area');
      if (!contentArea || !collection) return;

      contentArea.innerHTML = `
        <div style="background:var(--white); border:1px solid rgba(0,0,0,0.08); padding:24px; border-radius:6px; margin-bottom:24px; display:flex; justify-content:space-between; align-items:flex-start; flex-wrap:wrap; gap:16px;">
          <div>
            <span class="catalogue-badge" style="margin-bottom:8px;">Custom Collection</span>
            <h3 style="font-size:1.6rem; color:var(--obsidian); margin:6px 0;">${collection.name}</h3>
            <p style="color:var(--warm-gray); font-size:0.95rem; margin:0;">${collection.description || 'Archival Anthology'}</p>
          </div>
          <button class="btn btn-outline btn-sm" id="btn-delete-col" style="color:var(--danger); border-color:var(--danger);">🗑 Delete Collection</button>
        </div>

        <div id="collection-items-grid" class="editorial-grid-3">
          <div style="grid-column:1/-1; text-align:center; padding:30px; color:var(--muted-gray);">Loading items...</div>
        </div>
      `;

      document.getElementById('btn-delete-col')?.addEventListener('click', () => this.deleteCollection(colId, collection.name));

      // Fetch Items
      try {
        const res = await window.api.get(`/collections/${colId}/items`);
        const items = (res && res.data) ? res.data : (Array.isArray(res) ? res : []);
        const itemsGrid = document.getElementById('collection-items-grid');

        if (!itemsGrid) return;

        if (items.length === 0) {
          itemsGrid.innerHTML = `
            <div style="grid-column:1/-1; background:var(--white); border:1px solid rgba(0,0,0,0.08); padding:40px; text-align:center; border-radius:6px;">
              <div style="font-size:2.5rem; margin-bottom:12px;">🏛</div>
              <h4>No Items in this Collection Yet</h4>
              <p style="color:var(--warm-gray); font-size:0.9rem;">Browse heritage sites or states across DCHA and click "📁 Add to Collection".</p>
            </div>
          `;
          return;
        }

        itemsGrid.innerHTML = items.map(item => this.renderCollectionItemCard(item, colId)).join('');

        // Remove item buttons listeners
        itemsGrid.querySelectorAll('.remove-col-item-btn').forEach(btn => {
          btn.addEventListener('click', async (e) => {
            e.preventDefault();
            const itemId = btn.dataset.itemId;
            btn.disabled = true;
            btn.textContent = 'Removing...';
            try {
              await window.api.delete(`/collections/${colId}/items/${itemId}`);
              window.components?.showToast('Item removed from collection', 'info');
              this.selectCollection(colId);
            } catch (err) {
              btn.disabled = false;
              btn.textContent = 'Remove';
              window.components?.showToast(err.message || 'Failed to remove item', 'error');
            }
          });
        });

      } catch (err) {
        console.error("Failed to fetch collection items:", err);
      }
    },

    renderCollectionItemCard(colItem, collectionId) {
      // Resolve details from DCHA_DATA
      let itemDetails = null;
      const type = (colItem.itemType || 'heritage').toLowerCase();
      const itemId = colItem.itemId;

      if (window.DCHA_DATA) {
        if (type === 'heritage') {
          itemDetails = window.DCHA_DATA.FEATURED_HERITAGE.find(h => h.id == itemId);
        } else if (type === 'state') {
          itemDetails = window.DCHA_DATA.STATES.find(s => s.id == itemId || s.slug == itemId);
        } else if (type === 'festival') {
          itemDetails = window.DCHA_DATA.FESTIVALS.find(f => f.id == itemId);
        } else if (type === 'food') {
          itemDetails = window.DCHA_DATA.FOOD_ITEMS.find(f => f.id == itemId);
        } else if (type === 'textile') {
          itemDetails = window.DCHA_DATA.TEXTILE_ITEMS.find(t => t.id == itemId);
        } else if (type === 'art' || type === 'craft') {
          itemDetails = window.DCHA_DATA.ART_CRAFT_ITEMS.find(a => a.id == itemId);
        }
      }

      const title = itemDetails ? (itemDetails.name || itemDetails.title) : `Heritage Record #${itemId}`;
      const image = itemDetails ? (itemDetails.image || itemDetails.image_url) : 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=800&q=80';
      const location = itemDetails ? (itemDetails.state || itemDetails.capital || 'India') : 'Archival Directory';
      const targetPage = type === 'state' ? 'state-detail' : type === 'heritage' ? 'heritage-detail' : 'explore';

      return `
        <div class="archival-card" style="position:relative;">
          <div class="card-media-wrapper">
            <img src="${image}" alt="${title}" class="card-media" loading="lazy">
            <span class="catalogue-badge" style="text-transform:capitalize;">${type}</span>
          </div>
          <div class="card-content">
            <h3><a href="${targetPage}.html?id=${itemId}">${title}</a></h3>
            <div class="meta-location">📍 ${location}</div>
            <div style="margin-top:16px; display:flex; justify-content:space-between; align-items:center;">
              <a href="${targetPage}.html?id=${itemId}" class="view-link">View Details →</a>
              <button type="button" class="btn btn-outline btn-sm remove-col-item-btn" data-item-id="${colItem.id}" style="color:var(--danger); border-color:rgba(220,53,69,0.4); padding:3px 8px; font-size:0.75rem;">
                Remove
              </button>
            </div>
          </div>
        </div>
      `;
    },

    openCreateCollectionModal() {
      const content = `
        <form id="create-collection-modal-form" style="display:flex; flex-direction:column; gap:14px; padding:6px 0;">
          <div>
            <label style="font-weight:600; font-size:0.9rem; margin-bottom:4px; display:block;">Collection Name *</label>
            <input type="text" id="new-col-name-input" class="form-control" placeholder="e.g., Sacred Temples of South India" required style="width:100%; padding:10px; border:1px solid #CCC; border-radius:4px;">
          </div>
          <div>
            <label style="font-weight:600; font-size:0.9rem; margin-bottom:4px; display:block;">Description (Optional)</label>
            <textarea id="new-col-desc-input" class="form-control" placeholder="Describe the theme or scope of this anthology..." rows="3" style="width:100%; padding:10px; border:1px solid #CCC; border-radius:4px;"></textarea>
          </div>
          <button type="submit" class="btn btn-primary" style="margin-top:10px;">Create Collection</button>
        </form>
      `;

      window.components?.showModal(content, { title: '+ Create New Archival Collection' });

      const form = document.getElementById('create-collection-modal-form');
      form?.addEventListener('submit', async (e) => {
        e.preventDefault();
        const name = document.getElementById('new-col-name-input').value.trim();
        const description = document.getElementById('new-col-desc-input').value.trim();
        const submitBtn = form.querySelector('button[type="submit"]');

        if (!name) return;

        submitBtn.disabled = true;
        submitBtn.textContent = 'Creating...';

        try {
          const res = await window.api.post('/collections', {
            name,
            description: description || 'Custom Archival Anthology',
            isPublic: true
          });
          window.components?.showToast('Collection created successfully.', 'success');
          const overlay = form.closest('.modal-overlay');
          if (overlay) overlay.remove();
          
          await this.loadCollections();
          if (res && res.data && res.data.id) {
            this.selectCollection(res.data.id);
          }
        } catch (err) {
          submitBtn.disabled = false;
          submitBtn.textContent = 'Create Collection';
          window.components?.showToast(err.message || 'Failed to create collection', 'error');
        }
      });
    },

    async deleteCollection(colId, name) {
      if (!confirm(`Are you sure you want to delete collection "${name}"? This action cannot be undone.`)) {
        return;
      }

      try {
        await window.api.delete(`/collections/${colId}`);
        window.components?.showToast(`Collection "${name}" deleted.`, 'info');
        this.activeCollectionId = null;
        await this.loadCollections();
      } catch (err) {
        window.components?.showToast(err.message || 'Failed to delete collection', 'error');
      }
    }
  };

  window.collectionsPage = collectionsPage;

  document.addEventListener('DOMContentLoaded', () => {
    collectionsPage.init();
  });
})();
