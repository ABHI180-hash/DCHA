// calendar.js
document.addEventListener('DOMContentLoaded', () => {
  const calendarContainer = document.getElementById('calendar-grid');
  if (!calendarContainer) return;

  const date = new Date();
  const month = date.toLocaleString('default', { month: 'long' });
  const year = date.getFullYear();
  
  document.getElementById('calendar-month').textContent = `${month} ${year}`;
  
  // Render dummy grid
  let html = '';
  for(let i=1; i<=30; i++) {
    const hasEvent = Math.random() > 0.8;
    html += `
      <div class="calendar-day" style="border: 1px solid var(--light-gray); padding: 10px; min-height: 100px;">
        <span style="font-weight: bold; color: var(--muted-gray);">${i}</span>
        ${hasEvent ? '<div class="badge badge-gold mt-2" style="display:block; font-size:10px;">Festival</div>' : ''}
      </div>
    `;
  }
  
  calendarContainer.innerHTML = `<div style="display:grid; grid-template-columns: repeat(7, 1fr); gap: 10px;">${html}</div>`;
});
