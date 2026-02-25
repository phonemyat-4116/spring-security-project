# 🌦️ Spring Security Weather Service (Learning + Project)

A hands-on **Spring Boot + Spring Security** project to learn and implement:

- 🔐 Authentication with username/password
- 🪪 JWT-based authorization
- 👤 Role-based access control (`ADMIN`, `USER`)
- 🧩 Permission-based access (`WEATHER_READ`, `WEATHER_WRITE`, `WEATHER_DELETE`)
- ⚡ Method-level security with `@PreAuthorize`
- 🗄️ Caching with Redis + Spring Cache
- 🧪 In-memory database support with H2

This repository is both a **working project** and a **learning playground** for understanding core security concepts in modern Spring applications.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.4.2
- Spring Security
- Spring Data JPA
- H2 (in-memory DB)
- Redis (cache)
- JJWT (JSON Web Token library)
- Maven

---

## 📁 Project Structure

```text
src/main/java/com/codesnippet/weather_service
├── config        # Security and caching configuration
├── controller    # REST endpoints (auth, users, weather, logs)
├── entity        # JPA entities + enums (roles, permissions)
├── filters       # JWT auth filter + request logging filter
├── repository    # Data access layer
├── service       # Business logic
└── utils         # JWT utility
```

---

## 🔐 Security Model

### Roles
- `ADMIN`
- `USER`

### Permissions
- `WEATHER_READ`
- `WEATHER_WRITE`
- `WEATHER_DELETE`

### Role → Permission Mapping
- `ADMIN` ➜ all weather permissions
- `USER` ➜ read-only permission (`WEATHER_READ`)

### Authentication Flow
1. Client sends credentials to `/authenticate`.
2. Server validates credentials with `AuthenticationManager`.
3. Server returns a signed JWT token.
4. Client sends token in `Authorization: Bearer <token>` header.
5. `JwtAuthFilter` validates token and loads authorities into Spring Security context.

---

## 👥 Default Users (Auto-Created on Startup)

- **admin / admin123** → `ADMIN`
- **user / user123** → `USER`

> These are created by `AdminUserInitializer` if they don't already exist.

---

## 📡 API Endpoints Overview

### Public Endpoints
- `POST /authenticate` – generate JWT token
- `POST /api/users/register` – self-register as `USER`
- `GET /h2-console/**` – H2 web console
- `GET /weather/cacheData` – print cache details

### Authenticated / Protected Endpoints
- `GET /weather?city=...` – requires `WEATHER_READ`
- `POST /weather` – requires `WEATHER_WRITE`
- `DELETE /weather/{city}` – requires `WEATHER_DELETE`
- `POST /api/users/admin/create` – requires `ROLE_ADMIN`

### Additional Endpoints
- `GET /weather/all`
- `PUT /weather/{city}`
- `GET /weather/health`
- `GET /logs/{id}`
- `POST /logs`

> Some endpoints are intentionally less restricted to support experimentation and learning.

---

## ⚙️ Configuration

Key properties in `src/main/resources/application.properties`:

- H2 datasource configuration
- Redis cache host/port
- JWT secret and expiration

Example:

```properties
jwt.secret=your-secret
jwt.expiration=3600000
```

---

## ▶️ Run the Project

### 1) Prerequisites
- Java 17+
- Maven (or use Maven Wrapper)
- Redis running on `localhost:6379` (for caching features)

### 2) Start application

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

The app runs by default at: `http://localhost:8080`

---

## 🧪 Quick Test Flow

### 1) Authenticate

```bash
curl -X POST http://localhost:8080/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 2) Call protected endpoint with JWT

```bash
curl "http://localhost:8080/weather?city=Delhi" \
  -H "Authorization: Bearer <PASTE_TOKEN_HERE>"
```

### 3) Create weather data (ADMIN example)

```bash
curl -X POST http://localhost:8080/weather \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <PASTE_TOKEN_HERE>" \
  -d '{"city":"Delhi","forecast":"Sunny"}'
```

---

## 🎯 Learning Goals Covered

- Understand Spring Security filter chain
- Build custom JWT validation filter
- Use `UserDetails` + custom authorities
- Implement role and permission checks
- Use method-level authorization with `@PreAuthorize`
- Combine JPA + cache in a secure API

---

## 🛠️ Ideas to Improve Further

- Add Swagger/OpenAPI docs
- Add refresh token mechanism
- Add global exception handling for auth errors
- Add integration tests for role/permission scenarios
- Harden production configuration (external secrets, HTTPS, etc.)

---

## 🤝 Contribution

This is a learning-focused repo. Feel free to fork, experiment, and raise improvements via pull requests.

---

## 📜 License

No license declared yet. Add one (MIT/Apache-2.0) if you plan to share publicly.
