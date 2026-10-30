// admin-heritage.js - Admin Heritage Content CRUD Engine
document.addEventListener('DOMContentLoaded', async () => {
  const token = localStorage.getItem('dcha_token');
  const user = JSON.parse(localStorage.getItem('dcha_user') || '{}');

  if (!token || (user.role !== 'admin' && user.role !== 'curator')) {
    alert('Unauthorized: Admin access required.');
    window.location.href = '../login.html';
    return;
  }

  const stateFilter = document.getElementById('admin-state-filter');
  const categoryFilter = document.getElementById('admin-category-filter');
  const searchInput = document.getElementById('admin-search-input');
  const tableBody = document.getElementById('admin-heritage-table-body');

  const modal = document.getElementById('heritage-modal');
  const modalTitle = document.getElementById('modal-title');
  const modalCategory = document.getElementById('modal-category');
  const modalStateId = document.getElementById('modal-state-id');
  const modalName = document.getElementById('modal-name');
  const modalLocation = document.getElementById('modal-location');
  const modalImageUrl = document.getElementById('modal-image-url');
  const modalImgPreview = document.getElementById('modal-img-preview');
  const modalDescription = document.getElementById('modal-description');
  const modalSignificance = document.getElementById('modal-significance');
  const modalRecordId = document.getElementById('modal-record-id');
  const modalMode = document.getElementById('modal-mode');
  const heritageForm = document.getElementById('heritage-form');

  let allStates = [];
  let allRecords = [];

  // Load States for filters & dropdowns
  const loadStates = async () => {
    try {
      const res = await window.api.get('/states');
      if (res && res.success && Array.isArray(res.data)) {
        allStates = res.data;
      }
    } catch (e) {
      console.warn('Backend state fetch error, using fallback:', e);
      allStates = window.DCHA_DATA?.STATES || [];
    }

    // Populate dropdowns
    if (stateFilter) {
      stateFilter.innerHTML = '<option value="all">All States & UTs</option>';
      allStates.forEach(st => {
        const opt = document.createElement('option');
        opt.value = st.id;
        opt.textContent = st.name;
        stateFilter.appendChild(opt);
      });
    }

    if (modalStateId) {
      modalStateId.innerHTML = '<option value="">-- Select State --</option>';
      allStates.forEach(st => {
        const opt = document.createElement('option');
        opt.value = st.id;
        opt.textContent = st.name;
        modalStateId.appendChild(opt);
      });
    }
  };

  // Load All Heritage Content from Admin API
  const loadHeritageContent = async () => {
    tableBody.innerHTML = '<tr><td colspan="6" style="text-align:center; padding:30px; color:#888;">Loading heritage records from backend...</td></tr>';
    try {
      const res = await window.api.get('/admin/heritage/all');
      if (res && res.success && Array.isArray(res.data)) {
        allRecords = res.data;
      }
    } catch (e) {
      console.error('Failed to fetch admin heritage content:', e);
      allRecords = [];
    }
    renderTable();
  };

  const renderTable = () => {
    const catVal = categoryFilter ? categoryFilter.value : 'all';
    const stateVal = stateFilter ? stateFilter.value : 'all';
    const query = searchInput ? searchInput.value.trim().toLowerCase() : '';

    let filtered = allRecords.filter(rec => {
      const matchCat = (catVal === 'all') || (rec.category === catVal);
      const matchState = (stateVal === 'all') || (String(rec.stateId) === String(stateVal));
      const matchSearch = (!query) || 
        (rec.name && rec.name.toLowerCase().includes(query)) ||
        (rec.location && rec.location.toLowerCase().includes(query)) ||
        (rec.stateName && rec.stateName.toLowerCase().includes(query));
      return matchCat && matchState && matchSearch;
    });

    if (filtered.length === 0) {
      tableBody.innerHTML = '<tr><td colspan="6" style="text-align:center; padding:30px; color:#888;">No heritage records match your filters.</td></tr>';
      return;
    }

    tableBody.innerHTML = filtered.map(rec => {
      const thumb = rec.imageUrl || 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=100&q=80';
      return `
        <tr>
          <td>
            <img src="${thumb}" style="width:48px; height:48px; object-fit:cover; border-radius:4px; border:1px solid #eee;" onerror="this.onerror=null; this.src='https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=100&q=80';">
          </td>
          <td>
            <strong style="color:#1a1a1a;">${rec.name || 'Untitled'}</strong>
          </td>
          <td>
            <span style="background:#f0f4f8; color:#0056b3; padding:3px 8px; border-radius:4px; font-size:12px; font-weight:600;">${rec.categoryName || rec.category}</span>
          </td>
          <td>${rec.stateName || 'All India'}</td>
          <td style="color:#666; font-size:13px;">${rec.location || 'N/A'}</td>
          <td style="text-align:right;">
            <button class="btn-action edit edit-btn" data-id="${rec.id}" data-category="${rec.category}">Edit</button>
            <button class="btn-action delete delete-btn" data-id="${rec.id}" data-category="${rec.category}" data-name="${rec.name}">Delete</button>
          </td>
        </tr>
      `;
    }).join('');

    // Attach Event Listeners to Edit & Delete buttons
    tableBody.querySelectorAll('.edit-btn').forEach(btn => {
      btn.addEventListener('click', () => openEditModal(btn.dataset.id, btn.dataset.category));
    });

    tableBody.querySelectorAll('.delete-btn').forEach(btn => {
      btn.addEventListener('click', () => confirmDelete(btn.dataset.id, btn.dataset.category, btn.dataset.name));
    });
  };

  // Image URL Preview
  if (modalImageUrl) {
    modalImageUrl.addEventListener('input', () => {
      const val = modalImageUrl.value.trim();
      if (val) {
        modalImgPreview.src = val;
        modalImgPreview.style.display = 'block';
      } else {
        modalImgPreview.style.display = 'none';
      }
    });
  }

  // Open Add Modal
  const openAddModal = () => {
    modalMode.value = 'create';
    modalRecordId.value = '';
    modalTitle.textContent = 'Add New Heritage Content';
    heritageForm.reset();
    modalImgPreview.style.display = 'none';
    modal.style.display = 'flex';
  };

  // Open Edit Modal
  const openEditModal = (id, category) => {
    const rec = allRecords.find(r => String(r.id) === String(id) && r.category === category);
    if (!rec) return;

    modalMode.value = 'update';
    modalRecordId.value = id;
    modalTitle.textContent = `Edit Heritage Record #${id}`;
    modalCategory.value = category;
    modalStateId.value = rec.stateId || '';
    modalName.value = rec.name || '';
    modalLocation.value = rec.location || '';
    modalImageUrl.value = rec.imageUrl || '';
    modalDescription.value = rec.description || '';
    modalSignificance.value = rec.significance || '';

    if (rec.imageUrl) {
      modalImgPreview.src = rec.imageUrl;
      modalImgPreview.style.display = 'block';
    } else {
      modalImgPreview.style.display = 'none';
    }

    modal.style.display = 'flex';
  };

  // Confirm Delete
  const confirmDelete = async (id, category, name) => {
    if (!confirm(`Are you sure you want to delete "${name || 'this record'}" from ${category}? This action cannot be undone.`)) {
      return;
    }

    try {
      await window.api.delete(`/admin/heritage/${category}/${id}`);
      window.components?.showToast(`Deleted "${name}" successfully`, 'success');
      await loadHeritageContent();
    } catch (e) {
      alert(`Delete error: ${e.message || 'Failed to delete record'}`);
    }
  };

  // Handle Form Submission
  heritageForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const category = modalCategory.value;
    const isUpdate = modalMode.value === 'update';
    const recordId = modalRecordId.value;

    const payload = {
      stateId: modalStateId.value ? parseInt(modalStateId.value) : null,
      name: modalName.value.trim(),
      title: modalName.value.trim(),
      location: modalLocation.value.trim(),
      region: modalLocation.value.trim(),
      imageUrl: modalImageUrl.value.trim(),
      description: modalDescription.value.trim(),
      content: modalDescription.value.trim(),
      culturalSignificance: modalSignificance.value.trim(),
      historicalSignificance: modalSignificance.value.trim(),
      significance: modalSignificance.value.trim()
    };

    try {
      if (isUpdate) {
        await window.api.put(`/admin/heritage/${category}/${recordId}`, payload);
        window.components?.showToast('Heritage record updated successfully!', 'success');
      } else {
        await window.api.post(`/admin/heritage/${category}`, payload);
        window.components?.showToast('New heritage record created successfully!', 'success');
      }

      modal.style.display = 'none';
      await loadHeritageContent();
    } catch (err) {
      alert(`Save Error: ${err.message || 'Failed to save heritage record.'}`);
    }
  });

  // Modal Open/Close Controls
  document.getElementById('open-add-modal-btn')?.addEventListener('click', openAddModal);
  document.getElementById('close-modal-btn')?.addEventListener('click', () => modal.style.display = 'none');
  document.getElementById('cancel-modal-btn')?.addEventListener('click', () => modal.style.display = 'none');

  // Filter Event Listeners
  categoryFilter?.addEventListener('change', renderTable);
  stateFilter?.addEventListener('change', renderTable);
  searchInput?.addEventListener('input', renderTable);

  // Initialize
  await loadStates();
  await loadHeritageContent();
});
