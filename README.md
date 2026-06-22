# Simple Spring Boot Demo

This is a small Spring Boot 2.7.x + MySQL demo project with a Vue UI.

## Features

- `GET /hello`
- `GET /todos`
- `GET /todos/{id}`
- `POST /todos`
- `PUT /todos/{id}`
- `DELETE /todos/{id}`
- `GET /` opens the Vue page

## Requirements

- JDK 8
- Maven
- MySQL 8+

## Database

Create a database first:

```sql
CREATE DATABASE demo_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Then update `src/main/resources/application.properties` with your MySQL username and password.

## Run

```bash
mvn spring-boot:run
```

After startup:

- http://localhost:8080/
- http://localhost:8080/hello
- http://localhost:8080/todos

## Example requests

Create a todo:

```bash
curl -X POST http://localhost:8080/todos ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"learn spring boot\"}"
```

Update a todo:

```bash
curl -X PUT http://localhost:8080/todos/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"learn JPA\",\"completed\":true}"
```
