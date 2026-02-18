# CMPT 276 Assignment 2 - CS Teaching Staff Rating App

A full CRUD web application for rating Computer Science teaching staff, built with **Spring Boot**, **Thymeleaf**, and **PostgreSQL**.

## Features

- **Create** new staff ratings with validation
- **Read** all ratings (index page) and individual details
- **Update** existing ratings with pre-filled forms
- **Delete** ratings with confirmation
- **Polymorphism**: Role-specific display titles via `StaffMemberProfile` hierarchy
- **Server-side validation**: Email format, score ranges (1-10), non-empty names, comment length (≤500 chars)

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21 + Spring Boot |
| Frontend | Thymeleaf + Bootstrap 5 |
| Database | PostgreSQL (prod) / H2 (test) |
| Deployment | Render |

## Running Locally

### Prerequisites
- Java 21+
- Maven (or use the included `mvnw` wrapper)

### Start the Application
```bash
./mvnw spring-boot:run
```
The app will start at `http://localhost:8080/`.

By default, it tries to connect to a PostgreSQL database via the `JDBC_DATABASE_URL` environment variable. For local development without PostgreSQL, the app falls back to an H2 in-memory database.

### Environment Variables (for PostgreSQL)
```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/yourdb
JDBC_DATABASE_USERNAME=youruser
JDBC_DATABASE_PASSWORD=yourpassword
```

## Running Tests
```bash
./mvnw test
```
See [TESTING.md](TESTING.md) for details on the test suite.

## Project Structure

```
src/main/java/com/cmpt276/assignment2/
├── Assignment2Application.java        # Main Spring Boot app
├── controllers/
│   └── StaffRatingController.java     # CRUD controller
└── models/
    ├── RoleType.java                  # Enum: TA, PROF, INSTRUCTOR, STAFF
    ├── StaffMemberProfile.java        # Abstract class (polymorphism)
    ├── ProfessorProfile.java          # Concrete: "Professor"
    ├── TAProfile.java                 # Concrete: "Teaching Assistant"
    ├── InstructorProfile.java         # Concrete: "Instructor"
    ├── StaffProfile.java             # Concrete: "Staff Member"
    ├── StaffMemberProfileFactory.java # Factory for profiles
    ├── StaffRating.java               # JPA entity
    └── StaffRatingRepository.java     # JPA repository
```

## Known Issues / Future Work

- Average rating calculation is a simple mean of three scores; could add weighted averages
- No pagination on the index page yet
- No search/filter functionality
- Unique email constraint may cause a server error on duplicate submission (could add friendlier error handling)

## AI Assistance Declaration

- Detail comments for code.
- Identify the cause of specific console errors during the development process.
- Dockerfile to support hosting to Render.
- Testing cases for Assignment 2 and `TESTING.md`.
- `README.md` to declare any AI assistance in the Assignment 2
