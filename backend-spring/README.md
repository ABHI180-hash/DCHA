# 🏛️ DCHA Spring Boot Backend (`backend-spring`)

Digital Culture Heritage Archive (DCHA) backend built with **Java 17** and **Spring Boot 3.2.3**.

---

## 🛠️ Tech Stack & Dependencies

- **Java**: 17
- **Framework**: Spring Boot 3.2.3
- **Security**: Spring Security + JWT (`jjwt` 0.12.5) with BCrypt password hashing
- **Persistence**: Spring Data JPA + Hibernate
- **Database**: MySQL 8.0 (Production/Default) & H2 Database (Local Dev Profile)
- **Utilities**: Lombok, Jakarta Validation
- **Build Tool**: Apache Maven

---

## 🚀 How to Run

### 1. Run with In-Memory H2 Database (Zero-Configuration Dev Mode)

No database installation required. Automatically seeds initial users, states, heritage sites, festivals, foods, dances, audio artifacts, and timeline events in memory.

```bash
cd backend-spring
mvn clean package -DskipTests
java -jar target/dcha-backend-1.0.0.jar --spring.profiles.active=h2
```
or via Maven directly:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

- **Server Port**: `8080`
- **H2 Console**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:dchadb`, User: `sa`, Password: `password`)

---

### 2. Run with MySQL Database (Production Mode)

Ensure MySQL is installed and running on port 3306.

#### Step 1: Initialize MySQL Schema & Seed Data
Execute `database/schema.sql` and `database/seed.sql` on your MySQL server:
```bash
mysql -u root -p dcha < database/schema.sql
mysql -u root -p dcha < database/seed.sql
```

#### Step 2: Configure Environment Variables or `application.properties`
Set the following environment variables (or rely on defaults):

| Variable | Default Value | Description |
|---|---|---|
| `DB_HOST` | `localhost` | MySQL host address |
| `DB_PORT` | `3306` | MySQL port |
| `DB_NAME` | `dcha` | MySQL database name |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | `root` | MySQL password |
| `JWT_SECRET` | `404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970` | Secret key for JWT tokens (HMAC-SHA256) |
| `JWT_EXPIRATION` | `86400000` | Token expiration time in milliseconds (default: 24h) |
| `AI_API_KEY` | *(Optional)* | OpenAI/LLM API key for heritage chat assistant |
| `AI_BASE_URL` | `https://api.openai.com/v1` | LLM API base URL |
| `AI_MODEL` | `gpt-3.5-turbo` | LLM model identifier |

#### Step 3: Run the Application
```bash
cd backend-spring
mvn clean package -DskipTests
java -jar target/dcha-backend-1.0.0.jar
```

---

## 🔑 Default User Accounts

| Role | Email | Password | Access Level |
|---|---|---|---|
| **Administrator** | `admin@dcha.org` | `Admin@123` | Full Access + Curation Dashboard (`/api/admin/*`) |
| **Curator** | `curator@dcha.org` | `Curator@123` | Curation Dashboard (`/api/admin/*`) |
| **Standard User** | `user@dcha.org` | `User@123` | Favorites, Collections, Submissions |

---

## 📡 API Endpoint Overview

### 1. Authentication (`/api/auth`)
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and obtain JWT
- `GET /api/auth/me` - Fetch profile of currently authenticated user

### 2. State & Regional Cultural Data (`/api/states`)
- `GET /api/states` - List all 28 States & 8 UTs
- `GET /api/states/{id}` - Get state details
- `GET /api/states/{id}/heritage` - Get state heritage sites
- `GET /api/states/{id}/festivals` - Get state festivals
- `GET /api/states/{id}/foods` - Get state traditional foods
- `GET /api/states/{id}/dances` - Get state dance forms
- `GET /api/states/{id}/dresses` - Get state traditional attire
- `GET /api/states/{id}/languages` - Get state languages
- `GET /api/states/{id}/stories` - Get state folk stories
- `GET /api/states/{id}/museums` - Get state museums

### 3. Heritage & Discovery
- `GET /api/heritage` & `GET /api/heritage/{id}` - Heritage sites
- `GET /api/festivals` & `GET /api/festivals/{id}` - Cultural festivals
- `GET /api/foods` & `GET /api/foods/{id}` - Traditional foods & cuisines
- `GET /api/dances` - Dance forms
- `GET /api/audio` - Audio archive (oral histories, music, folklore)
- `GET /api/calendar` - Cultural event calendar
- `GET /api/timeline` - Historical timeline events
- `GET /api/heritage-risk` - Endangered heritage sites
- `GET /api/search?q={query}&category={cat}&stateId={id}` - Global search

### 4. AI Heritage Assistant (`/api/ai`)
- `POST /api/ai/chat` - Query contextual AI Heritage Assistant (combines live DB context & optional LLM API)

### 5. Protected User Operations (Requires `Authorization: Bearer <token>`)
- `GET /api/favorites` - Fetch user favorites
- `POST /api/favorites` - Add item to favorites
- `DELETE /api/favorites/{id}` - Remove favorite by ID
- `DELETE /api/favorites/item?itemType=...&itemId=...` - Remove favorite by type and ID
- `GET /api/collections` - List user collections
- `POST /api/collections` - Create user collection
- `POST /api/contributions` - Submit user heritage contribution

### 6. Admin & Curator Curation (`/api/admin`) *(Requires `ADMIN` or `CURATOR` role)*
- `GET /api/admin/stats` - System overview metrics
- `GET /api/admin/contributions` - Review pending/approved/rejected submissions
- `PUT /api/admin/contributions/{id}/status` - Approve or reject user submission with curator notes
