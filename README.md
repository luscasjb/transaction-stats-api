# Transaction Statistics API

A real-time REST API for processing financial transactions and retrieving statistics, developed for the backend challenge available [here](https://github.com/feltex/desafio-itau-backend). This project is built with Java 17, Spring Boot, and Docker.

## About The Project

This API provides endpoints to:
- Post new financial transactions.
- Calculate real-time statistics for transactions made in the last configured time window (default 60 seconds).
- Delete all existing transactions.

All data is stored in-memory, with no database dependency.

## Key Features

- In-memory storage for high performance.
- Real-time statistics calculation (`sum`, `avg`, `min`, `max`, `count`).
- Input validation for transaction data.
- Fully containerized with Docker and Docker Compose.
- Interactive API documentation via Swagger UI.

## Tech Stack

- **Backend:** Java 17, Spring Boot 3
- **Build Tool:** Apache Maven
- **Containerization:** Docker, Docker Compose
- **API Documentation:** OpenAPI (springdoc)
- **Testing:** JUnit 5, Mockito

## Getting Started

To get a local copy up and running, first clone the repository:

```sh
git clone https://github.com/luscasjb/transaction-stats-api.git
cd transaction-stats-api
```

Once you have the code, you can run the application in one of two ways.

### Running the Application

#### Option 1: Running with Docker (Recommended)

This is the simplest way to run the application, as it handles all dependencies and setup automatically.

**Prerequisites:**
- Docker
- Docker Compose (usually included with Docker Desktop)

**Instructions:**

From the project's root directory, run the following command:

```sh
docker-compose up --build
```
This command will:
1.  Build the Docker image from the `Dockerfile`.
2.  Start the application container.

The API will be available at `http://localhost:8080`. To run in the background, add the `-d` flag.

To stop the application, run:
```sh
docker-compose down
```

#### Option 2: Running Locally without Docker

If you prefer to run the application directly on your machine without Docker, you can use Maven.

**Prerequisites:**
- Java Development Kit (JDK) 17 or later
- Apache Maven

**Instructions:**

1.  **Build the application package**
    From the project's root directory, run the Maven command to compile the code and package it into an executable `.jar` file.
    ```sh
    mvn clean package
    ```

2.  **Run the application**
    After the build is successful, run the generated `.jar` file.
    ```sh
    java -jar target/transaction-stats-api-*.jar
    ```

The API will be available at `http://localhost:8080`.

## API Documentation

Once the application is running (using either method), you can access the interactive API documentation (Swagger UI) at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Available Endpoints

- `POST /transactions`: Creates a new transaction.
- `DELETE /transactions`: Deletes all transactions.
- `GET /statistics`: Returns statistics for transactions in the last configured time window.
