# CMPT 276 Assignment 2 - CS Teaching Staff Rating App

A full CRUD web application for rating Computer Science teaching staff, built with **Spring Boot**, **Thymeleaf**, and **PostgreSQL**.

**Deployed URL:** https://cmpt-276-assignment-2-99bz.onrender.com

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

### Start the Application Locally
```bash
./mvnw spring-boot:run
```
The app will start at `http://localhost:8080/`.

## Deployment 

This application is deployed on [Render](https://render.com) using a Docker container and PostgreSQL database.

### Database Setup
1. Create a new **PostgreSQL** database on Render.
   - **Name:** `cmpt276_assignment2_db`
   - **Region:** Oregon (US West)
   - **Version:** 17
2. Once created, copy the **Internal Database URL**, **Username**, and **Password** from the dashboard.

### Web Service Setup
1. Create a new **Web Service** on Render connected to this GitHub repository.
2. Select **Docker** as the Runtime.

### Environment Variables
You must set the following environment variables in the Render "Environment" tab to connect the app to the database.

| Key | Value |
| :--- | :--- |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://dpg-d6afio3h46gs73c1g8ig-a:5432/cmpt276_assignment2_db` |
| `SPRING_DATASOURCE_USERNAME` | `cmpt276_assignment2_db_user` |
| `SPRING_DATASOURCE_PASSWORD` | `1UzSb0xpljfdHHT7o9yLJGMMVcBYdT6a` |

### Deploy
1. Click **Create Web Service**.
2. Watch the logs. The build should finish, and the app will start on port `8080`.

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
- Login/Signup to add or edit rating
- Unique email constraint may cause a server error on duplicate submission (could add friendlier error handling)

## AI Assistance Declaration

- Detail comments for code.
- Identify the cause of specific console errors during the development process.
- Dockerfile to support hosting to Render.
- Testing cases for Assignment 2 and `TESTING.md`.
- `README.md` to declare any AI assistance in the Assignment 2
