# 🏛️ Indian Cultural Heritage Archive (DCHA)

<p align="center">
  <img src="./a_realistic_high_detail_website_hero_banner_style.png" width="100%" alt="Indian Cultural Heritage Archive"/>
</p>

<h3 align="center">Preserving India's Heritage. Celebrating Its Living Culture.</h3>

<p align="center">
  A digital platform dedicated to discovering, documenting, exploring, and preserving India's diverse cultural heritage.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Project-DCHA-C49A45?style=for-the-badge" alt="DCHA"/>
  <img src="https://img.shields.io/badge/Status-In%20Development-orange?style=for-the-badge" alt="Status"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="MIT License"/>
</p>

<p align="center">
  <a href="#-overview">Overview</a> •
  <a href="#-features">Features</a> •
  <a href="#-technology-stack">Tech Stack</a> •
  <a href="#-project-structure">Structure</a> •
  <a href="#-installation--setup">Installation</a> •
  <a href="#-api-documentation">API</a> •
  <a href="#-contributing">Contributing</a>
</p>

---

## 📌 Overview

**Indian Cultural Heritage Archive (DCHA)** is a digital cultural preservation and heritage discovery platform designed to bring India's rich cultural identity into one accessible digital ecosystem.

India has an extraordinary diversity of traditions, languages, festivals, monuments, architecture, art forms, cuisine, music, and historical stories.

DCHA aims to make this heritage easier to discover, explore, document, and preserve through an interactive digital experience.

The platform combines heritage exploration, cultural documentation, interactive geographical discovery, community participation, and administrative content management.

### 🎯 Project Objectives

- Digitally document India's cultural and historical heritage.
- Make information about Indian states and their traditions accessible.
- Encourage cultural awareness among younger generations.
- Support community-driven heritage documentation.
- Provide an organized digital repository of cultural resources.
- Promote awareness of historical monuments and endangered heritage.

---

## ✨ Features

### 🗺️ Heritage Discovery
- Interactive India map.
- Exploration of 28 states and 8 union territories.
- State-wise cultural information.
- Historical monuments and heritage destinations.
- Heritage categories and detailed descriptions.

### 🎭 Culture & Traditions
- Indian festivals and celebrations.
- Traditional clothing and regional attire.
- Regional food and cuisine.
- Classical and folk dance forms.
- Traditional music and art forms.
- Languages and cultural practices.
- Folk stories and historical narratives.

### 🏛️ Digital Museum
- Virtual cultural exhibitions.
- Cultural artifacts and galleries.
- Historical timelines.
- Audio and cultural archives.
- Heritage preservation information.

### ❤️ User Experience
- User registration and authentication.
- Personal profiles.
- Save favorite heritage destinations.
- Personal cultural collections.
- Global search functionality.
- Multilingual interface support.

### 👨‍💼 Administration
- Admin dashboard.
- Heritage content management.
- State and cultural information management.
- Community submission review.
- User management.
- Platform analytics.

---

## 🛠️ Technology Stack

| Category | Technologies |
|---|---|
| Frontend | React.js, HTML5, CSS3, JavaScript |
| Styling | Tailwind CSS |
| Backend | Java, Spring Boot |
| Database | MySQL |
| Authentication | JWT, BCrypt |
| API | RESTful APIs |
| Build Tool | Maven |
| Development Environment | VS Code, IntelliJ IDEA |
| API Testing | Postman |
| Version Control | Git & GitHub |

---

## 💻 Programming Languages & Technologies

### Programming Languages

<p>
  <img src="https://skillicons.dev/icons?i=java,js,html,css,mysql" />
</p>

### Frameworks & Libraries

<p>
  <img src="https://skillicons.dev/icons?i=spring,react,tailwind" />
</p>

### Database & Tools

<p>
  <img src="https://skillicons.dev/icons?i=mysql,maven,git,github,vscode,idea,postman" />
</p>

### Technology Overview

- **Java:** Backend application development.
- **JavaScript:** Frontend functionality and API integration.
- **HTML5:** Webpage structure.
- **CSS3:** User interface styling.
- **SQL:** Database operations.
- **Spring Boot:** Backend application framework.
- **MySQL:** Relational database management.
- **Git & GitHub:** Source code management and collaboration.
- **Postman:** API testing.


## 🏗️ System Architecture

The project follows a client-server architecture.

```text
              USER
                |
                v
        FRONTEND APPLICATION
          React.js / UI
                |
                v
          REST API LAYER
                |
                v
         SPRING BOOT BACKEND
                |
        +-------+-------+
        |               |
        v               v
    AUTHENTICATION   BUSINESS LOGIC
        |               |
        +-------+-------+
                |
                v
           MYSQL DATABASE
```

---

## 📁 Project Structure

```text
DCHA/
│
├── frontend/
│   ├── src/
│   ├── public/
│   ├── components/
│   ├── pages/
│   ├── assets/
│   ├── styles/
│   └── package.json
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── database/
│   ├── schema.sql
│   ├── seed.sql
│   └── README.md
│
├── docs/
│   ├── screenshots/
│   └── diagrams/
│
├── .gitignore
├── LICENSE
└── README.md
```

---

## 🚀 Installation & Setup

### Prerequisites

Install the following tools before running the project:

- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/)
- [Node.js](https://nodejs.org/)
- [MySQL](https://dev.mysql.com/downloads/)
- [Git](https://git-scm.com/)
- [Visual Studio Code](https://code.visualstudio.com/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

### 1. Clone the Repository

```bash
git clone https://github.com/ABHI180-hash/DCHA.git
```

```bash
cd DCHA
```

### 2. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

### 3. Database Setup

Create the MySQL database:

```sql
CREATE DATABASE dcha;
```

Import the database schema and seed data using MySQL or phpMyAdmin.

### 4. Backend Setup

Navigate to the backend directory:

```bash
cd backend
```

Build the Spring Boot application:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

### 5. Access the Application

```text
Frontend: http://localhost:5173
Backend:  http://localhost:8080
```

*Note: Actual ports and commands depend on your project configuration.*

---

## 📡 API Documentation

The backend provides REST API endpoints for managing cultural and heritage information.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/states` | Retrieve all states and UTs |
| GET | `/api/heritage` | Retrieve heritage sites |
| GET | `/api/festivals` | Retrieve festivals |
| GET | `/api/foods` | Retrieve regional foods |
| GET | `/api/traditions` | Retrieve traditions |
| GET | `/api/stories` | Retrieve folk stories |
| GET | `/api/search?q=` | Search cultural resources |
| POST | `/api/auth/register` | Register a user |
| POST | `/api/auth/login` | Authenticate a user |
| GET | `/api/favorites` | Retrieve saved favorites |
| POST | `/api/contributions` | Submit a contribution |

---

## 🔐 Security

Security is an important part of the platform.

Planned security mechanisms include:

- JWT-based authentication.
- BCrypt password hashing.
- Role-based access control.
- Protected administrative endpoints.
- Server-side input validation.
- Secure configuration management.

Never commit API keys, database passwords, JWT secrets, or other credentials to a public repository.

---


## 🔮 Future Enhancements

- AI-powered cultural heritage assistant.
- Personalized heritage recommendations.
- Advanced multilingual content support.
- Heritage tourism route planning.
- 3D cultural artifact visualization.
- Augmented Reality heritage exploration.
- Community-driven cultural documentation.
- Advanced heritage preservation analytics.

---

## 🤝 Contributing

Contributions and suggestions are welcome.

To contribute:

1. Fork this repository.
2. Create a new branch.
3. Make your changes.
4. Commit your changes.
5. Push your branch.
6. Submit a Pull Request.

```bash
git checkout -b feature/your-feature
git add .
git commit -m "feat: add your feature"
git push origin feature/your-feature
```

Please ensure your contributions are meaningful and properly documented.

---

## 👨‍💻 Project Information

**Project:** Indian Cultural Heritage Archive (DCHA)

**Developer:** Avijit Mandal

**GitHub:** [@ABHI180-hash](https://github.com/ABHI180-hash)

**Repository:** [Indian Cultural Heritage Archive](https://github.com/ABHI180-hash/DCHA)

---

## 📄 License

This project is intended to be distributed under the MIT License.

See the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgements

We acknowledge the institutions and organizations dedicated to preserving India's cultural heritage:

- Archaeological Survey of India (ASI)
- UNESCO World Heritage Centre
- Ministry of Culture, Government of India
- National Museum, New Delhi
- Sahapedia
- India's cultural communities and heritage researchers

---

<p align="center">
  <strong>🏛️ INDIAN CULTURAL HERITAGE ARCHIVE</strong>
</p>

<p align="center">
  Preserving the Past. Celebrating the Present. Inspiring the Future.
</p>

<p align="center">
  Made with ❤️ for India's Cultural Heritage.
</p>
