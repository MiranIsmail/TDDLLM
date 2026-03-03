# Group A — Standard Prompting Instructions

## Overview

You will implement login functionality for a Java web application using the Javalin framework.
You must use **Claude claude-opus-4-6** as your code generation assistant.

The project skeleton has already been set up for you. Open it in your IDE and familiarise
yourself with the structure before you begin.

---

## Your Task
Your task is to implement logic for the following pages:
### ── Login Page/Register Page ──
* connect the database with the login and register page
* You should be able to creat a new user to the register page and it should be saved in the database
* You should be able to login to a specific user.
* If your not logged in you should only be able to access /login and /register
### ── Admin Page ──
* Admins should not be able to be created, admins can only be created from the database.
* Only admins should be able to access the admin page
* On the admin page you should be able to see every user, and also modify their timers. 

### ── User Page ──

Implement the four classes marked **"YOU MUST IMPLEMENT ALL METHODS"** in the project:

| Class               | Location |
|---------------------|---|
| `UserRepository`    | `src/main/java/com/experiment/repository/` |
| `SessionRepository` | `src/main/java/com/experiment/repository/` |
| `AuthService`       | `src/main/java/com/experiment/service/` |
| `AuthController`    | `src/main/java/com/experiment/controller/` |
| `UserController`    | `src/main/java/com/experiment/controller/` |
| `AdminController`   | `src/main/java/com/experiment/controller/` |

Do **not** modify `App.java`, or the model classes if not needed.

---

# Required Endpoints
Your implementation must expose the following HTTP endpoints:

## ── Authentication & Registration ──

### `POST /api/register`
Registers a new user.
- **Request body:** `{ "username": "...", "password": "..." }`
- **201 Created** on success: `{ "message": "User registered successfully" }`             #Kanske tid?
- **409 Conflict** if username already exists: `{ "error": "Username already taken" }`    #varför ha id om username är unik
- **400 Bad Request** for invalid input: `{ "error": "<reason>" }`

### `POST /api/login`
Authenticates a user and returns a session token.
- **Request body:** `{ "username": "...", "password": "..." }`
- **401 Unauthorized** for invalid credentials: `{ "error": "Invalid credentials" }`
- **400 Bad Request** for missing/malformed input

### `GET /api/profile`
Returns the authenticated user's profile.
- **Header:** `Authorization: Bearer <token>`
- **200 OK** on success: `{ "username": "..." }`
- **401 Unauthorized** if token is missing or invalid

### `POST /api/logout`
Invalidates the current session.
- **Header:** `Authorization: Bearer <token>`
- **200 OK** on success: `{ "message": "Logged out" }`
- **401 Unauthorized** if token is missing or invalid


## ── Time Tracking (The `data` Table) ──

### `POST /start`
Initiates a new tracking session.
- **Logic:** `INSERT INTO data (user_id, start_time, end_time) VALUES (?, CURRENT_TIMESTAMP, NULL)`.
- **200 OK:** `{ "message": "Started successfully" }`

### `POST /stop`
Terminates the active session for the authenticated user.
- **Logic:** `UPDATE data SET end_time = CURRENT_TIMESTAMP WHERE user_id = ? AND end_time IS NULL`.
- **200 OK:** `{ "message": "Stopped successfully" }`

---
## ── Data Retrieval ──
### help functions
* **stringifyUser** - takes input of database and converts it into correct format for the frontend. needed for AdminController and USerController.
  * Expected inputs:
    * `int id` - id from db
    * `String username` - username from db
    * `long loggedTime` - total logged time (use helpfunction)
    * `List<String> starts` - list of all time entries with matching user ID from data section in db
    * `List<String> ends` - list of all time entries with matching user ID from data section in db
  * Expected output:
    * `String`
* **calculateTotalLoggedTime** - Takes input of all time entries given and calculate the total time
  * Expected inputs:
      * `List<String> starts` - list of all time entries with matching user ID from data section in db
      * `List<String> ends` - list of all time entries with matching user ID from data section in db
  * Expected output:
      * `Long`
### `GET /user/data`
Retrieves the authenticated user's profile and their associated sessions from the `data` table.
- **Backend Logic:** `SELECT * FROM users JOIN data ON users.id = data.user_id WHERE users.id = ?`.
- **200 OK:** ```json
  [
  {
  "id": 1,
  "username": "j_doe_99",
  "logged_time": 45,
  "sessions": [
  { "start_time": "2026-03-02 08:00:00", "end_time": "" },
  { "start_time": "2026-03-01 09:00:00", "end_time": "2026-03-01 17:00:00" }
  ]
  }
  ]
---



## Rules

1. You **must** use Claude claude-opus-4-6 at `claude.ai` for code generation.
2. You may use Claude freely — as many prompts as you like, in whatever order you prefer.
3. You may copy and paste generated code directly, modify it, or combine multiple responses.
4. Do not write code without going through Claude.
5. Record the number of prompts you send (the experimenter will also log this).
6. Record the total time taken from first prompt to all tests passing.

---

## How to Run the Application

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.experiment.App"
```

The app will start on port 8080.

## How to Verify Your Work

The experimenter will run security and quality analysis tools on your submitted code.
Make sure the application starts and all four endpoints respond correctly.

---

## Time Limit

You have **90 minutes** to complete the implementation.

---

*Please do not discuss your approach with participants in the other group.*
