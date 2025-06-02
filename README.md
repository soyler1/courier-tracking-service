# Migros Courier Tracking System

## Overview

Migros Courier Tracking System is a Java-based microservice architecture project that tracks courier geolocations, logs entries into store areas, and calculates the total distance traveled by couriers. The system is built using **Hexagonal Architecture** for modularity and testability. It is divided into three main microservices:

* **API Gateway**: Handles all incoming HTTP requests.
* **Tracking Service**: Processes courier location data and logs store entries.
* **Store Service**: Serves store location data.

This separation allows each service to scale independently and follow the Single Responsibility Principle.

---

## Key Features

* Live Courier Location Tracking via REST endpoint
* Store Entry Detection with proximity logic (100m threshold, 1 min cooldown)
* Distance Calculation per courier
* Resilient Service Integration using Feign client for store data
* Redis-backed cooldown control for store entries
* In-memory Store Caching updated every 10 minutes
* Entry logs are persisted in a PostgreSQL-backed **EntryLog** table
* Design Patterns Used:

    * Singleton Pattern for Redis-based Entry Cache
    * Observer Pattern for Courier Entry Event publishing
    * Command Pattern for logging store entries as independent commands
    * Factory Pattern for creating Store domain objects from raw input
* Architecture: Hexagonal (Ports and Adapters)

---

## Technologies Used

* Java 21
* Spring Boot 3
* Spring Web, Scheduling, Validation
* Feign Client
* Redis (for caching & cooldown)
* PostgreSQL (as the main database)
* Docker & Docker Compose
* Swagger (OpenAPI 3)
* JUnit 5 + Mockito

> Redis is preferred over in-memory caching to ensure correct behavior in multi-instance deployments. In-memory caches cannot coordinate re-entry cooldowns between distributed instances, which could result in multiple logs for the same store visit.

> Access Control:
>
> * `tracking-service` only accepts requests from `api-gateway`
> * `store-service` is only accessible from `tracking-service`

---

## Data Model Summary

### CourierLocation

```java
class CourierLocation {
    Long courierId;
    double latitude;
    double longitude;
    LocalDateTime timestamp;
}
```

### Store

```java
class Store {
    String name;
    double latitude;
    double longitude;
}
```

### EntryLog

```java
class EntryLog {
    Long id;
    Long courierId;
    String storeName;
    LocalDateTime timestamp;
}
```


## How to Run the Project (Dockerized)

1. Clone the repository

```bash
git clone https://github.com/soyler1/courier-tracking-service
cd courier-tracking-service
```

2. Build the JAR files manually (if not using multi-stage Dockerfiles):

```bash
mvn clean package -DskipTests
```

3. Start Docker containers:

```bash
docker-compose up --build
```

This command builds and launches all necessary services including:

* api-gateway
* tracking-service
* store-service
* redis
* postgres

4. Access the services:

* API Gateway: [http://localhost:8080](http://localhost:8080)
* Tracking Service Swagger UI: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
* Store Service Swagger UI: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## Example API Usage (Postman Ready)

### 1. Save Courier Location

```http
POST /api/locations
Content-Type: application/json
```

```json
{
  "courierId": 1,
  "latitude": 40.9923307,
  "longitude": 29.1244229,
  "timestamp": "2025-06-02T11:00:00"
}
```

### 2. Get Total Travel Distance

```http
GET /api/locations/1/distance
```

Response:

```json
{
  "courierId": 1,
  "totalDistance": 1524.38
}
```

### 3. Get All Stores (via Store Service)

```http
GET /api/internal/stores
```

Response:

```json
[
  {
    "name": "Ataşehir MMM Migros",
    "latitude": 40.9923307,
    "longitude": 29.1244229
  },
  {
    "name": "Novada MMM Migros",
    "latitude": 40.986106,
    "longitude": 29.1161293
  }
  // ... more stores
]
```

---

## Architectural Approach

### Hexagonal Architecture (Ports & Adapters)

* **Domain Layer**: Core business logic (CourierLocation, Store, Services)
* **Inbound Adapter**: `RestController` for HTTP requests
* **Outbound Adapter**: Feign client for Store Service, Redis for cooldown checks
* **Application Layer**: Coordinates between inbound and outbound through use-cases (ports)

---

## Design Patterns Summary

| Pattern   | Purpose                                               |
| --------- | ----------------------------------------------------- |
| Singleton | Shared Redis cache service                            |
| Observer  | Triggering events on store entry detection            |
| Command   | Encapsulating store entry logs as discrete operations |
| Factory   | Creating Store objects from external JSON data        |

---

## Developer Tips

* API's and domain objects can be seen by Swagger UI but request will not be successful because of proxy configuration to accept request from only authorized sources as I mentioned before.
* Redis ensures re-entry cooldown logic and event duplication prevention.
* Store list is auto-cached and updated every 10 mins.

---

## Notes

* `stores.json` is parsed on app startup (available in `/resources`)
* Add test data via `/api/locations` endpoint
* All services only accept requests through `api-gateway` with required headers

---

## TODOs / Improvements

* Add authentication layer to secure endpoints
* Enable Prometheus metrics for observability and set up alerting rules for system anomalies
* Integrate Grafana dashboards 
* Eureka discovery server can be added to communicate without urls. (Especially for multi instance scenario)
