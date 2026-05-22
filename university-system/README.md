# University System — Lab 8

Two-service microservice setup for a university domain: a `student-service` that owns student records, and a `course-service` that owns courses and enrollments. The course-service talks to the student-service over both Feign (for validation during enrollment) and RestTemplate (for hydrating student details when listing a course's roster).

## Features

- CRUD for students and courses.
- Student enrollment into a course with cross-service validation.
- Enrollment date stored per enrollment.
- Prerequisite check on enrollment: a course may declare a `prerequisiteCourseId`; the student must already be enrolled in that prerequisite course before they can enroll.
- Lookup courses by a student's name (or partial name/surname).
- Swagger UI fully documented in Azerbaijani (titles, tags, operation summaries/descriptions, DTO `@Schema` descriptions and examples, validation messages, exception messages, and error reason phrases).

## Technologies

- Java 21
- Spring Boot 3.3.5 (Web, Data JPA, Validation)
- Spring Cloud 2023.0.3 (OpenFeign)
- springdoc-openapi 2.6.0 (Swagger UI / OpenAPI 3)
- PostgreSQL 17 (one DB per service)
- Lombok
- Gradle (root wrapper)
- Docker / Docker Compose

## Project Layout

```
university-system/
├── course-service/     # port 8081, DB courseDB
├── student-service/    # port 9090, DB studentDB
├── docker-compose.yml  # 2 services + 2 Postgres instances
├── gradlew             # shared root wrapper
└── settings.gradle
```

## Ports & Databases

| Service          | App port | DB host port | DB name    | DB user / pass            |
| ---------------- | -------- | ------------ | ---------- | ------------------------- |
| student-service  | 9090     | 5432         | studentDB  | postgres / passwords*     |
| course-service   | 8081     | 5433         | courseDB   | postgres / passwordc*     |

\* The local `application.properties` uses `passwords` / `passwordc`; the Docker Compose stack overrides these to `password` via env vars. If you run Gradle locally against a Docker Compose stack, either edit the properties or override `SPRING_DATASOURCE_PASSWORD` on the command line.

## Running the Project

### Option A — Docker Compose (recommended)

Spins up both services with their own Postgres instances:

```bash
cd university-system
./gradlew :student-service:bootJar :course-service:bootJar
docker compose up --build
```

Then:

- student-service → http://localhost:9090
- course-service  → http://localhost:8081

Stop with `docker compose down` (add `-v` to wipe the DB volumes).

### Option B — Local Gradle (Postgres running separately)

> [!IMPORTANT]
> **The databases must exist *before* you start the apps.** Spring's `ddl-auto=update` will create tables inside an existing database, but it will **not** create the database itself — if `studentDB` or `courseDB` is missing, the service will fail to start with a Postgres `database "…" does not exist` error. We hit this during development, so do this step first.

1. Make sure Postgres 17 is running locally and reachable on `localhost:5432`.
2. **Create both databases** (one-time setup). Using `psql`:

   ```bash
   psql -U postgres -h localhost -c "CREATE DATABASE \"studentDB\";"
   psql -U postgres -h localhost -c "CREATE DATABASE \"courseDB\";"
   ```

   Or from inside a `psql` shell:

   ```sql
   CREATE DATABASE "studentDB";
   CREATE DATABASE "courseDB";
   ```

   The DB names are case-sensitive in Postgres when quoted — keep the camelCase exactly as shown.

3. Confirm the credentials match `src/main/resources/application.properties` in each service (`postgres` / `passwords` for student-service, `postgres` / `passwordc` for course-service). Adjust your local Postgres password or the properties file so they line up.

4. From `university-system/`:

   ```bash
   # in terminal 1
   ./gradlew :student-service:bootRun

   # in terminal 2
   ./gradlew :course-service:bootRun
   ```

`course-service` reads `STUDENT_SERVICE_BASE_URL` (defaults to `http://localhost:9090`) — override it if the student-service runs elsewhere.

> Docker Compose (Option A) does not need this step — each `postgres:17-alpine` container creates the DB automatically from the `POSTGRES_DB` env var on first start.

## Swagger / OpenAPI

Swagger UI is exposed at the default springdoc path on each service and is fully translated to Azerbaijani:

- Student service: http://localhost:9090/swagger-ui.html
- Course service:  http://localhost:8081/swagger-ui.html

Raw OpenAPI JSON:

- http://localhost:9090/v3/api-docs
- http://localhost:8081/v3/api-docs

## Endpoints

### student-service (`/api/v1/students`)

| Method | Path                  | Description                       |
| ------ | --------------------- | --------------------------------- |
| POST   | `/`                   | Create a student                  |
| GET    | `/`                   | List all students                 |
| GET    | `/{id}`               | Get a student by id               |
| GET    | `/search?name=…`      | Search by name/surname (partial)  |
| PUT    | `/{id}`               | Update a student                  |
| DELETE | `/{id}`               | Delete a student                  |

### course-service (`/api/v1/courses`)

| Method | Path                                            | Description                                                                                  |
| ------ | ----------------------------------------------- | -------------------------------------------------------------------------------------------- |
| POST   | `/`                                             | Create a course (optional `prerequisiteCourseId`)                                            |
| GET    | `/`                                             | List all courses                                                                             |
| GET    | `/{id}`                                         | Get a course by id                                                                           |
| PATCH  | `/{id}`                                         | Update a course                                                                              |
| DELETE | `/{id}`                                         | Delete a course                                                                              |
| POST   | `/{courseId}/students/{studentId}`              | Enroll a student — validates the student via Feign and checks the prerequisite if any        |
| GET    | `/{courseId}/students`                          | List students enrolled in a course (hydrated via RestTemplate from student-service)          |
| GET    | `/students/search?name=…`                       | Find courses by a student's name/surname                                                     |

## Example Requests

Create a student:

```bash
curl -X POST http://localhost:9090/api/v1/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Aysel",
    "lastName": "Məmmədova",
    "email": "aysel.mammadova@ada.edu.az",
    "age": 19
  }'
```

Create a course (no prerequisite):

```bash
curl -X POST http://localhost:8081/api/v1/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Verilənlər Bazasının Əsasları",
    "code": "CS101",
    "credits": 3,
    "prerequisiteCourseId": null
  }'
```

Create a course with a prerequisite:

```bash
curl -X POST http://localhost:8081/api/v1/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Verilənlər Bazası II",
    "code": "CS201",
    "credits": 3,
    "prerequisiteCourseId": 1
  }'
```

Enroll a student into a course:

```bash
curl -X POST http://localhost:8081/api/v1/courses/1/students/1
```

List students in a course:

```bash
curl http://localhost:8081/api/v1/courses/1/students
```

Find courses by student name:

```bash
curl "http://localhost:8081/api/v1/courses/students/search?name=Aysel"
```

## Error Responses

Both services use a `GlobalExceptionHandler` that returns a uniform `ApiErrorResponse` with an Azerbaijani `error` reason phrase. Example shape:

```json
{
  "timestamp": "2026-05-23T10:15:30",
  "status": 409,
  "error": "Münaqişə",
  "message": "Tələbə 1 artıq 2 kursunda qeydiyyatdan keçib.",
  "path": "/api/v1/courses/2/students/1"
}
```

Status mapping:

- `404 Tapılmadı` — course or student not found
- `409 Münaqişə` — duplicate enrollment or unmet prerequisite
- `400 Yanlış sorğu` — validation failures
- `502 Keçid xətası` — failure communicating with student-service from course-service
- `500 Daxili server xətası` — unexpected errors

## Notes

- `prerequisiteCourseId` is nullable — a course without prerequisites should send `null` (or omit the field).
- The prerequisite check requires the student to *already be enrolled* in the prerequisite course; create that enrollment first.
- The course-service depends on the student-service being reachable for `POST /{courseId}/students/{studentId}` and `GET /{courseId}/students`.
- DB schemas are auto-managed via `spring.jpa.hibernate.ddl-auto=update`.
- Logs include Feign and RestTemplate call traces at DEBUG for the course-service to aid troubleshooting cross-service calls.
