# 🏛️ Indian Cultural Heritage Archive (DCHA)

<p align="center">
  <img src="./a_realistic_high_detail_website_hero_banner_style.png" width="100%" alt="Indian Cultural Heritage Archive Banner" />
</p>

<h3 align="center">Preserving India's Heritage • Celebrating Its Living Culture</h3>

<p align="center">
  A Digital Repository of India's Heritage, Culture, Monuments, Art, Festivals & Traditions.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Project-DCHA-C49A45?style=for-the-badge" />
  <img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Frontend-React.js-61DAFB?style=for-the-badge&logo=react" />
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/Database-MySQL-4479A1?style=for-the-badge&logo=mysql" />
</p>

---

## 📋 Overview

The **Indian Cultural Heritage Archive (DCHA)** is a comprehensive digital platform designed to discover, document, preserve, and explore India's rich cultural heritage.

India has an extraordinary cultural identity consisting of historical monuments, ancient civilizations, traditional art forms, classical dances, festivals, languages, museums, and indigenous traditions.

DCHA aims to bring this diverse heritage together through a centralized digital platform.

The platform functions as:

- A Digital Museum.
- A Cultural Heritage Repository.
- A Historical Information Platform.
- An Interactive Heritage Discovery System.
- An Educational and Research Resource.

It combines interactive maps, historical information, cultural traditions, monuments, festivals, museums, and community contributions into one unified platform.

## ✨ Key Features

### 🏛️ Heritage Discovery
- Explore historical monuments, temples, forts, palaces, and archaeological sites.
- Discover UNESCO World Heritage locations.
- View monument descriptions, historical backgrounds, and locations.
- Browse India's architectural heritage.

### 🗺️ Interactive India Map
- SVG-based interactive map of India.
- Clickable state and union territory selection.
- State-specific cultural information.
- Regional heritage discovery.
- Explore historical and cultural information by geographical region.

### 🏞️ States & Union Territories
- Complete coverage of 28 states and 8 union territories.
- Individual state information and cultural profiles.
- Capitals, regional languages, traditions, and heritage highlights.
- State-specific festivals, art forms, and landmarks.
- Quick View and Explore Page functionality.

### 🎭 Art & Culture Archive
- Classical Indian dance traditions.
- Folk dance and regional performances.
- Traditional paintings and handicrafts.
- Textile and handloom traditions.
- Indigenous art and cultural practices.
- Regional cultural identities.

### 🎉 Festivals & Celebrations
- Discover Indian festivals and seasonal celebrations.
- Explore Durga Puja, Diwali, Holi, Onam, Bihu, and other festivals.
- Regional festival information.
- Traditional rituals and cultural significance.

### 🏺 Virtual Museum
- Exhibition-style browsing of cultural artefacts.
- Historical collections and traditional objects.
- Museum and gallery directory.
- National and regional cultural institutions.

### 📜 Historical Timeline
- Explore important periods in Indian history.
- Indus Valley Civilization.
- Mauryan Empire and Emperor Ashoka.
- Ancient, medieval, and modern historical developments.
- Chronological historical information.

### 🤖 AI Heritage Assistant
- AI-powered heritage assistance.
- Heritage-related questions and answers.
- Intelligent exploration of Indian culture and history.
- AI-assisted discovery of cultural information.

### ❤️ User Features
- User registration and login.
- Secure authentication.
- Save favourite heritage places.
- Personal collections and saved locations.
- User-friendly navigation.

### 🛡️ Admin Dashboard
- Administrative content management.
- Add and manage monuments.
- Add museums and heritage locations.
- Upload images and descriptions.
- Manage cultural and historical information.

---

## 🛠️ Technology Stack

### Frontend
| Technology | Purpose |
|---|---|
| React.js | Component-based user interface |
| Vite | Frontend development and build tool |
| HTML5 | Webpage structure |
| CSS3 | Styling and responsive layouts |
| JavaScript | Frontend functionality |
| Tailwind CSS | Utility-based styling |

### Backend
| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Backend application framework |
| REST APIs | Frontend-backend communication |
| Maven | Dependency and build management |

### Database
| Technology | Purpose |
|---|---|
| MySQL | Relational database |
| SQL | Database queries and operations |
| phpMyAdmin | Database administration |

### Development Tools
- Visual Studio Code
- IntelliJ IDEA
- XAMPP
- Postman
- Git
- GitHub
- Antigravity IDE

---

## 📁 Project Structure

The Indian Cultural Heritage Archive follows a modular full-stack architecture, separating frontend components, backend services, database configuration, and application resources.

```text
Indian-Cultural-Heritage-Archive/
│
├── frontend/
│   ├── public/
│   │   ├── images/
│   │   ├── icons/
│   │   └── assets/
│   │
│   ├── src/
│   │   ├── assets/
│   │   │   ├── images/
│   │   │   ├── fonts/
│   │   │   └── styles/
│   │   │
│   │   ├── components/
│   │   │   ├── Navbar/
│   │   │   ├── Footer/
│   │   │   ├── Cards/
│   │   │   ├── Search/
│   │   │   └── Common/
│   │   │
│   │   ├── pages/
│   │   │   ├── Home/
│   │   │   ├── Explore/
│   │   │   ├── StatesAndUTs/
│   │   │   ├── Heritage/
│   │   │   ├── ArtAndCulture/
│   │   │   ├── InteractiveMap/
│   │   │   ├── Timeline/
│   │   │   ├── Museums/
│   │   │   ├── Login/
│   │   │   └── Admin/
│   │   │
│   │   ├── services/
│   │   │   └── api.js
│   │   │
│   │   ├── hooks/
│   │   ├── context/
│   │   ├── utils/
│   │   ├── App.jsx
│   │   └── main.jsx
│   │
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   └── tailwind.config.js
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/dcha/
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── entity/
│   │   │   │       ├── dto/
│   │   │   │       ├── security/
│   │   │   │       ├── exception/
│   │   │   │       └── DchaApplication.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── static/
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
│
├── database/
│   ├── schema.sql
│   └── sample-data.sql
│
├── screenshots/
│   ├── home-page.png
│   ├── explore-page.png
│   ├── states-and-uts.png
│   ├── heritage-page.png
│   ├── art-and-culture.png
│   ├── india-map.png
│   ├── historical-timeline.png
│   └── museums-and-galleries.png
│
├── .gitignore
├── README.md
└── LICENSE
```

### 📌 Directory Responsibilities

| Directory | Responsibility |
|---|---|
| frontend/ | React-based user interface |
| public/ | Public images and static assets |
| components/ | Reusable UI components |
| pages/ | Individual application pages |
| services/ | API communication |
| hooks/ | Reusable React hooks |
| context/ | Shared application state |
| backend/ | Spring Boot application |
| controller/ | REST API endpoints |
| service/ | Business logic |
| repository/ | Database operations |
| entity/ | Database entity models |
| dto/ | Data transfer objects |
| security/ | Authentication and authorization |
| config/ | Application configuration |
| database/ | SQL schema and sample data |
| screenshots/ | Project interface screenshots |

---

## 2. Frontend Architecture

The frontend is developed using React.js and Vite, providing a modular and responsive user interface.

### Core Responsibilities

- Rendering application pages and reusable components.
- Managing navigation between different heritage modules.
- Displaying historical and cultural information.
- Handling user interactions and search operations.
- Communicating with backend REST APIs.
- Providing responsive layouts across devices.

### Frontend Technologies

- React.js
- Vite
- JavaScript
- HTML5
- CSS3
- Tailwind CSS

## 3. Backend Architecture

The backend is developed using Java and Spring Boot, following a layered architecture.

### Backend Layers

| Layer | Responsibility |
|---|---|
| Controller | Receives HTTP requests and returns responses |
| Service | Handles business logic |
| Repository | Communicates with MySQL |
| Entity | Represents database records |
| DTO | Transfers structured data |
| Security | Handles authentication and access control |
| Configuration | Manages application settings |

### Backend Responsibilities

- Processing client requests.
- Managing user authentication.
- Retrieving and managing heritage information.
- Performing database operations.
- Providing REST API services.
- Supporting administrative operations.
- Integrating external AI services where configured.

## 4. Database Architecture

MySQL is used as the relational database management system.

The database layer is responsible for storing and managing structured application information.

### Suggested Database Entities

| Entity | Purpose |
|---|---|
| Users | User account information |
| Roles | User access permissions |
| States | State and union territory details |
| Monuments | Historical monument information |
| Museums | Museum and gallery records |
| Festivals | Festival information |
| CulturalData | Cultural traditions and art forms |
| Collections | User saved collections |
| Favorites | Favourite heritage locations |

*These are logical data entities; the actual database tables depend on the implemented schema.*

## 5. Application Workflow

The application follows a client-server communication model.

```text
       USER INTERACTION
              |
              ▼
       REACT FRONTEND
              |
              ▼
       API REQUEST (HTTP)
              |
              ▼
     SPRING BOOT CONTROLLER
              |
              ▼
       SERVICE LAYER
              |
              ▼
      REPOSITORY LAYER
              |
              ▼
          MYSQL
              |
              ▼
       API RESPONSE
              |
              ▼
       REACT FRONTEND
              |
              ▼
       DISPLAY RESULTS
```

## 6. API Architecture

The application uses RESTful communication between the frontend and backend.

Proposed API organization:

| API Group | Responsibility |
|---|---|
| /api/auth | Authentication and registration |
| /api/states | State and UT information |
| /api/heritage | Heritage and monument records |
| /api/museums | Museum information |
| /api/festivals | Festival information |
| /api/culture | Art and cultural information |
| /api/collections | Saved collections |
| /api/admin | Administrative operations |
| /api/ai | AI assistant integration |

**Note:** These are suggested endpoint groups, not a claim that every endpoint is already implemented.

## 7. Configuration Management

Application configuration is maintained separately for frontend and backend.

### Frontend Configuration

- API base URL.
- Application environment.
- External service configuration.

### Backend Configuration

- Server port.
- MySQL connection URL.
- Database username and password.
- AI provider API credentials.
- Application security settings.

Sensitive credentials should be stored in environment variables rather than committed to GitHub.

## 8. Development Methodology

The project follows a modular development approach:

1. Requirement analysis and feature planning.
2. UI/UX design and frontend development.
3. Backend API development.
4. Database design and integration.
5. Frontend-backend connectivity.
6. API testing using Postman.
7. Functional testing and debugging.
8. Version control and GitHub integration.


## 🏗️ System Architecture

DCHA follows a client-server architecture.

```text
             USER
               |
               ▼
      REACT + VITE FRONTEND
               |
               ▼
          REST APIs
               |
               ▼
       SPRING BOOT BACKEND
               |
         ┌─────┴─────┐
         ▼           ▼
      MYSQL       AI API
     DATABASE    INTEGRATION
```

**Frontend Layer:** React.js provides the user interface and interactive experience.

**Backend Layer:** Java and Spring Boot handle application logic, authentication, and API requests.

**Database Layer:** MySQL stores application data and related records.

**AI Integration:** An AI API provides heritage-related assistance.

---

## 📂 Main Project Modules

| Module | Description |
|---|---|
| Home | Main landing page |
| Explore | Central cultural discovery interface |
| States & UTs | Regional information and state profiles |
| Heritage | Historical monuments and UNESCO sites |
| Art & Culture | Dance, traditional art, and folk culture |
| Interactive Map | Geographical heritage exploration |
| Historical Timeline | Chronological historical information |
| Museums | Museum and gallery directory |
| AI Assistant | AI-powered heritage assistance |
| Authentication | User login and registration |
| Collections | Favourite and saved heritage locations |
| Admin Dashboard | Heritage content management |

---

## ⚙️ Installation & Setup

### Prerequisites

Install the following software before running the project:

- Node.js 18 or above
- Java JDK 17
- MySQL 8.0
- Apache Maven
- Git
- Visual Studio Code
- IntelliJ IDEA

### 1. Clone the Repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

### 2. Navigate to the Project

```bash
cd DCHA
```

### 3. Frontend Setup

Navigate to the frontend directory:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

### 4. Backend Setup

Navigate to the Spring Boot backend directory.

Build the application:

```bash
mvn clean install
```

Run the backend:

```bash
mvn spring-boot:run
```

### 5. Database Configuration

1. Start MySQL through XAMPP or your configured MySQL server.
2. Open phpMyAdmin.
3. Create the required MySQL database.
4. Configure the database URL, username, and password in `application.properties`.
5. Run the backend application.

**Note:** Update the commands and directory paths according to the actual repository structure.

---

## 🔐 Security & Authentication

The platform includes or is designed to support:

- User authentication.
- Password protection.
- Role-based administrative access.
- Backend API validation.
- Database-backed user management.
- Protected administrative operations.

Actual security depends on the backend implementation and configuration.

---

## 🎯 Project Objectives

1. Digitally preserve India's cultural heritage.
2. Promote awareness of Indian traditions and historical monuments.
3. Provide easy access to historical and cultural information.
4. Encourage educational and research activities.
5. Build a centralized digital cultural repository.
6. Connect geographical regions with their cultural identities.
7. Make India's diverse heritage accessible through modern technology.

---

## 🚀 Future Enhancements

- Multilingual content support.
- Interactive 3D monument experiences.
- Virtual heritage tours.
- Advanced AI-based cultural recommendations.
- Community-driven heritage contributions.
- Audio-guided museum experiences.
- Historical storytelling and documentary resources.
- Improved accessibility and mobile experiences.
- Advanced cultural search and filtering.

---

## 📜 License

This project is licensed under the MIT License.

---

## 🙏 Acknowledgement

We sincerely acknowledge everyone who contributed to the development of this academic project.

The Indian Cultural Heritage Archive is developed with the vision of preserving, promoting, and celebrating India's diverse cultural heritage through digital technology.

---

<p align="center">
  <h2 align="center">🏛️ INDIAN CULTURAL HERITAGE ARCHIVE</h2>
  <h3 align="center">Preserving the Past. Celebrating the Present. Inspiring the Future.</h3>
</p>
