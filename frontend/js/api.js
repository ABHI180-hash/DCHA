// api.js
(function() {
  const API_BASE = window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1' 
    ? 'http://localhost:8085/api' 
    : '/api';

  const api = {
    getToken() {
      return localStorage.getItem('dcha_token');
    },
    setToken(token) {
      localStorage.setItem('dcha_token', token);
    },
    removeToken() {
      localStorage.removeItem('dcha_token');
    },
    isLoggedIn() {
      return !!this.getToken();
    },
    getUser() {
      const user = localStorage.getItem('dcha_user');
      return user ? JSON.parse(user) : null;
    },
    setUser(user) {
      localStorage.setItem('dcha_user', JSON.stringify(user));
    },

    async request(endpoint, options = {}) {
      const url = `${API_BASE}${endpoint}`;
      const headers = {
        'Content-Type': 'application/json',
        ...options.headers
      };

      const token = this.getToken();
      // Only attach Bearer if token looks like a valid JWT (3 parts separated by dot)
      if (token && token.split('.').length === 3) {
        headers['Authorization'] = `Bearer ${token}`;
      }

      const config = {
        ...options,
        headers
      };

      try {
        const response = await fetch(url, config);
        const data = await response.json().catch(() => null);

        if (!response.ok) {
          const isAuthEndpoint = endpoint.includes('/auth/login') || endpoint.includes('/auth/register') || endpoint.includes('/auth/signup');
          if (response.status === 401 && !isAuthEndpoint) {
            this.removeToken();
            localStorage.removeItem('dcha_user');
            // Protected resource failed auth
            if (window.location.pathname.includes('profile.html') || window.location.pathname.includes('admin/')) {
              window.location.href = 'login.html';
            }
          }
          const errorMsg = (data && data.message) || response.statusText || 'Request failed';
          const err = new Error(errorMsg);
          err.status = response.status;
          err.data = data;
          throw err;
        }
        return data;
      } catch (error) {
        // Re-throw so caller handles specific error
        throw error;
      }
    },

    async get(endpoint, params = {}) {
      const qs = new URLSearchParams(params).toString();
      const url = qs ? `${endpoint}?${qs}` : endpoint;
      return this.request(url, { method: 'GET' });
    },

    async post(endpoint, data) {
      return this.request(endpoint, {
        method: 'POST',
        body: JSON.stringify(data)
      });
    },

    async put(endpoint, data) {
      return this.request(endpoint, {
        method: 'PUT',
        body: JSON.stringify(data)
      });
    },

    async patch(endpoint, data) {
      return this.request(endpoint, {
        method: 'PATCH',
        body: JSON.stringify(data)
      });
    },

    async delete(endpoint) {
      return this.request(endpoint, { method: 'DELETE' });
    }
  };

  window.api = api;
  window.DCHA_API = { BASE: API_BASE, api };
})();
