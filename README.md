# Indexer Service

Service responsible for **indexing and maintaining search data** in the Vendo platform.

The service consumes events from other services (e.g. product-service) and builds a **search-optimized read model** in
Elasticsearch.

This enables fast and flexible product search, filtering, and sorting.

---

# Tech Stack

* Java 17
* Spring Boot
* Elasticsearch
* Kafka
* Docker
* Eureka
* Zipkin
* MapStruct
* Lombok
* Maven

---

# Architecture

The service follows **Hexagonal Architecture (Ports and Adapters)** and **CQRS** pattern.

It acts as a **read-model builder**, separating write operations (product-service) from read operations (search).

The core logic is independent of external systems such as Kafka or Elasticsearch.

## Layers

### domain

Core indexing logic.

* Search models (ElasticProduct)
* Value objects
* Domain transformations
* Exceptions

---

### application

Application use cases.

* Indexing orchestration

---

### port

Interfaces for external communication.

* Input ports (event consumers)
* Output ports (search index operations)

---

### adapter

External integrations.

#### adapter.in

* Kafka consumers
* Event deserialization

#### adapter.out

* Elasticsearch repositories
* Index management

---

# Project Structure

```
src
└── main
    └── java
        └── com.vendo.indexer_service
            ├── adapter
            │ ├── in
            │ │   └── kafka
            │ └── out
            │     └── elasticsearch
            ├── application
            ├── domain
            ├── port
            └── infrastructure
```

---

# How It Works

1. Product Service publishes events to Kafka
2. Indexer Service consumes events
3. Transforms data into search model
4. Stores data in Elasticsearch

This ensures **eventual consistency** between write and read models.

---

# Prerequisites

Before running this service, start required infrastructure.

## Dependencies

- **Config Server** – centralized configuration
- **Service Registry (Eureka)** – service discovery
- **Kafka** – event streaming
- **Elasticsearch** – search storage

---

## 1. Clone and run Config Server

```
git clone https://github.com/vendo-marketplace/config-server
cd config-server
mvn spring-boot:run
```

---

## 2. Clone and run Service Registry

```
git clone https://github.com/vendo-marketplace/registry-service
cd registry-service
mvn spring-boot:run
```

---

## 3. Run application

Or build and run:

```
mvn clean package
java -jar target/product-service.jar
```

---

# Environment Variables

| Variable            | Description               | Default |
|---------------------|---------------------------|--------|
| CONFIG_SERVER_URL   | Config server URL         | 8010   |
| KAFKA_BOOTSTRAP     | Kafka brokers             | localhost:9092 |
| ELASTICSEARCH_URL   | Elasticsearch endpoint    | localhost:9200 |

---

# API Documentation

Swagger UI:

```
http://localhost:8090/swagger-ui.html
```

OpenAPI specification:

```
http://localhost:8090/v3/api-docs
```

---

# Running Tests

Run all tests

```
mvn test
```

Run integration tests

```
mvn verify
```

---

# Code Style

The project follows standard **Java code conventions**.

Key principles:

* Clean Architecture
* SOLID principles
* Immutable DTOs
* Constructor injection
* Clear separation between layers

---

# Contributing

1. Create feature branch
2. Write tests
3. Ensure tests pass
4. Create pull request