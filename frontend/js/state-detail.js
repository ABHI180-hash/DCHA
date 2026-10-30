// state-detail.js - Dynamic State & UT Heritage Detail Page Engine
document.addEventListener('DOMContentLoaded', async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const rawId = urlParams.get('id') || urlParams.get('state') || 'west-bengal';
  
  if (window.components) {
    window.components.createNav('states');
    window.components.createFooter();
  }

  function normalizeText(text) {
    if (!text) return '';
    let str = String(text).toLowerCase();
    const aliases = {
      'puduicherry': 'puducherry', 'pondicherry': 'puducherry', 'pondicherri': 'puducherry',
      'pondy': 'puducherry', 'orissa': 'odisha', 'calcutta': 'kolkata', 'bombay': 'mumbai'
    };
    for (const [k, v] of Object.entries(aliases)) {
      if (str.includes(k)) str = str.replace(new RegExp(k, 'g'), v);
    }
    return str;
  }

  function resolveStateFromParam(param) {
    if (!param || !window.DCHA_DATA || !window.DCHA_DATA.STATES) return null;
    const cleanParam = String(param).trim().toLowerCase().replace(/_/g, '-');
    
    const ALIASES = {
      'od': 'or', 'odisha': 'or',
      'uk': 'ut', 'uttarakhand': 'ut',
      'dh': 'dn', 'dadra-and-nagar-haveli-and-daman-and-diu': 'dn',
      'andaman-and-nicobar-islands': 'an',
      'jammu-and-kashmir': 'jk'
    };

    const target = ALIASES[cleanParam] || cleanParam;
    const states = window.DCHA_DATA.STATES;

    let found = states.find(s => s.id && s.id.toLowerCase() === target);
    if (found) return found;
    found = states.find(s => s.slug && s.slug.toLowerCase() === target);
    if (found) return found;
    found = states.find(s => s.name && s.name.toLowerCase() === target);
    if (found) return found;
    found = states.find(s => s.name && s.name.toLowerCase().replace(/\s+/g, '-') === target);
    if (found) return found;
    if (target.length >= 3) {
      found = states.find(s => s.name.toLowerCase().includes(target) || (s.slug && s.slug.toLowerCase().includes(target)));
    }
    return found;
  }

  const rawParam = rawId || sessionStorage.getItem('selected_state_id');
  let state = resolveStateFromParam(rawParam);

  // Try fetching state from live REST API if not found
  if (!state) {
    try {
      const res = await window.api.get(`/states/${encodeURIComponent(clean)}`);
      if (res && res.success && res.data) {
        state = res.data;
      }
    } catch (err) {
      console.warn("Failed to fetch state from backend API:", err);
    }
  }

  if (!state) {
    const nameFormatted = rawId.split('-').map(w => w.charAt(0).toUpperCase() + w.slice(1)).join(' ');
    state = {
      id: rawId,
      slug: rawId,
      name: nameFormatted,
      capital: 'Regional Capital',
      region: 'India',
      type: 'state',
      description: `Welcome to the ${nameFormatted} heritage archive.`
    };
  }

  // Set page title & hero elements
  document.title = `${state.name} Archive — Digital Culture Heritage Archive`;
  const heroEl = document.getElementById('state-hero');
  if (heroEl && (state.imageUrl || state.image)) {
    heroEl.style.backgroundImage = `linear-gradient(180deg, rgba(13,14,13,0.4) 0%, rgba(13,14,13,0.92) 100%), url('${state.imageUrl || state.image}')`;
  }
  
  const nameEl = document.getElementById('hero-state-name');
  if (nameEl) nameEl.textContent = state.name;

  const typeBadge = document.getElementById('hero-state-type');
  if (typeBadge) typeBadge.textContent = state.type === 'union_territory' ? 'UNION TERRITORY ARCHIVE' : 'STATE ARCHIVE';

  const metaCap = document.getElementById('meta-capital');
  if (metaCap) metaCap.textContent = state.capital || 'N/A';

  const metaReg = document.getElementById('meta-region');
  if (metaReg) metaReg.textContent = (state.region || 'India').toUpperCase();

  const metaLang = document.getElementById('meta-languages');
  if (metaLang) metaLang.textContent = Array.isArray(state.languages) ? state.languages.join(', ') : (state.languages || 'N/A');

  const metaYear = document.getElementById('meta-year');
  if (metaYear) metaYear.textContent = state.establishedYear || state.established_year || 'Historical';

  const overviewSig = document.getElementById('overview-significance');
  if (overviewSig) overviewSig.textContent = state.culturalSignificance || state.cultural_significance || state.description;

  const overviewImg = document.getElementById('overview-img');
  if (overviewImg) {
    overviewImg.src = state.imageUrl || state.image || 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=1000&q=80';
    overviewImg.alt = `${state.name} Cultural Heritage`;
    overviewImg.onerror = function() {
      this.onerror = null;
      this.src = 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=1000&q=80';
    };
  }

  const overviewCredit = document.getElementById('overview-credit');
  if (overviewCredit) {
    overviewCredit.textContent = state.image_credit || state.image_source || `Photo: ${state.name} Cultural Heritage Archives`;
  }

  // Populate state highlights list dynamically
  const highlightsList = document.getElementById('overview-highlights-list');
  if (highlightsList && Array.isArray(state.highlights) && state.highlights.length > 0) {
    highlightsList.innerHTML = state.highlights.map(h => `<li>${h}</li>`).join('');
  }

  // Helper to load category grid with local DCHA_DATA fallback
  const loadCategoryGrid = async (endpoint, elementId, categoryLabel, localItemsFn) => {
    const grid = document.getElementById(elementId);
    if (!grid) return;

    let items = [];
    try {
      const res = await window.api.get(`/states/${encodeURIComponent(state.slug || state.id)}/${endpoint}`);
      if (res && res.success && Array.isArray(res.data) && res.data.length > 0) {
        items = res.data;
      }
    } catch (e) {
      // Ignore REST API failure
    }

    // Fallback to static DCHA_DATA if API returned empty
    if (items.length === 0 && typeof localItemsFn === 'function') {
      items = localItemsFn();
    }

    if (items.length === 0) {
      grid.innerHTML = `<div style="grid-column: 1/-1; padding: 40px; text-align: center; background: #FFF8F3; border: 1px dashed var(--bronze); border-radius: 8px;">
        <h4 style="color:var(--terracotta); margin-bottom: 8px;">No ${categoryLabel} content available yet</h4>
        <p style="color:var(--warm-gray); font-size: 0.9rem;">No ${categoryLabel.toLowerCase()} records currently archived for ${state.name}. Admins can contribute new records via the Admin Portal.</p>
      </div>`;
      return;
    }

    grid.innerHTML = items.map(item => {
      const img = item.imageUrl || item.image || item.image_url || 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=600&q=80';
      const title = item.name || item.title || categoryLabel;
      const desc = item.description || item.culturalSignificance || item.historicalSignificance || 'Preserved cultural heritage.';
      const loc = item.location || item.state || state.name;

      return `
        <div class="card card-heritage">
          <div class="card-img-wrap" style="height:200px; overflow:hidden;">
            <img src="${img}" alt="${title}" style="width:100%; height:100%; object-fit:cover;" onerror="this.onerror=null; this.src='https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=600&q=80';">
          </div>
          <div class="card-content" style="padding:16px;">
            <span class="catalogue-tag">${loc}</span>
            <h4 style="margin:8px 0; font-family:var(--font-heading);">${title}</h4>
            <p style="color:var(--warm-gray); font-size:0.85rem; line-height:1.4;">${desc.length > 120 ? desc.substring(0, 120) + '...' : desc}</p>
          </div>
        </div>
      `;
    }).join('');
  };

  const isStateMatch = (itemState) => {
    if (!itemState) return false;
    const s1 = normalizeText(itemState);
    const s2 = normalizeText(state.name);
    return s1.includes(s2) || s2.includes(s1);
  };

  loadCategoryGrid('heritage', 'state-heritage-grid', 'Monuments', () => {
    return (window.DCHA_DATA.FEATURED_HERITAGE || []).filter(h => h.state_id === state.id || isStateMatch(h.state));
  });

  loadCategoryGrid('festivals', 'state-festivals-grid', 'Festivals & Rituals', () => {
    return (window.DCHA_DATA.FESTIVALS || []).filter(f => isStateMatch(f.state));
  });

  loadCategoryGrid('foods', 'state-food-grid', 'Gastronomy', () => {
    return (window.DCHA_DATA.FOOD_ITEMS || []).filter(f => isStateMatch(f.state));
  });

  loadCategoryGrid('dresses', 'state-textiles-grid', 'Textiles & Attire', () => {
    return (window.DCHA_DATA.TEXTILE_ITEMS || []).filter(t => isStateMatch(t.state));
  });

  loadCategoryGrid('dances', 'state-arts-grid', 'Performing Arts & Crafts', () => {
    const dances = (window.DCHA_DATA.DANCE_MUSIC_ITEMS || []).filter(d => isStateMatch(d.state));
    const arts = (window.DCHA_DATA.ART_CRAFT_ITEMS || []).filter(a => isStateMatch(a.state));
    return [...dances, ...arts];
  });

  // Tab switching logic
  const tabs = document.querySelectorAll('.tab');
  const panes = document.querySelectorAll('.tab-pane');
  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      panes.forEach(p => p.classList.remove('active'));
      tab.classList.add('active');
      const targetPane = document.getElementById(tab.dataset.target);
      if (targetPane) targetPane.classList.add('active');
    });
  });

  const favBtn = document.getElementById('fav-state-btn');
  if (favBtn) {
    favBtn.addEventListener('click', () => {
      window.components.showToast(`Saved ${state.name} to your collection!`, 'success');
    });
  }
});
