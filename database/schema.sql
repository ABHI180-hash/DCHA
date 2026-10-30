-- DCHA Complete Schema
-- Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('user', 'admin', 'curator') DEFAULT 'user',
    avatar VARCHAR(255),
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- States Table
CREATE TABLE states (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    capital VARCHAR(255),
    region VARCHAR(100),
    type ENUM('state', 'union_territory') NOT NULL,
    description TEXT,
    cultural_significance TEXT,
    languages TEXT,
    area INT,
    population BIGINT,
    established_year INT,
    image_url VARCHAR(255),
    map_lat DECIMAL(10, 6),
    map_lng DECIMAL(10, 6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Heritage Sites Table
CREATE TABLE heritage_sites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    category ENUM('Monument', 'Temple', 'Fort', 'Palace', 'Archaeological Site', 'Natural Heritage', 'Sacred Site', 'Historic City', 'UNESCO Site', 'Cave', 'Mosque', 'Church', 'Stepwell', 'Stupa'),
    location VARCHAR(255),
    description TEXT,
    historical_significance TEXT,
    cultural_significance TEXT,
    period VARCHAR(100),
    image_url VARCHAR(255),
    map_lat DECIMAL(10, 6),
    map_lng DECIMAL(10, 6),
    source_name VARCHAR(255),
    source_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Festivals Table
CREATE TABLE festivals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    month INT,
    description TEXT,
    cultural_significance TEXT,
    category VARCHAR(100),
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Foods Table
CREATE TABLE foods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category ENUM('Main Course', 'Street Food', 'Sweet', 'Snack', 'Beverage', 'Rice Dish', 'Bread'),
    ingredients TEXT,
    cultural_significance TEXT,
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Traditional Dresses Table
CREATE TABLE traditional_dresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    gender ENUM('Male', 'Female', 'Unisex'),
    description TEXT,
    occasions VARCHAR(255),
    cultural_significance TEXT,
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Traditions Table
CREATE TABLE traditions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    description TEXT,
    cultural_significance TEXT,
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Languages Table
CREATE TABLE languages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    script VARCHAR(100),
    speakers_approx BIGINT,
    is_official BOOLEAN DEFAULT FALSE,
    description TEXT,
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Dances Table
CREATE TABLE dances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category ENUM('Classical', 'Folk', 'Tribal', 'Martial'),
    description TEXT,
    cultural_significance TEXT,
    performers VARCHAR(255),
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Music Table
CREATE TABLE music (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category ENUM('Classical', 'Folk', 'Devotional', 'Tribal', 'Film', 'Fusion'),
    description TEXT,
    instruments VARCHAR(255),
    cultural_significance TEXT,
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Arts and Crafts Table
CREATE TABLE arts_crafts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    description TEXT,
    materials VARCHAR(255),
    cultural_significance TEXT,
    gi_tag BOOLEAN DEFAULT FALSE,
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Folk Stories Table
CREATE TABLE folk_stories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    story_text TEXT NOT NULL,
    moral VARCHAR(255),
    category VARCHAR(100),
    cultural_significance TEXT,
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Gallery Items Table
CREATE TABLE gallery_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    image_url VARCHAR(255),
    credit VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Timeline Events Table
CREATE TABLE timeline_events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    year_start INT NOT NULL,
    year_end INT,
    era ENUM('Ancient', 'Medieval', 'Early Modern', 'Colonial', 'Independence', 'Modern'),
    category VARCHAR(100),
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE SET NULL
);

-- Museum Artifacts Table
CREATE TABLE museum_artifacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    historical_context TEXT,
    cultural_significance TEXT,
    period VARCHAR(100),
    category VARCHAR(100),
    section ENUM('Textiles', 'Sculpture', 'Paintings', 'Jewelry', 'Weapons', 'Pottery', 'Manuscripts', 'Musical Instruments', 'Architecture', 'Rituals'),
    image_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Audio Items Table
CREATE TABLE audio_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category ENUM('Folk Music', 'Traditional Music', 'Oral History', 'Folk Story', 'Cultural Sound'),
    duration_seconds INT,
    credit VARCHAR(255),
    audio_url VARCHAR(255),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Cultural Events Table
CREATE TABLE cultural_events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    event_date DATE,
    month INT,
    description TEXT,
    cultural_significance TEXT,
    category VARCHAR(100),
    source_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Heritage Risks Table
CREATE TABLE heritage_risks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    heritage_site_id INT,
    state_id INT NOT NULL,
    site_name VARCHAR(255),
    threat VARCHAR(255) NOT NULL,
    risk_level ENUM('LOW', 'MODERATE', 'HIGH', 'CRITICAL'),
    description TEXT,
    preservation_status VARCHAR(255),
    suggested_action TEXT,
    source_name VARCHAR(255),
    source_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (heritage_site_id) REFERENCES heritage_sites(id) ON DELETE SET NULL,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

-- Contributions Table
CREATE TABLE contributions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    state_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    description TEXT,
    cultural_significance TEXT,
    historical_context TEXT,
    location VARCHAR(255),
    image_url VARCHAR(255),
    audio_url VARCHAR(255),
    sources TEXT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    reviewer_notes TEXT,
    reviewed_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE,
    FOREIGN KEY (reviewed_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Favorites Table
CREATE TABLE favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    item_type ENUM('state', 'heritage', 'festival', 'food', 'tradition', 'story', 'dance', 'music', 'art', 'dress') NOT NULL,
    item_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY (user_id, item_type, item_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Collections Table
CREATE TABLE collections (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_public BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Collection Items Table
CREATE TABLE collection_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    collection_id INT NOT NULL,
    item_type VARCHAR(100) NOT NULL,
    item_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (collection_id) REFERENCES collections(id) ON DELETE CASCADE
);

-- Newsletter Subscribers Table
CREATE TABLE newsletter_subscribers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    subscribed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Contact Messages Table
CREATE TABLE contact_messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    subject VARCHAR(255),
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create necessary indexes
CREATE INDEX idx_states_slug ON states(slug);
CREATE INDEX idx_heritage_sites_slug ON heritage_sites(slug);
CREATE INDEX idx_heritage_sites_category ON heritage_sites(category);
CREATE INDEX idx_festivals_month ON festivals(month);
CREATE INDEX idx_cultural_events_date ON cultural_events(event_date);
CREATE INDEX idx_timeline_events_era ON timeline_events(era);
