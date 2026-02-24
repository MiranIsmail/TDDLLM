# Group A — Standard Prompting Instructions

## Overview

You will implement login functionality for a Java web application using the Javalin framework.
You must use **Claude claude-opus-4-6** as your code generation assistant.

The project skeleton has already been set up for you. Open it in your IDE and familiarise
yourself with the structure before you begin.

---

## Your Task

Implement the four classes marked **"YOU MUST IMPLEMENT ALL METHODS"** in the project:

| Class | Location |
|---|---|
| `UserRepository` | `src/main/java/com/experiment/repository/` |
| `SessionRepository` | `src/main/java/com/experiment/repository/` |
| `AuthService` | `src/main/java/com/experiment/service/` |
| `AuthController` | `src/main/java/com/experiment/controller/` |

Do **not** modify `App.java`, `Database.java`, or the model classes.

---

## Required Endpoints

Your implementation must expose the following HTTP endpoints:

### `POST /api/register`
Registers a new user.
- **Request body:** `{ "username": "...", "password": "..." }`
- **201 Created** on success: `{ "message": "User registered successfully" }`
- **409 Conflict** if username already exists: `{ "error": "Username already taken" }`
- **400 Bad Request** for invalid input: `{ "error": "<reason>" }`

### `POST /api/login`
Authenticates a user and returns a session token.
- **Request body:** `{ "username": "...", "password": "..." }`
- **200 OK** on success: `{ "token": "<session-token>" }`
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

The app will start on port 7070.

## How to Verify Your Work

The experimenter will run security and quality analysis tools on your submitted code.
Make sure the application starts and all four endpoints respond correctly.

---

## Time Limit

You have **90 minutes** to complete the implementation.

---

*Please do not discuss your approach with participants in the other group.*
