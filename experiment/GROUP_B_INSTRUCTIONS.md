# Group B — TDD Instructions

## Overview

You will implement login functionality for a Java web application using the Javalin framework.
You must use **Claude claude-opus-4-6** as your code generation assistant, following a
**Test-Driven Development (TDD)** workflow.

The project skeleton AND a complete test suite have already been set up for you.

---

## The TDD Workflow You Must Follow

> **This process must be followed for every class you implement. Deviating from it
> invalidates your participation in the study.**

```
For each implementation task:

  1. READ the relevant test file(s) carefully.
  2. COPY the failing test(s) into your Claude prompt.
  3. ASK Claude to implement only the code needed to make those tests pass.
  4. RUN the tests: mvn test
  5. If tests fail, SHARE the failure output with Claude and iterate.
  6. Only move on when the tests pass.
```

The tests are your specification. You do not need to figure out what to build —
the tests tell you exactly what behaviour is required.

---

## Your Task

Implement the four classes marked **"YOU MUST IMPLEMENT ALL METHODS"**:

| Class | Location | Relevant Test File |
|---|---|---|
| `UserRepository` | `src/main/java/com/experiment/repository/` | `RegistrationTest.java` (partly) |
| `SessionRepository` | `src/main/java/com/experiment/repository/` | `SessionTest.java` (partly) |
| `AuthService` | `src/main/java/com/experiment/service/` | All test files |
| `AuthController` | `src/main/java/com/experiment/controller/` | All test files |

Do **not** modify `App.java`, `Database.java`, the model classes, or **any test file**.

---

## Test Files

All tests are in `src/test/java/com/experiment/`:

| File | Covers |
|---|---|
| `RegistrationTest.java` | `POST /api/register` |
| `LoginTest.java` | `POST /api/login` |
| `SessionTest.java` | `GET /api/profile` |
| `LogoutTest.java` | `POST /api/logout` |

---

---

## Commit Workflow (mandatory)

Every TDD cycle must be recorded as three commits using the provided scripts.
This is how we verify that TDD was followed correctly.

```bash
# Step 0 — run once at the very start
bash setup.sh

# Then for every task, repeat this cycle:
./commit-red.sh       # AFTER writing the test  — blocks if tests pass (wrong order)
./commit-green.sh     # AFTER implementing code — blocks if tests fail
./commit-refactor.sh  # AFTER any cleanup       — blocks if tests break
```

The scripts enforce the cycle automatically. Your git log should end up looking like:

```
[GREEN]    login returns 200 and a token
[RED]      login returns 200 and a token
[GREEN]    register returns 201 on success
[RED]      register returns 201 on success
[SETUP]    experiment skeleton — unimplemented stubs
```


## How to Run Tests

```bash
# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=RegistrationTest

# Run a single test method
mvn test -Dtest=RegistrationTest#successfulRegistrationReturns201
```

A successful run looks like:
```
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

---

## Suggested Prompting Strategy

Start your Claude prompt with something like:

> *"I am implementing a Javalin web application. Below is a JUnit 5 test that I need
> to make pass. Please implement the classes needed to satisfy these tests.
> Do not modify the test. Here are the test(s): [paste test(s) here]"*

After receiving generated code, run the tests. If they fail, follow up with:

> *"The tests failed with the following output: [paste mvn test output].
> Please fix the implementation."*

---

## Rules

1. You **must** use Claude claude-opus-4-6 at `claude.ai` for code generation.
2. You **must** paste tests into Claude before writing any implementation code.
3. You **must not** write implementation code before running the corresponding test first.
4. You **must not** modify any test file.
5. All tests must pass before you submit.
6. Record the number of prompts you send (the experimenter will also log this).
7. Record the total time taken from first prompt to all tests passing.

---

## Time Limit

You have **90 minutes** to complete the implementation.

---

*Please do not discuss your approach with participants in the other group.*
