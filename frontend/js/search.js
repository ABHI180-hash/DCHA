// search.js - Comprehensive Global Search Engine for DCHA
document.addEventListener('DOMContentLoaded', () => {
  const searchInput = document.getElementById('search-input') || document.getElementById('global-search-input');
  const resultsContainer = document.getElementById('search-results') || document.getElementById('search-results-grid');
  const filterState = document.getElementById('filter-state');
  const filterCategory = document.getElementById('filter-category');

  if (!searchInput || !resultsContainer) return;

  function normalizeText(text) {
    if (!text) return '';
    let str = String(text).toLowerCase();
    const aliases = {
      'puduicherry': 'puducherry',
      'pondicherry': 'puducherry',
      'pondicherri': 'puducherry',
      'pondy': 'puducherry',
      'orissa': 'odisha',
      'calcutta': 'kolkata',
      'bombay': 'mumbai',
      'madras': 'chennai',
      'trivandrum': 'thiruvananthapuram',
      'banaras': 'varanasi',
      'benares': 'varanasi'
    };
    for (const [key, val] of Object.entries(aliases)) {
      if (str.includes(key)) {
        str = str.replace(new RegExp(key, 'g'), val);
      }
    }
    return str;
  }

  const performLocalSearch = (query, catVal = 'all') => {
    if (!window.DCHA_DATA) return;
    const normalizedQ = normalizeText(query);
    const tokens = normalizedQ ? normalizedQ.split(/\s+/).filter(Boolean) : [];

    const data = window.DCHA_DATA;
    let pool = [
      ...(data.STATES || []).map(s => ({ ...s, itemType: 'state', title: s.name })),
      ...(data.FEATURED_HERITAGE || []).map(h => ({ ...h, itemType: 'heritage', title: h.name })),
      ...(data.FESTIVALS || []).map(f => ({ ...f, itemType: 'festival', title: f.name, image: f.image_url || f.image })),
      ...(data.FOOD_ITEMS || []).map(fd => ({ ...fd, itemType: 'food', title: fd.name })),
      ...(data.TEXTILE_ITEMS || []).map(t => ({ ...t, itemType: 'textile', title: t.name })),
      ...(data.DANCE_MUSIC_ITEMS || []).map(d => ({ ...d, itemType: 'dance', title: d.name })),
      ...(data.ART_CRAFT_ITEMS || []).map(a => ({ ...a, itemType: 'art', title: a.name })),
      ...(data.LANGUAGE_ITEMS || []).map(l => ({ ...l, itemType: 'language', title: l.name })),
      ...(data.HERITAGE_MUSIC_ITEMS || []).map(m => ({ ...m, itemType: 'music', title: m.name })),
      ...(data.FOLK_INSTRUMENT_ITEMS || []).map(i => ({ ...i, itemType: 'instrument', title: i.name })),
      ...(data.HERITAGE_AT_RISK_ITEMS || []).map(r => ({ ...r, itemType: 'risk', title: r.site })),
      ...(data.INDIAN_MUSEUMS || []).map(m => ({ ...m, itemType: 'museum', title: m.name }))
    ];

    let results = pool.filter(item => {
      const matchCat = !catVal || catVal === 'all' || item.itemType === catVal;
      if (!matchCat) return false;
      if (tokens.length === 0) return true;

      const fieldsText = normalizeText([
        item.title || item.name || '',
        item.description || '',
        item.state || '',
        item.city || '',
        item.capital || '',
        item.region || '',
        item.category || '',
        item.type || '',
        item.technique || '',
        item.period || '',
        item.cultural_significance || '',
        item.context || '',
        item.provenance || '',
        Array.isArray(item.languages) ? item.languages.join(' ') : (item.languages || ''),
        Array.isArray(item.highlights) ? item.highlights.join(' ') : (item.highlights || ''),
        Array.isArray(item.key_artifacts) ? item.key_artifacts.join(' ') : (item.key_artifacts || '')
      ].join(' '));

      return tokens.every(token => fieldsText.includes(token));
    });

    if (results.length === 0) {
      resultsContainer.innerHTML = window.components.createEmptyState(`No archival items found matching "${query}". Try searching for another term.`);
    } else {
      resultsContainer.innerHTML = `<div class="editorial-grid-3">${results.map(r => window.components.createCard(r, r.itemType)).join('')}</div>`;
    }
  };

  const performSearch = async () => {
    const query = searchInput.value.trim();
    const stateVal = filterState ? filterState.value : '';
    const catVal = filterCategory ? filterCategory.value : '';
    
    // Always perform robust local search directly
    performLocalSearch(query, catVal);
  };

  const debouncedSearch = window.components ? window.components.debounce(performSearch, 200) : performSearch;

  searchInput.addEventListener('input', debouncedSearch);
  if (filterState) filterState.addEventListener('change', performSearch);
  if (filterCategory) filterCategory.addEventListener('change', performSearch);
  
  // Parse URL search parameters if arriving from header search input
  const urlParams = new URLSearchParams(window.location.search);
  const initialQ = urlParams.get('q') || urlParams.get('query') || urlParams.get('search');
  if (initialQ) {
    searchInput.value = initialQ;
  }

  performSearch();
});
