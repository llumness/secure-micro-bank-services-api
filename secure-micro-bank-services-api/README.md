# Secure Micro Bank Services API

A Spring Boot portfolio project by **Nicholas Allum**  
Email: **allumnicholas@gmail.com**

## Overview

Secure Micro Bank Services API is a backend REST API designed to demonstrate Java backend development skills for banking and financial services roles. It includes user registration, bank account creation, secure endpoints, account-to-account transfers, transaction recording, validation, and centralized exception handling.

This project is built as a portfolio application to support applications for Java Backend Developer, Application Developer, Systems Analyst, and Banking Technology roles.

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation
- H2 in-memory database
- Maven

## Main Features

- Register banking users
- Create customer bank accounts
- Generate unique account numbers
- View accounts
- Transfer funds between accounts
- Reject transfers with insufficient funds
- Prevent transfers to the same account
- Record transaction history
- Secure banking endpoints with HTTP Basic authentication
- Centralized API error handling
- H2 database console for local development

## Security Notes

This project uses Spring Security with HTTP Basic authentication for simple portfolio demonstration.

Default local credentials:

```text
Username: admin
Password: admin123
```

For a production-level version, the next improvements would be JWT authentication, refresh tokens, role-based authorization, audit logs, and encrypted secrets.

## How to Run

```bash
mvn spring-boot:run
```

Application runs at:

```text
http://localhost:8080
```

H2 Console:

```text
http://localhost:8080/h2-console
```

H2 JDBC URL:

```text
jdbc:h2:mem:microbankdb
```

## API Endpoints

### Register User

```http
POST /api/auth/register
```

```json
{
  "fullName": "Nicholas Allum",
  "email": "allumnicholas@gmail.com",
  "password": "Password123"
}
```

### Create Account

Requires Basic Auth.

```http
POST /api/accounts
```

```json
{
  "userId": 1,
  "openingBalance": 2500.00
}
```

### View Accounts

```http
GET /api/accounts
```

### Transfer Funds

```http
POST /api/transfers
```

```json
{
  "fromAccountNumber": "MB-12345678",
  "toAccountNumber": "MB-87654321",
  "amount": 500.00,
  "description": "Monthly savings transfer"
}
```

### View Transaction History

```http
GET /api/transactions/{accountNumber}
```

## Sample Resume Bullet Points

- Developed a secure Java Spring Boot banking API with account management, fund transfers, transaction history, validation, and centralized exception handling.
- Implemented REST endpoints using Spring Web, Spring Data JPA, Spring Security, and H2 database for local testing.
- Designed transactional fund-transfer logic to validate account status, prevent same-account transfers, reject insufficient funds, and record debit/credit transaction entries.
- Applied layered architecture using controllers, services, repositories, DTOs, entities, and exception handling to improve maintainability.

## Future Enhancements

- JWT authentication and refresh tokens
- Role-based access control for ADMIN and CUSTOMER users
- PostgreSQL database profile
- Dockerfile and Docker Compose
- Unit and integration tests
- OpenAPI/Swagger documentation
- CI/CD pipeline using GitHub Actions
