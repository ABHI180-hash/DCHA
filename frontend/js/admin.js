// admin.js - Real Backend Admin Dashboard logic
document.addEventListener('DOMContentLoaded', async () => {
  if (!document.body.classList.contains('admin-mode')) return;

  // Verify auth & role
  if (window.auth) {
    const user = window.auth.getCurrentUser();
    if (!user || (user.role !== 'admin' && user.role !== 'curator')) {
      alert('Unauthorized access: Admin or Curator role required.');
      window.location.href = '/';
      return;
    }
  }

  // Sidebar toggle
  const toggleBtn = document.getElementById('admin-sidebar-toggle');
  const sidebar = document.querySelector('.admin-sidebar');
  if (toggleBtn && sidebar) {
    toggleBtn.addEventListener('click', () => {
      sidebar.classList.toggle('show');
    });
  }

  // Load Dashboard Stats
  const loadStats = async () => {
    try {
      const res = await window.api.get('/admin/stats');
      const stats = res.data || res || {};

      const totalUsersEl = document.getElementById('stat-users');
      const totalSitesEl = document.getElementById('stat-sites');
      const totalContribsEl = document.getElementById('stat-contribs');
      const pendingEl = document.getElementById('stat-pending');

      if (totalUsersEl) totalUsersEl.textContent = stats.totalUsers || 0;
      if (totalSitesEl) totalSitesEl.textContent = stats.totalHeritageSites || 0;
      if (totalContribsEl) totalContribsEl.textContent = stats.totalContributions || 0;
      if (pendingEl) pendingEl.textContent = stats.pendingContributions || 0;
    } catch (e) {
      console.warn('Admin stats fetch error:', e);
    }
  };

  // Load Contributions Table
  const loadContributions = async () => {
    const tableBody = document.getElementById('contributions-table-body');
    if (!tableBody) return;

    try {
      const res = await window.api.get('/admin/contributions');
      const items = res.data || res || [];

      if (items.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" class="text-center">No submissions recorded.</td></tr>';
        return;
      }

      tableBody.innerHTML = items.map(item => {
        const userName = (item.user && item.user.name) ? item.user.name : (item.user ? item.user : 'Anonymous');
        const statusClass = item.status === 'APPROVED' ? 'badge-green' : item.status === 'REJECTED' ? 'badge-red' : 'badge-gold';
        const createdDate = item.createdAt ? new Date(item.createdAt).toLocaleDateString() : 'N/A';

        return `
          <tr>
            <td>#${item.id}</td>
            <td><strong>${item.title}</strong></td>
            <td>${userName}</td>
            <td>${createdDate}</td>
            <td><span class="badge ${statusClass}">${item.status}</span></td>
            <td>
              ${item.status === 'PENDING' ? `
                <button class="btn btn-sm btn-success approve-btn" data-id="${item.id}">Approve</button>
                <button class="btn btn-sm btn-danger reject-btn" data-id="${item.id}">Reject</button>
              ` : `
                <span style="font-size:0.8rem; color:var(--muted-gray)">Reviewed</span>
              `}
            </td>
          </tr>
        `;
      }).join('');

      // Add Approve & Reject click handlers
      tableBody.querySelectorAll('.approve-btn').forEach(btn => {
        btn.addEventListener('click', () => reviewSubmission(btn.dataset.id, 'APPROVED'));
      });
      tableBody.querySelectorAll('.reject-btn').forEach(btn => {
        btn.addEventListener('click', () => reviewSubmission(btn.dataset.id, 'REJECTED'));
      });

    } catch (e) {
      console.error('Failed to load contributions:', e);
      tableBody.innerHTML = '<tr><td colspan="6" class="text-center color-danger">Error loading submissions from backend.</td></tr>';
    }
  };

  const reviewSubmission = async (id, newStatus) => {
    try {
      await window.api.put(`/admin/contributions/${id}/status`, { status: newStatus });
      window.components?.showToast(`Submission #${id} set to ${newStatus}`, 'success');
      await loadStats();
      await loadContributions();
    } catch (error) {
      window.components?.showToast(error.message || 'Review action failed', 'error');
    }
  };

  await loadStats();
  await loadContributions();
});
