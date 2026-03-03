# Group B — TDD + LLM Instructions

## Overview

You will implement login functionality for a Java web application using the **Javalin** framework
and a **SQLite** database. You must use **Claude Opus 4-6** as your code generation assistant,
but only *after* you have written and seen your tests fail first.

The project skeleton and an example test suite are already provided. Familiarise yourself with
the structure before you begin.

---

## The Rule: Tests First, Code Second

> **You must write the test yourself, by hand, before you prompt Claude for any implementation
> code. This rule is non-negotiable and is the core of what we are measuring.**

The workflow for every task is:

```
1. READ   — understand what the endpoint must do (see spec below)
2. WRITE  — write the JUnit test(s) yourself, by hand, in the relevant test file
3. RED    — run the tests and confirm they FAIL, then run ./red.sh to commit
4. PROMPT — paste the failing test(s) into Claude and ask for an implementation
5. GREEN  — paste the generated code in, run the tests, iterate with Claude until
             they all PASS, then run ./green.sh to commit
6. REFACTOR — clean up the code (with or without Claude), confirm tests still pass,
              then run ./blue.sh to commit
7. REPEAT — move to the next task
```

The tests are your specification. You do not need to figure out what to build —
the tests tell you exactly what behaviour is required.

---

## Your Task

Implement the four classes marked **"YOU MUST IMPLEMENT ALL METHODS"**:

| Class | Location | Test File |
|---|---|---|
| `UserRepository` | `src/main/java/com/experiment/repository/` | `RegistrationTest.java` |
| `SessionRepository` | `src/main/java/com/experiment/repository/` | `SessionTest.java` |
| `AuthService` | `src/main/java/com/experiment/service/` | All test files |
| `AuthController` | `src/main/java/com/experiment/controller/` | All test files |

Do **not** modify `App.java`, `Database.java`, or any of the model classes.

---

## Required Endpoints

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
- **400 Bad Request** for missing or malformed input

### `GET /api/profile`

Returns the authenticated user's profile.

- **Header:** `Authorization: Bearer <token>`
- **200 OK** on success: `{ "username": "..." }`
- **401 Unauthorized** if the token is missing or invalid

### `POST /api/logout`

Invalidates the current session.

- **Header:** `Authorization: Bearer <token>`
- **200 OK** on success: `{ "message": "Logged out" }`
- **401 Unauthorized** if the token is missing or invalid

---

## Test Files

All test files live in `src/test/java/com/experiment/`. Each file already contains one
example test to show you the structure. **You write the remaining tests yourself.**

| File | Endpoint it covers |
|---|---|
| `RegistrationTest.java` | `POST /api/register` |
| `LoginTest.java` | `POST /api/login` |
| `SessionTest.java` | `GET /api/profile` |
| `LogoutTest.java` | `POST /api/logout` |

### Useful things to test (non-exhaustive)

- Happy path (correct input → correct status + body)
- Duplicate username on register → 409
- Wrong password on login → 401
- Missing or malformed JSON → 400
- Accessing `/api/profile` without a token → 401
- Accessing `/api/profile` with an invalid or expired token → 401
- Logging out invalidates the token (subsequent profile call → 401)

---

## Commit Workflow

Every TDD cycle produces exactly three commits. Use the provided scripts — they will
**block the commit** if the cycle is in the wrong state, so you cannot cheat accidentally.

```bash
# Run once at the very start
bash setup.sh

# Then repeat for every task:
./red.sh      # After writing tests — BLOCKS if tests pass (you wrote code too early)
./green.sh    # After implementation passes — BLOCKS if any test still fails
./blue.sh     # After refactoring — BLOCKS if refactoring broke anything
```

Your git log should end up looking something like:

```
[BLUE]   extracted token generation to a helper method
[GREEN]  logout invalidates the session token
[RED]    logout invalidates the session token
[BLUE]   no changes needed
[GREEN]  valid token returns 200 on profile endpoint
[RED]    valid token returns 200 on profile endpoint
...
[SETUP]  experiment skeleton — unimplemented stubs
```

---

## How to Run the Tests

```bash
# Run all tests
mvn test

# Run only one test class
mvn test -Dtest=RegistrationTest

# Run one specific test method
mvn test -Dtest=RegistrationTest#successfulRegistrationReturns201
```

A fully passing run looks like:

```
[INFO] Tests run: N, Failures: 0, Errors: 0, Skipped: 0
```

---

## How to Prompt Claude Effectively

**Starting a new task — paste your failing test(s) and ask for the implementation:**

> *"I am implementing a Javalin web application backed by SQLite. Below is a JUnit 5
> test that I need to make pass. Please implement the classes necessary to satisfy
> these tests. Do not modify the tests. Here are the relevant tests: [paste tests]
> Here is the existing skeleton code for context: [paste relevant stub files]"*

**When tests still fail after the first attempt:**

> *"The tests failed with this output: [paste full mvn test output].
> Please fix the implementation."*

**Starting the refactor phase:**

> *"All tests pass. Please refactor the implementation for clarity and clean code.
> Do not change any behaviour or modify the tests."*

You may prompt as freely as you like — the only constraint is that the test must already
exist and be failing before you ask for implementation code.

---

## Rules

1. You **must** use Claude Opus 4-6 (at the URL provided by the experimenter) for code generation.
2. You **must** write each test yourself, by hand, before prompting for implementation code.
3. You **must** run the tests and confirm they fail (RED) before prompting for implementation.
4. You **must** confirm all tests pass (GREEN) before committing the green phase.
6. Record the total time from your first prompt to all tests passing.
7. Record how many prompts you sent (the experimenter also logs this independently).

---

## Recommended IDE: IntelliJ IDEA

We recommend using IntelliJ IDEA (Community Edition is free) as it handles Maven projects automatically and has built-in test running. Download it from <https://www.jetbrains.com/idea/download>.
To open the project: File → Open → select the experiment/ folder. IDEA will detect the pom.xml and import everything for you. You can run tests directly by clicking the green arrow next to any test method, or by right-clicking the test file.
You are free to use any IDE you are comfortable with (VS Code with the Extension Pack for Java, Eclipse, etc.), but support will only be provided for IDEA during the session
---

## Time Limit

You have **150 minutes** to complete the full implementation.

---

*Please do not discuss your approach with participants in Group A.*
