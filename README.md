# TODO Application

A simple Spring Boot application for managing TODO items.

## Features

- Create, read, update, and delete TODO items
- RESTful API
- H2 in-memory database
- Swagger/OpenAPI documentation

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

- `GET /api/todos` - Get all todos
- `GET /api/todos/{id}` - Get todo by ID
- `POST /api/todos` - Create a new todo
- `PUT /api/todos/{id}` - Update an existing todo
- `DELETE /api/todos/{id}` - Delete a todo by ID

## Database

The application uses H2 in-memory database. You can access the H2 console at `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Testing

Run the tests:

```bash
mvn test
```

## Building

Build the application:

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.