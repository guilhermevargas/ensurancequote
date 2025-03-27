# EnsuranceQuote

A Spring Boot-based service for managing insurance quotes. This project validates, persists, and processes insurance quote requests, sends messages via Kafka, and consumes policy issuance events to update quotes.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Cloning the Repository](#cloning-the-repository)
- [Running the Project with Docker Compose](#running-the-project-with-docker-compose)
- [API Endpoints](#api-endpoints)
    - [Create an Ensurance Quote](#create-an-ensurance-quote)
    - [Get an Ensurance Quote](#get-an-ensurance-quote)
- [Kafka Messaging](#kafka-messaging)
    - [Viewing Messages on a Topic](#viewing-messages-on-a-topic)
    - [Publishing a Message via CLI](#publishing-a-message-via-cli)
- [Notes](#notes)

## Prerequisites

Before running the project, ensure you have the following installed:

- **Java 17** or later
- **Gradle** (or use the provided Gradle wrapper)
- **Docker** and **Docker Compose**
- **Git**

## Cloning the Repository

Clone the repository using Git:

```bash

git clone https://github.com/guilhermevargas/ensurancequote.git
cd ensurancequote
```

## Running the Project with Docker Compose

### The project is containerized with Docker Compose. It includes:

- A **PostgreSQL** database for persisting quotes
- **Kafka** (with Zookeeper) for messaging
- **WireMock** container for mocking external catalog services
- The **EnsuranceQuote** application

### To build and start the containers, run:

```bash

docker-compose up --build

```

This command will build your application image, start all services, and expose the following ports (by default):

- PostgreSQL: 5432
- Kafka: 9092
- WireMock (Catalog Mock): 8081
- Application: 8080

## API Endpoints

### Create an Ensurance Quote

Send a POST request to create a new quote. For example, using curl:

**If you want create more quotes just change the document_number**
```bash

curl -X POST "http://localhost:8080/quotes" \
  -H "Content-Type: application/json" \
  -d '{
    "product_id": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offer_id": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "total_monthly_premium_amount": 75.25,
    "total_coverage_amount": 825000.00,
    "coverages": {
      "incêndio": 250000.00,
      "desastres_naturais": 500000.00,
      "responsabiliadade_civil": 75000.00
    },
    "assistances": [
      "encanador",
      "eletricista",
      "chaveiro_24h"
    ],
    "customer": {
      "document_number": "36205578900",
      "name": "John Wick",
      "type": "NATURAL",
      "gender": "MALE",
      "date_of_birth": "1973-05-02",
      "email": "johnwick@gmail.com",
      "phone_number": 11950503030
    }
}'

```

If successful, the API will return a 201 Created response with the created quote (including its generated numeric ID).

### Get an Ensurance Quote

Retrieve a quote by its ID with a GET request:

```bash
curl -X GET "http://localhost:8080/quotes/1"
```
The API will return a JSON payload representing the quote details.

## Kafka Messaging

### Viewing Messages on a Topic

To view messages sent to a Kafka topic, when a new quote was create.

1.  Open a shell in your Kafka container:

```bash
docker exec -it kafka /bin/bash

```
2. Run the console consumer:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic quote-received-topic --from-beginning

```
This will display all messages from the beginning of the topic **quote-received-topic**.


## Publishing a Message via CLI

To simulate a policy issuance event and update a quote when a policy is created. You need to publish a message to the policy-issued-topic. For example:

1. Open a shell in your Kafka container:

```bash
docker exec -it kafka /bin/bash

```

2. Run the console producer:


```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic policy-issued-topic

```

3. When the producer is running, type the following JSON payload (press Enter after the message):

```json
{"quoteId": 1, "policyNumber": 12345}
```

This message will be published on policy-issued-topic and (if configured) consumed by your application to update the quote with the generated policy number.


## Notes

- Unique Constraint:
The application ensures each quote is unique based on product ID, offer ID, and customer document number.

- Properties:
The application reads configuration properties (like database URL and Kafka bootstrap servers) from environment variables when running inside Docker Compose.

- Testing:
The project includes unit and integration tests using Testcontainers, Embedded Kafka, and WireMock.

- Messaging:
Apache Kafka is used as the message broker for asynchronous communication.

## Architecture Overview

### Domain Layer
The domain layer encapsulates all core business logic and domain models (entities, value objects, aggregates) without any external framework dependencies. This isolation ensures that business rules remain pure and easily testable.

### Validation Using Chain of Responsibility
We use the Chain of Responsibility pattern for quote validation—each validator checks one rule (like coverage limits or sum matching) and immediately throws an exception if violated, making the validations modular and easy to adjust.

### Outbox Pattern for Transactional Integrity
As a suggestion to mitigate the dual write problem—where separate transactions for database updates and message publication can lead to inconsistencies—we recommend using the Outbox Pattern. This pattern atomically writes events to an outbox table alongside the main transaction, and a separate process publishes those events to Kafka only after a successful commit.

### Benefits
- **Separation of Concerns:** Business logic is isolated from infrastructure code.
- **Modularity:** Each validation rule is self-contained and independently testable.
- **Transactional Integrity:** The Outbox Pattern prevents the dual write problem by ensuring messages are published only after a successful database commit.
- **Maintainability:** The architecture supports flexible and evolvable business requirements.
