import os

base_dir = r"c:\Users\ABHIJIT\OneDrive\Desktop\DCHA\frontend"
os.makedirs(base_dir, exist_ok=True)
os.makedirs(os.path.join(base_dir, "admin"), exist_ok=True)

def template(title, page_id, content, admin=False, script_extras=""):
    css = """  <link rel="stylesheet" href="css/main.css">
  <link rel="stylesheet" href="css/components.css">
  <link rel="stylesheet" href="css/responsive.css">"""
    scripts = """  <script src="js/data.js"></script>
  <script src="js/api.js"></script>
  <script src="js/auth.js"></script>
  <script src="js/components.js"></script>
  <script src="js/favorites.js"></script>"""
    prefix = ""
    if admin:
        css = """  <link rel="stylesheet" href="../css/main.css">
  <link rel="stylesheet" href="../css/components.css">
  <link rel="stylesheet" href="../css/responsive.css">
  <link rel="stylesheet" href="../css/admin.css">"""
        scripts = """  <script src="../js/data.js"></script>
  <script src="../js/api.js"></script>
  <script src="../js/auth.js"></script>
  <script src="../js/components.js"></script>
  <script src="../js/favorites.js"></script>
  <script src="../js/admin.js"></script>"""
        prefix = "../"
        
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>{title} — Digital Culture Heritage Archive</title>
{css}
</head>
<body>
  <div id="nav-container"></div>
  <main id="main-content">
{content}
  </main>
  <div id="footer-container"></div>
  <div id="toast-container" class="toast-container"></div>
  
{scripts}
{script_extras}
  <script>
    document.addEventListener('DOMContentLoaded', () => {{
      if(typeof initNav === 'function') initNav('{page_id}');
      if(typeof initFooter === 'function') initFooter();
    }});
  </script>
</body>
</html>"""

pages = {
    "index.html": ("Home", "home", """
    <section class="hero" style="max-height: 500px;">
      <div class="hero-content">
        <h1>Preserving India's Living Heritage</h1>
        <p>A comprehensive digital archive of India's states, heritage sites, festivals, food, art, and traditions.</p>
        <div class="hero-cta">
          <a href="explore.html" class="btn btn-primary">Explore</a>
          <a href="states.html" class="btn btn-secondary">States & UTs</a>
        </div>
      </div>
    </section>
    <section class="stats-bar">
      <div class="stat-item">28 States</div>
      <div class="stat-item">8 Union Territories</div>
      <div class="stat-item">1000+ Heritage Sites</div>
      <div class="stat-item">500+ Festivals</div>
    </section>
    <section class="explore-india">
      <h2>Explore India</h2>
      <div class="category-grid">
        <a href="heritage.html" class="card">Heritage Sites</a>
        <a href="festivals.html" class="card">Festivals</a>
        <a href="food.html" class="card">Traditional Food</a>
        <a href="culture.html" class="card">Art & Craft</a>
        <a href="culture.html" class="card">Dance & Music</a>
        <a href="stories.html" class="card">Folk Stories</a>
      </div>
    </section>
    <section class="map-preview">
      <h2>Interactive Map Preview</h2>
      <div id="map-embed">Explore the Map (SVG Placeholder)</div>
      <a href="map.html" class="btn">Go to Interactive Map</a>
    </section>
    <section class="states-preview">
      <h2>States & UTs</h2>
      <div class="card-scroll">
        <div class="card"><h3>Maharashtra</h3><p>Capital: Mumbai</p></div>
        <div class="card"><h3>Delhi</h3><p>Capital: New Delhi</p></div>
        <div class="card"><h3>Kerala</h3><p>Capital: Thiruvananthapuram</p></div>
      </div>
    </section>
    <section class="featured-heritage">
      <h2>Featured Heritage</h2>
      <div class="card-grid">
        <div class="card"><h3>Taj Mahal</h3><p>Uttar Pradesh</p></div>
        <div class="card"><h3>Hampi</h3><p>Karnataka</p></div>
        <div class="card"><h3>Sun Temple</h3><p>Odisha</p></div>
      </div>
    </section>
    <section class="cultural-highlights">
      <h2>Cultural Highlights</h2>
      <p>Discover dance, music, and art forms across India.</p>
    </section>
    <section class="cultural-calendar">
      <h2>Upcoming Events</h2>
      <p>Diwali - October/November</p>
    </section>
    <section class="timeline-preview">
      <h2>Heritage Timeline Preview</h2>
      <a href="timeline.html">View Timeline</a>
    </section>
    <section class="museum-preview">
      <h2>Virtual Museum Preview</h2>
      <a href="museum.html">View Museum</a>
    </section>
    <section class="audio-preview">
      <h2>Audio Archive Preview</h2>
      <a href="audio.html">Listen to Folklore</a>
    </section>
    <section class="at-risk-preview alert-style">
      <h2>Heritage at Risk</h2>
      <a href="at-risk.html">View Sites at Risk</a>
    </section>
    <section class="recently-added">
      <h2>Recently Added</h2>
      <p>Latest heritage and culture updates.</p>
    </section>
    <section class="community">
      <h2>Community</h2>
      <p>Over 10,000 active contributors preserving heritage.</p>
    </section>
    <section class="newsletter">
      <h2>Subscribe to Newsletter</h2>
      <form>
        <input type="email" placeholder="Email Address">
        <button type="submit">Subscribe</button>
      </form>
    </section>
"""),
    "explore.html": ("Explore", "explore", """
    <header><h1>Explore Cultural Categories</h1></header>
    <section class="explore-grid">
      <a href="heritage.html" class="card">Heritage</a>
      <a href="culture.html" class="card">Culture</a>
      <a href="traditions.html" class="card">Traditions</a>
      <a href="festivals.html" class="card">Festivals</a>
      <a href="food.html" class="card">Food</a>
      <a href="stories.html" class="card">Folk Stories</a>
      <a href="gallery.html" class="card">Gallery</a>
      <a href="timeline.html" class="card">Timeline</a>
      <a href="museum.html" class="card">Virtual Museum</a>
      <a href="audio.html" class="card">Audio Archive</a>
      <a href="calendar.html" class="card">Calendar</a>
      <a href="at-risk.html" class="card">At-Risk</a>
    </section>
"""),
    "states.html": ("States & UTs", "states", """
    <header><h1>28 States + 8 Union Territories</h1></header>
    <section class="filters">
      <input type="text" id="state-search" placeholder="Search states...">
      <select id="region-filter">
        <option value="">All Regions</option>
        <option value="North">North</option>
        <option value="South">South</option>
        <option value="East">East</option>
        <option value="West">West</option>
        <option value="Central">Central</option>
        <option value="Northeast">Northeast</option>
      </select>
      <select id="type-filter">
        <option value="">State / UT</option>
        <option value="State">State</option>
        <option value="UT">Union Territory</option>
      </select>
    </section>
    <section id="states-grid" class="card-grid">
      <div class="card">
        <h3>Maharashtra</h3>
        <p>Capital: Mumbai</p>
        <span class="badge">West</span>
        <a href="state-detail.html?id=1" class="btn">Explore</a>
      </div>
      <!-- Fallback data or fetched via JS -->
    </section>
"""),
    "state-detail.html": ("State Detail", "states", """
    <header class="detail-hero">
      <h1 id="state-name">State Name</h1>
      <p id="state-info">Region • Capital • <span class="badge">Type</span></p>
      <button class="btn-favorite">Save to Favorites</button>
    </header>
    <nav class="tab-nav">
      <a href="#overview">Overview</a>
      <a href="#heritage">Heritage</a>
      <a href="#festivals">Festivals</a>
      <a href="#food">Food</a>
      <a href="#dress">Dress</a>
      <a href="#traditions">Traditions</a>
      <a href="#languages">Languages</a>
      <a href="#dance">Dance</a>
      <a href="#music">Music</a>
      <a href="#arts">Arts</a>
      <a href="#stories">Stories</a>
      <a href="#gallery">Gallery</a>
      <a href="#map">Map</a>
    </nav>
    <section id="tab-content">
      <div id="overview" class="tab-pane active">
        <h2>Overview</h2>
        <p>Detailed description and cultural significance...</p>
        <ul id="state-languages"><li>Language 1</li></ul>
      </div>
    </section>
    <button class="btn-share">Share</button>
    <section class="related-states">
      <h2>Related States</h2>
      <div class="card-grid"></div>
    </section>
"""),
    "heritage.html": ("Heritage Directory", "heritage", """
    <header><h1>Heritage Directory</h1></header>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
      <select id="category-filter">
        <option value="">All Categories</option>
        <option value="Monument">Monument</option>
        <option value="Temple">Temple</option>
        <option value="Fort">Fort</option>
      </select>
      <input type="text" id="heritage-search" placeholder="Search heritage...">
    </section>
    <p id="result-count">0 Results</p>
    <section id="heritage-grid" class="card-grid">
      <div class="card">
        <h3>Gateway of India</h3>
        <p>Maharashtra</p>
        <span class="badge">Monument</span>
        <a href="heritage-detail.html?id=1" class="btn">View Details</a>
      </div>
    </section>
    <div class="pagination"></div>
"""),
    "heritage-detail.html": ("Heritage Detail", "heritage", """
    <header class="detail-hero">
      <h1 id="heritage-name">Heritage Name</h1>
      <p id="heritage-state">State</p>
      <span class="badge" id="heritage-category">Category</span>
      <button class="btn-favorite">Save</button>
    </header>
    <section class="details">
      <h2>Description</h2>
      <p>Historical and cultural significance...</p>
      <h2>Location</h2>
      <p>Location information...</p>
    </section>
    <section class="gallery">
      <h2>Gallery</h2>
    </section>
    <section class="map">
      <h2>Map</h2>
      <div id="heritage-map"></div>
    </section>
    <section class="credits">
      <h3>Sources</h3>
      <p>Credit info...</p>
    </section>
    <section class="related">
      <h2>Related Heritage</h2>
      <div class="card-grid"></div>
    </section>
"""),
    "culture.html": ("Culture Overview", "culture", """
    <header><h1>Culture Overview</h1></header>
    <section class="culture-links">
      <a href="traditions.html">Traditions</a>
      <a href="festivals.html">Festivals</a>
      <a href="food.html">Food</a>
      <a href="stories.html">Stories</a>
      <a href="arts.html">Arts, Dance & Music</a>
    </section>
    <section class="featured-culture">
      <h2>Featured Cultural Items</h2>
      <div class="card-grid"></div>
    </section>
"""),
    "traditions.html": ("Traditions", "culture", """
    <header><h1>Traditions</h1></header>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
    </section>
    <section id="traditions-list" class="card-grid"></section>
"""),
    "festivals.html": ("Festivals", "culture", """
    <header><h1>Festivals</h1></header>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
      <select id="month-filter"><option value="">All Months</option></select>
      <input type="text" placeholder="Search festivals...">
    </section>
    <section id="festivals-list" class="card-grid">
      <div class="card">
        <h3>Diwali</h3>
        <span class="badge">October/November</span>
        <button class="btn" onclick="showModal('festival-modal')">View Details</button>
      </div>
    </section>
"""),
    "food.html": ("Traditional Food", "culture", """
    <header><h1>Traditional Food</h1></header>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
      <select id="category-filter">
        <option value="">All</option>
        <option value="Main">Main Course</option>
        <option value="Street">Street Food</option>
        <option value="Sweet">Sweet</option>
      </select>
    </section>
    <section id="food-list" class="card-grid"></section>
"""),
    "stories.html": ("Folk Stories", "culture", """
    <header><h1>Folk Stories</h1></header>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
    </section>
    <section id="stories-list" class="card-grid"></section>
"""),
    "gallery.html": ("Gallery", "gallery", """
    <header><h1>Gallery</h1></header>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
      <select id="category-filter"><option value="">All Categories</option></select>
      <input type="text" placeholder="Search...">
    </section>
    <section id="gallery-grid" class="masonry-grid">
      <!-- Image elements -->
    </section>
    <div id="lightbox" class="modal" tabindex="-1" aria-hidden="true">
      <div class="modal-content">
        <button class="close-btn" onclick="closeModal('lightbox')">×</button>
        <img id="lightbox-img" src="" alt="">
        <p id="lightbox-caption"></p>
        <p id="lightbox-credit"></p>
      </div>
    </div>
"""),
    "timeline.html": ("Timeline", "timeline", """
    <header><h1>Historical Timeline</h1></header>
    <section class="filters">
      <select id="era-filter"><option value="">All Eras</option></select>
      <select id="state-filter"><option value="">All States</option></select>
    </section>
    <section id="timeline-container" class="timeline">
      <div class="timeline-event">
        <div class="period">2500 BCE</div>
        <h3>Indus Valley Civilization</h3>
        <p>Ancient artifacts from Harappa.</p>
        <span class="badge">Gujarat</span>
      </div>
    </section>
"""),
    "museum.html": ("Virtual Museum", "museum", """
    <header><h1>Virtual Museum</h1></header>
    <nav class="museum-sections">
      <a href="#textiles">Textiles</a>
      <a href="#sculpture">Sculpture</a>
      <a href="#paintings">Paintings</a>
      <a href="#jewelry">Jewelry</a>
      <a href="#weapons">Weapons</a>
      <a href="#pottery">Pottery</a>
      <a href="#manuscripts">Manuscripts</a>
      <a href="#instruments">Instruments</a>
      <a href="#architecture">Architecture</a>
      <a href="#rituals">Rituals</a>
    </nav>
    <section id="museum-display">
      <div id="textiles" class="museum-section">
        <h2>Textiles</h2>
        <p>Rich weaving traditions.</p>
        <div class="card-grid"></div>
      </div>
    </section>
"""),
    "audio.html": ("Audio Archive", "audio", """
    <header><h1>Audio Archive</h1></header>
    <section class="filters">
      <select id="category-filter">
        <option value="">All</option>
        <option value="Folk Music">Folk Music</option>
        <option value="Oral History">Oral History</option>
      </select>
      <select id="state-filter"><option value="">All States</option></select>
    </section>
    <section id="audio-list" class="card-grid">
      <div class="card audio-card">
        <h3>Folk Song</h3>
        <audio controls>
          <source src="fallback.mp3" type="audio/mpeg">
          Your browser does not support the audio element.
        </audio>
        <span class="duration">3:45</span>
      </div>
    </section>
"""),
    "calendar.html": ("Cultural Calendar", "calendar", """
    <header><h1>Cultural Calendar</h1></header>
    <section class="calendar-nav">
      <button>&lt;</button>
      <h2 id="current-month">September 2026</h2>
      <button>&gt;</button>
    </section>
    <section class="filters">
      <select id="state-filter"><option value="">All States</option></select>
      <select id="category-filter"><option value="">All Categories</option></select>
    </section>
    <section id="calendar-grid" class="calendar-grid">
      <!-- 7 columns -->
    </section>
    <section id="calendar-events" class="card-grid"></section>
"""),
    "at-risk.html": ("Heritage at Risk", "at-risk", """
    <header><h1>Heritage At Risk</h1></header>
    <section class="filters">
      <select id="risk-filter">
        <option value="">All Risk Levels</option>
        <option value="LOW">Low</option>
        <option value="MODERATE">Moderate</option>
        <option value="HIGH">High</option>
        <option value="CRITICAL">Critical</option>
      </select>
      <select id="state-filter"><option value="">All States</option></select>
    </section>
    <section id="at-risk-list" class="card-grid">
      <div class="card risk-card critical">
        <h3>Ancient Site</h3>
        <p>State</p>
        <p>Threat: Urbanization</p>
        <span class="badge risk-level">CRITICAL</span>
        <p>Status: Unprotected</p>
      </div>
    </section>
"""),
    "compare.html": ("Compare States", "compare", """
    <header><h1>Compare States</h1></header>
    <section class="compare-selectors">
      <select id="state1"><option value="">Select State 1</option></select>
      <select id="state2"><option value="">Select State 2</option></select>
      <button id="btn-compare" class="btn">Compare</button>
    </section>
    <section id="compare-results">
      <table class="compare-table">
        <thead>
          <tr>
            <th>Feature</th>
            <th id="th-state1">State 1</th>
            <th id="th-state2">State 2</th>
          </tr>
        </thead>
        <tbody>
          <tr><td>Languages</td><td id="td-lang1"></td><td id="td-lang2"></td></tr>
          <tr><td>Festivals</td><td id="td-fest1"></td><td id="td-fest2"></td></tr>
          <tr><td>Heritage Count</td><td id="td-hc1"></td><td id="td-hc2"></td></tr>
        </tbody>
      </table>
    </section>
"""),
    "map.html": ("Interactive Map", "map", """
    <header><h1>Interactive Map</h1></header>
    <section class="map-controls">
      <select id="category-filter"><option value="">All Categories</option></select>
      <input type="text" id="map-search" placeholder="Search...">
      <button id="btn-location" class="btn">Use My Location</button>
    </section>
    <section id="interactive-map-container">
      <div id="map-svg-container" style="width:100%; height: 500px; background: #eee;">
        <!-- SVG Map -->
      </div>
      <div id="map-legend" class="legend">Legend</div>
    </section>
"""),
    "search.html": ("Global Search", "search", """
    <header><h1>Search Archive</h1></header>
    <section class="search-bar-large">
      <input type="text" id="global-search" placeholder="Type to search..." style="width:100%; padding:15px; font-size:1.2rem;">
    </section>
    <section class="filters">
      <div class="checkbox-group">
        <label><input type="checkbox" value="heritage" checked> Heritage</label>
        <label><input type="checkbox" value="festivals" checked> Festivals</label>
      </div>
      <select id="state-filter"><option value="">All States</option></select>
      <button id="btn-clear" class="btn">Clear Filters</button>
    </section>
    <p id="search-count">0 Results</p>
    <section id="search-results" class="results-list"></section>
"""),
    "login.html": ("Login", "home", """
    <section class="auth-container" style="max-width: 400px; margin: 0 auto;">
      <h1>Login</h1>
      <form id="login-form">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" required>
        </div>
        <div id="login-error" class="error-msg"></div>
        <button type="submit" class="btn btn-primary">Login</button>
      </form>
      <p>Don't have an account? <a href="register.html">Register</a></p>
    </section>
"""),
    "register.html": ("Register", "home", """
    <section class="auth-container" style="max-width: 400px; margin: 0 auto;">
      <h1>Register</h1>
      <form id="register-form">
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" required>
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" required>
        </div>
        <div class="form-group">
          <label for="confirm-password">Confirm Password</label>
          <input type="password" id="confirm-password" required>
        </div>
        <div id="register-error" class="error-msg"></div>
        <button type="submit" class="btn btn-primary">Register</button>
      </form>
      <p>Already have an account? <a href="login.html">Login</a></p>
    </section>
"""),
    "profile.html": ("Profile", "home", """
    <header><h1>User Profile</h1></header>
    <section class="profile-info">
      <h2 id="user-name">Name</h2>
      <p id="user-email">Email</p>
      <span class="badge" id="user-role">Role</span>
      <p id="user-joined">Joined Date</p>
      <button id="btn-edit-profile" class="btn">Edit Profile</button>
      <form id="edit-profile-form" style="display:none;">
        <input type="text" id="edit-name" placeholder="Name">
        <button type="submit">Save</button>
      </form>
    </section>
    <nav class="tab-nav">
      <a href="#saved-heritage">Saved Heritage</a>
      <a href="#saved-states">Saved States</a>
      <a href="#saved-festivals">Saved Festivals</a>
      <a href="#collections">Collections</a>
      <a href="#contributions">Contributions</a>
    </nav>
    <section id="tab-content">
      <div id="saved-heritage" class="tab-pane active">
        <h2>Saved Heritage</h2>
        <div class="card-grid" id="saved-heritage-list"></div>
      </div>
      <!-- other panes... -->
    </section>
"""),
    "collections.html": ("Collections", "home", """
    <header><h1>My Collections</h1></header>
    <section class="collections-actions">
      <button class="btn" onclick="showModal('new-collection-modal')">Create New Collection</button>
    </section>
    <section id="collections-list" class="card-grid">
      <p class="empty-state">No collections yet.</p>
    </section>
"""),
    "contribute.html": ("Contribute", "home", """
    <header><h1>Contribute to the Archive</h1></header>
    <section class="multi-step-form">
      <div class="step-indicator">Step <span id="current-step">1</span> of 7</div>
      <form id="contribution-form">
        <div class="step" id="step1">
          <h2>Basic Info</h2>
          <input type="text" placeholder="Title" required>
        </div>
        <div class="step" id="step2" style="display:none;">
          <h2>Select State/UT</h2>
          <select required><option value="">Select...</option></select>
        </div>
        <div class="step" id="step3" style="display:none;">
          <h2>Select Category</h2>
          <select required><option value="">Select...</option></select>
        </div>
        <div class="step" id="step4" style="display:none;">
          <h2>Cultural Content</h2>
          <textarea placeholder="Description" required></textarea>
        </div>
        <div class="step" id="step5" style="display:none;">
          <h2>Media & References</h2>
          <input type="url" placeholder="Image URL">
        </div>
        <div class="step" id="step6" style="display:none;">
          <h2>Review</h2>
          <div id="review-content"></div>
        </div>
        <div class="step" id="step7" style="display:none;">
          <h2>Success!</h2>
          <p>Your contribution has been submitted.</p>
        </div>
        <div class="form-actions">
          <button type="button" id="btn-prev" disabled>Back</button>
          <button type="button" id="btn-next">Next</button>
        </div>
      </form>
    </section>
"""),
    "about.html": ("About", "home", """
    <header><h1>About DCHA</h1></header>
    <section class="mission">
      <h2>Mission</h2>
      <p>Preserving India's cultural heritage digitally.</p>
    </section>
    <section class="team">
      <h2>Team</h2>
      <p>Placeholder for team information.</p>
    </section>
    <section class="tech">
      <h2>Technology Stack</h2>
      <p>HTML, CSS, JavaScript</p>
    </section>
    <section class="contact">
      <h2>Contact Us</h2>
      <form>
        <input type="text" placeholder="Name" required>
        <input type="email" placeholder="Email" required>
        <textarea placeholder="Message" required></textarea>
        <button type="submit" class="btn">Send</button>
      </form>
    </section>
""")
}

admin_pages = {
    "dashboard.html": ("Admin Dashboard", "admin", """
    <header><h1>Dashboard</h1></header>
    <section class="stats-grid">
      <div class="stat-card">States: 36</div>
      <div class="stat-card">Heritage Sites: 1000+</div>
      <div class="stat-card">Users: 500</div>
      <div class="stat-card">Pending: 12</div>
    </section>
    <section class="quick-actions">
      <a href="states.html" class="btn">Manage States</a>
      <a href="submissions.html" class="btn">View Submissions</a>
    </section>
"""),
    "states.html": ("Admin States", "admin", """
    <header><h1>Manage States</h1></header>
    <button class="btn" onclick="showModal('add-state')">Add State</button>
    <table class="admin-table">
      <thead><tr><th>Name</th><th>Type</th><th>Actions</th></tr></thead>
      <tbody>
        <tr><td>Maharashtra</td><td>State</td><td><button>Edit</button></td></tr>
      </tbody>
    </table>
"""),
    "heritage.html": ("Admin Heritage", "admin", """
    <header><h1>Manage Heritage</h1></header>
    <button class="btn">Add New</button>
    <select id="state-filter"><option value="">All States</option></select>
    <table class="admin-table">
      <thead><tr><th>Name</th><th>State</th><th>Actions</th></tr></thead>
      <tbody>
        <tr><td>Taj Mahal</td><td>UP</td><td><button>Edit</button> <button>Delete</button></td></tr>
      </tbody>
    </table>
"""),
    "submissions.html": ("Admin Submissions", "admin", """
    <header><h1>Submissions</h1></header>
    <select id="status-filter">
      <option value="Pending">Pending</option>
      <option value="Approved">Approved</option>
      <option value="Rejected">Rejected</option>
    </select>
    <table class="admin-table">
      <thead><tr><th>Title</th><th>User</th><th>Status</th><th>Actions</th></tr></thead>
      <tbody>
        <tr><td>New Temple</td><td>User1</td><td>Pending</td><td><button>Approve</button> <button>Reject</button></td></tr>
      </tbody>
    </table>
"""),
    "users.html": ("Admin Users", "admin", """
    <header><h1>Manage Users</h1></header>
    <table class="admin-table">
      <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Created</th></tr></thead>
      <tbody>
        <tr><td>Admin</td><td>admin@test.com</td><td><span class="badge">Admin</span></td><td>2026-01-01</td></tr>
      </tbody>
    </table>
"""),
    "analytics.html": ("Admin Analytics", "admin", """
    <header><h1>Analytics</h1></header>
    <section class="analytics-charts">
      <div class="chart">Content Statistics</div>
      <div class="chart">Category Distribution</div>
      <div class="chart">State Coverage</div>
      <div class="chart">Contribution Trends</div>
    </section>
""")
}

for filename, (title, page_id, content) in pages.items():
    filepath = os.path.join(base_dir, filename)
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(template(title, page_id, content, admin=False))

for filename, (title, page_id, content) in admin_pages.items():
    filepath = os.path.join(base_dir, "admin", filename)
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(template(title, page_id, content, admin=True))

print("Successfully generated all HTML files.")
