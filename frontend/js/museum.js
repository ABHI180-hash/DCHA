// museum.js - Virtual Museum
document.addEventListener('DOMContentLoaded', () => {
  const museumContainer = document.getElementById('museum-sections');
  if (!museumContainer || !window.DCHA_DATA) return;

  const sections = window.DCHA_DATA.MUSEUM_SECTIONS;
  
  const html = sections.map(section => `
    <div class="card museum-section">
      <div class="card-body text-center p-5">
        <i class="fas ${section.icon} fa-3x text-gold mb-3"></i>
        <h3>${section.name}</h3>
        <a href="#view-${section.id}" class="btn btn-outline mt-3">Enter Room</a>
      </div>
    </div>
  `).join('');

  museumContainer.innerHTML = `<div class="grid-3">${html}</div>`;
});
