# FlapKap Vending Machine Task

## Installation

```bash
git clone git@github.com:mahmoud-aref/flapkap-vending.git

cd flapkap-vending

./gradlew bootJar

docker compose up
```

## Architecture

The application is built using the following technologies:

- Java 17
- Spring Boot 3.1.4
- Gradle 7.2
- Docker
- PostgreSQL

## API Documentation

The API documentation is available
at [http://localhost:8080/api-docs/ui/swagger-ui/swagger-ui/index.html](http://localhost:8080/api-docs/ui/swagger-ui/swagger-ui/index.html)

## Architecture

I tried to implement my take on understanding of the onion architecture. The application is divided into 3 layers:

- **Domain Layer**: This layer contains the business logic of the application. It contains the entities, services, and the
  domain exceptions.
- **Application Layer**: This layer contains the application logic. It contains the controllers.
- **Infrastructure Layer**: This layer contains the infrastructure logic. It contains the database configuration,
  repositories,and also the security configuration.

