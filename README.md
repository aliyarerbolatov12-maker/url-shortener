LinkCut — High-Performance URL Shortener
LinkCut is a modern, high-performance URL shortening service built to handle high-traffic loads. By leveraging Java 21
Virtual Threads and a multi-layer caching strategy, it ensures sub-millisecond redirect latency and massive throughput.

🚀 Key Features
Java 21 & Virtual Threads (Loom): Each request is processed in a lightweight virtual thread, enabling the system to
scale to tens of thousands of concurrent connections with minimal memory footprint.

Dual-Layer Storage: Persistent storage using PostgreSQL combined with Redis for lightning-fast lookups.

Base62 Encoding: Generates short, clean, and URL-safe identifiers (e.g., localhost:8081/6gh7A).

Smart Caching: Automatic caching of original URLs and click counts to offload the primary database.

Database Migrations: Version control for your schema using Flyway.

API Documentation: Fully interactive API playground via Swagger/OpenAPI.

🛠 Tech Stack
Framework: Spring Boot 3.5.x

Language: Java 21 (Loom enabled)

Database: PostgreSQL 15

Caching: Redis

ORM: Spring Data JPA (Hibernate)

Mapping: MapStruct & Custom Mappers

Migration: Flyway

Documentation: SpringDoc OpenAPI (Swagger UI)

Containerization: Docker & Docker Compose

📦 Quick Start (Docker)
The project is fully containerized. To spin up the entire infrastructure (App + DB + Redis), run:

Bash
docker-compose up -d --build
Once started, the application will be available at: http://localhost:8081

📖 API Documentation
Explore and test the API directly through the Swagger UI:

🔗 http://localhost:8081/swagger-ui/index.html

Core Endpoints:
POST /api/links/shorten — Create a shortened URL.

GET /api/links/{key} — Redirect to the original URL (increments click count).

GET /api/links/info/{key} — Retrieve the original URL without redirecting.

GET /api/links/details/{id} — Get full link metadata and statistics.

DELETE /api/links/{id} — Remove a link and purge associated cache.

⚙️ Environment Configuration
The application is configured via the .env file. Key parameters include:

Ini, TOML
SERVER_PORT=8081
APP_BASE_URL=http://localhost:8081
DB_URL=jdbc:postgresql://postgres:5432/url_shortener
REDIS_HOST=redis
Virtual Threads: Enabled via spring.threads.virtual.enabled=true in application.yaml.

🏗 Project Structure
Plaintext
src/main/java/com/linkcut/shortener/
├── common/ # Global config, Exceptions, and Handlers
├── modules/urls/ # Core URL Shortening Module
│ ├── controller/ # REST Controllers
│ ├── service/ # Logic (Base62, Caching, Sequences)
│ ├── repository/ # JPA Repositories
│ ├── entity/ # Database Entities
│ ├── dto/ # Request/Response Data Objects
│ └── mapper/ # MapStruct & Custom Mappers
└── ShortenerApiApplication.java
Built for speed, scalability, and clean code.