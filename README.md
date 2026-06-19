# catalog-ms

A Spring Boot catalog microservice with JWT authentication, role-based access control,
PostgreSQL persistence, and Flyway-managed schema migrations. Interactive API docs are
served via Swagger UI.

## Tech stack

- **Java 17**, **Spring Boot 4.1.0** (Web MVC, Data JPA, Security)
- **PostgreSQL 17** + **Flyway** migrations
- **JWT** (HS256, self-issued via `jjwt`) for stateless auth
- **springdoc-openapi** for Swagger UI
- **Maven** (wrapper included) / **Docker + Docker Compose**

## Project layout

```
catalog-ms/
├── source/main/         # the Spring Boot application (Maven project)
│   ├── pom.xml
│   ├── mvnw, mvnw.cmd   # Maven wrapper
│   └── src/main/
│       ├── java/com/example/catalogms/   # controllers, services, security, entities
│       └── resources/
│           ├── application.properties
│           └── db/migration/             # Flyway V1..V7 SQL migrations
└── container/main/      # Docker assets
    ├── Dockerfile           # production image (build + slim JRE)
    ├── Dockerfile.dev       # dev image (live reload)
    ├── compose.yaml         # app + postgres (prod-style)
    └── compose.dev.yaml     # app + postgres with hot reload
```

## Prerequisites

- **JDK 17** (for the local/Maven path)
- **Docker** + **Docker Compose v2** (for the containerized path)
- A running **PostgreSQL 17** if you run the app locally without Docker

---

## Running with Docker Compose (recommended)

This starts both the application and a PostgreSQL database; Flyway creates the schema and
seeds demo users automatically on first boot.

From the repository root:

```bash
# Production-style: builds the jar into an image and runs it
docker compose -f container/main/compose.yaml up --build
```

The app is exposed on **http://localhost:7171** (Compose maps host `7171` → container `8080`).

### Development mode (live reload)

Edit code on the host and have it recompile/hot-restart inside the container:

```bash
docker compose -f container/main/compose.dev.yaml up --build
```

App on **http://localhost:7171**, PostgreSQL published on **localhost:5432**, and DevTools
LiveReload on `35729`.

To stop and remove containers (add `-v` to also drop the database volume):

```bash
docker compose -f container/main/compose.yaml down       # keep data
docker compose -f container/main/compose.yaml down -v    # wipe the DB volume
```

---

## Running locally with Maven

1. **Start PostgreSQL** with a database/user matching the defaults (or override via env vars below):

   ```bash
   docker run --name catalog-pg -e POSTGRES_DB=catalog_ms_db \
     -e POSTGRES_USER=catalog -e POSTGRES_PASSWORD=catalog \
     -p 5432:5432 -d postgres:17
   ```

2. **Run the app** from the Maven project directory:

   ```bash
   cd source/main
   ./mvnw spring-boot:run          # use mvnw.cmd on Windows
   ```

   The app starts on **http://localhost:8080** (the default Spring Boot port when run directly).

3. **Build a runnable jar** instead:

   ```bash
   cd source/main
   ./mvnw clean package            # add -DskipTests to skip tests
   java -jar target/catalog-ms-0.0.1-SNAPSHOT.jar
   ```

> Note: ports differ by path — **8080** when run locally with Maven, **7171** when run via Docker Compose.

---

## Configuration

All settings come from [application.properties](source/main/src/main/resources/application.properties)
and are overridable via environment variables (Compose injects these for you):

| Variable                     | Default                                              | Description                              |
| ---------------------------- | ---------------------------------------------------- | ---------------------------------------- |
| `SPRING_DATASOURCE_URL`      | `jdbc:postgresql://localhost:5432/catalog_ms_db`     | JDBC URL                                 |
| `SPRING_DATASOURCE_USERNAME` | `catalog`                                            | DB username                              |
| `SPRING_DATASOURCE_PASSWORD` | `catalog`                                            | DB password                              |
| `JWT_SECRET`                 | `dev-only-change-me-please-32+chars-secret-key`      | HMAC secret (HS256, **≥ 32 chars**)      |
| `JWT_EXPIRATION_MS`          | `86400000` (24h)                                     | Token lifetime in milliseconds           |

> **Security:** the default `JWT_SECRET` is for local/dev only — set a strong value in any
> real environment.

The database schema and demo data are managed by Flyway migrations
(`source/main/src/main/resources/db/migration`); Hibernate is set to `validate` only.

---

## API docs (Swagger UI)

Once running, open the interactive docs:

- Local: **http://localhost:8080/swagger-ui.html**
- Docker: **http://localhost:7171/swagger-ui.html**

Use the **Authorize** button to paste a JWT (see below) so requests are sent with the bearer token.

## Authentication & roles

Auth is stateless JWT. Log in to obtain a token, then send it as `Authorization: Bearer <token>`.

Seeded demo users (from `V7__seed_auth_data.sql`):

| Username     | Password        | Role         |
| ------------ | --------------- | ------------ |
| `admin`      | `admin123`      | `ADMIN`      |
| `manager`    | `manager123`    | `MANAGER`    |
| `supervisor` | `supervisor123` | `SUPERVISOR` |

Access rules (enforced in [SecurityConfig](source/main/src/main/java/com/example/catalogms/security/SecurityConfig.java)):

- `POST /auth/login`, `GET /health`, Swagger — **public**
- `GET /products/**` — any authenticated role (ADMIN, MANAGER, SUPERVISOR)
- `POST/PUT/DELETE /products/**` — **ADMIN, MANAGER** (supervisor is read-only)
- `/users/**`, `/roles/**`, `/settings/**` — **ADMIN only**

## Quick start with curl

```bash
# 1. Log in (use 7171 for Docker, 8080 for local Maven)
TOKEN=$(curl -s -X POST http://localhost:7171/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"admin123"}' | sed -E 's/.*"token":"([^"]+)".*/\1/')

# 2. List products (paged: ?page=1&size=10)
curl http://localhost:7171/products -H "Authorization: Bearer $TOKEN"

# 3. Create a product
curl -X POST http://localhost:7171/products \
  -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d '{"name":"Widget","price":9.99,"description":"A handy widget"}'
```

### Endpoints

| Method                  | Path             | Roles                  |
| ----------------------- | ---------------- | ---------------------- |
| `POST`                  | `/auth/login`    | public                 |
| `GET`                   | `/health`        | public                 |
| `GET`                   | `/products`      | ADMIN, MANAGER, SUPERVISOR |
| `GET`                   | `/products/{id}` | ADMIN, MANAGER, SUPERVISOR |
| `POST`                  | `/products`      | ADMIN, MANAGER         |
| `PUT`                   | `/products/{id}` | ADMIN, MANAGER         |
| `DELETE`                | `/products/{id}` | ADMIN, MANAGER         |
| `GET/POST/PUT/DELETE`   | `/users/**`      | ADMIN                  |
| `GET`                   | `/roles`         | ADMIN                  |
| `GET/PUT`               | `/settings`      | ADMIN                  |

---

## Running tests

```bash
cd source/main
./mvnw test
```
