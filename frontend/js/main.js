// main.js - Homepage specific logic
document.addEventListener('DOMContentLoaded', () => {
  if (window.components) {
    window.components.createNav('home');
    window.components.createFooter();
  }

  // Load featured states (Mock)
  const featuredStatesContainer = document.getElementById('featured-states');
  if (featuredStatesContainer && window.DCHA_DATA) {
    const featured = window.DCHA_DATA.STATES.slice(0, 3);
    featuredStatesContainer.innerHTML = featured.map(state => window.components.createCard(state, 'state')).join('');
  }

  // Smooth scroll
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      document.querySelector(this.getAttribute('href'))?.scrollIntoView({
        behavior: 'smooth'
      });
    });
  });

  // Intersection Observer for scroll animations
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('animate-slide-up');
      }
    });
  }, { threshold: 0.1 });

  document.querySelectorAll('.card, .section-header').forEach((el) => {
    el.style.opacity = '0';
    observer.observe(el);
  });
});
