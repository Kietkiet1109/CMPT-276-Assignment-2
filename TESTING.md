# Testing Guide

## Running the Test Suite

```bash
./mvnw test
```

All tests use an **H2 in-memory database**, so no external database setup is required.

## Test Classes

### 1. `StaffRatingValidationTest`
Test constraints on the `StaffRating` entity.

| Test | What it verifies |
|---|---|
| `validRating_noViolations` | A fully valid rating passes all constraints |
| `blankName_shouldFail` | Empty name is rejected |
| `nullName_shouldFail` | Null name is rejected |
| `invalidEmail_shouldFail` | Malformed email is rejected |
| `blankEmail_shouldFail` | Empty email is rejected |
| `clarityBelowMin_shouldFail` | Clarity score < 1 is rejected |
| `clarityAboveMax_shouldFail` | Clarity score > 10 is rejected |
| `nicenessOutOfRange_shouldFail` | Niceness score < 1 is rejected |
| `knowledgeScoreOutOfRange_shouldFail` | Knowledge score > 10 is rejected |
| `nullScores_shouldFail` | Null scores are rejected (all three) |
| `commentTooLong_shouldFail` | Comment > 500 chars is rejected |
| `commentExactly500_shouldPass` | Comment at exactly 500 chars is accepted |
| `nullComment_shouldPass` | Null/empty comment is accepted (optional field) |

### 2. `StaffRatingControllerTest`
Test HTTP endpoints.

| Test | What it verifies |
|---|---|
| `indexPage_shouldReturn200` | `GET /` returns 200 with ratings list |
| `viewRating_shouldReturn200` | `GET /view/{id}` returns 200 with detail |
| `addForm_shouldReturn200` | `GET /add` returns 200 with empty form |
| `editForm_shouldReturn200` | `GET /edit/{id}` returns 200 with pre-filled form |
| `saveValidRating_shouldRedirect` | `POST /save` with valid data redirects to `/` |
| `saveInvalidRating_shouldShowErrors` | `POST /save` with invalid data re-renders form |
| `deleteRating_shouldRedirect` | `GET /delete/{id}` redirects to `/` |

### 3. `StaffRatingRepositoryTest`
Test JPA persistence.

| Test | What it verifies |
|---|---|
| `saveAndRetrieve_shouldWork` | Save entity, retrieve by ID, verify fields |
| `delete_shouldRemoveEntry` | Delete entity, confirm it's gone |
| `timestamps_shouldBeSetOnSave` | `createdAt`/`updatedAt` auto-populated |
| `findAll_returnsAllEntries` | Multiple saves reflected in `findAll()` |

### 4. `Assignment2ApplicationTests`
Spring Boot integration test that verifies the application context loads successfully.
