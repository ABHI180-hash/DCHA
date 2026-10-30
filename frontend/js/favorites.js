// favorites.js - Real Backend API Favorites Management
(function() {
  const favorites = {
    async fetchUserFavorites() {
      if (window.api && window.api.isLoggedIn()) {
        try {
          const res = await window.api.get('/favorites');
          const list = res.data || res || [];
          localStorage.setItem('dcha_favs', JSON.stringify(list));
          return list;
        } catch (e) {
          console.error("Failed to fetch favorites:", e);
        }
      }
      return JSON.parse(localStorage.getItem('dcha_favs') || '[]');
    },

    getFavorites() {
      return JSON.parse(localStorage.getItem('dcha_favs') || '[]');
    },

    isFavorited(itemId, itemType = 'heritage') {
      const favs = this.getFavorites();
      return favs.some(f => (f.itemId == itemId || f == itemId) && (f.itemType ? f.itemType === itemType : true));
    },

    async toggleFavorite(itemId, itemType = 'heritage') {
      if (!window.api || !window.api.isLoggedIn()) {
        window.auth?.showLoginPrompt();
        return false;
      }

      const isFav = this.isFavorited(itemId, itemType);
      try {
        if (isFav) {
          await window.api.delete(`/favorites/item?itemType=${encodeURIComponent(itemType)}&itemId=${encodeURIComponent(itemId)}`);
          window.components?.showToast('Removed from favorites', 'info');
        } else {
          await window.api.post('/favorites', { itemType, itemId: parseInt(itemId) || 1 });
          window.components?.showToast('Added to favorites', 'success');
        }
        await this.fetchUserFavorites();
        return !isFav;
      } catch (error) {
        window.components?.showToast(error.message || 'Error updating favorites', 'error');
        return isFav;
      }
    },

    createFavoriteBtn(itemId, itemType = 'heritage') {
      const isFav = this.isFavorited(itemId, itemType);
      const btn = document.createElement('button');
      btn.className = 'btn-icon';
      btn.style.color = isFav ? 'var(--danger)' : 'var(--muted-gray)';
      btn.innerHTML = `<i class="${isFav ? 'fas' : 'far'} fa-heart"></i>`;
      
      btn.addEventListener('click', async (e) => {
        e.preventDefault();
        const nowFav = await this.toggleFavorite(itemId, itemType);
        btn.style.color = nowFav ? 'var(--danger)' : 'var(--muted-gray)';
        btn.innerHTML = `<i class="${nowFav ? 'fas' : 'far'} fa-heart"></i>`;
      });
      return btn;
    }
  };

  window.favorites = favorites;
  window.DCHA_FAVORITES = favorites;

  document.addEventListener('DOMContentLoaded', () => {
    if (window.api && window.api.isLoggedIn()) {
      favorites.fetchUserFavorites();
    }
  });
})();
