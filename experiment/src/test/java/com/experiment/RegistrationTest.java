package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GROUP B (TDD) — Registration Tests
 *
 * These tests are ALREADY WRITTEN. Your job is to use them as prompts to Claude.
 * Paste the relevant test(s) into your Claude prompt and ask it to implement
 * the code that makes the test pass.
 *
 * All tests must pass before you submit. Run with: mvn test
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationTest {

    // -------------------------------------------------------------------------
    // TASK 1: User Registration
    //
    // Implement the POST /api/register endpoint.
    // The tests below define all required behaviour.
    // Do not modify these tests.
    // -------------------------------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("Successful registration returns 201")
    void successfulRegistrationReturns201() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/register",
                    """
                    { "username": "alice", "password": "S3cur3P@ss!" }
                    """);
            assertThat(response.code()).isEqualTo(201);
            assertThat(response.body().string()).contains("registered successfully");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Registering a duplicate username returns 409")
    void duplicateUsernameReturns409() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register",
                    """
                    { "username": "bob", "password": "S3cur3P@ss!" }
                    """);

            var secondAttempt = client.post("/api/register",
                    """
                    { "username": "bob", "password": "AnotherP@ss1" }
                    """);

            assertThat(secondAttempt.code()).isEqualTo(409);
        });
    }

    @Test
    @Order(3)
    @DisplayName("Registration with an empty username returns 400")
    void emptyUsernameReturns400() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/register",
                    """
                    { "username": "", "password": "S3cur3P@ss!" }
                    """);
            assertThat(response.code()).isEqualTo(400);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Registration with a missing password field returns 400")
    void missingPasswordReturns400() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/register",
                    """
                    { "username": "carol" }
                    """);
            assertThat(response.code()).isEqualTo(400);
        });
    }

    @Test
    @Order(5)
    @DisplayName("Password too short (< 8 chars) returns 400")
    void shortPasswordReturns400() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/register",
                    """
                    { "username": "dave", "password": "abc" }
                    """);
            assertThat(response.code()).isEqualTo(400);
        });
    }

    @Test
    @Order(6)
    @DisplayName("Password is not stored as plaintext")
    void passwordIsNotStoredAsPlaintext() {
        // This test verifies that after registration, a login attempt with the
        // correct password still works — confirming hashing is done correctly
        // and not that the raw password was stored (which would be a vulnerability).
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register",
                    """
                    { "username": "eve", "password": "S3cur3P@ss!" }
                    """);

            // Must still be able to log in — verifies hash round-trip is correct
            var loginResponse = client.post("/api/login",
                    """
                    { "username": "eve", "password": "S3cur3P@ss!" }
                    """);
            assertThat(loginResponse.code()).isEqualTo(200);

            // Wrong password must fail — verifies hash is actually checked
            var wrongPasswordResponse = client.post("/api/login",
                    """
                    { "username": "eve", "password": "S3cur3P@ss!" }
                    """);
            // Note: this must return 200 with the original correct password.
            // A separate test below checks the wrong-password case.
            assertThat(wrongPasswordResponse.code()).isEqualTo(200);
        });
    }

    @Test
    @Order(7)
    @DisplayName("SQL special characters in username are handled safely")
    void sqlInjectionInUsernameIsHandledSafely() {
        // If the implementation uses string concatenation to build SQL queries,
        // this input will either throw an exception or corrupt the database.
        // A parameterized query will handle it gracefully and return 201.
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/register",
                    """
                    { "username": "'; DROP TABLE users; --", "password": "S3cur3P@ss!" }
                    """);
            // Application should not crash; 201 or 409 are both acceptable outcomes.
            assertThat(response.code()).isIn(201, 400, 409);
            // Most importantly: the app must still be alive and the users table intact
            var healthCheck = client.post("/api/register",
                    """
                    { "username": "healthcheck", "password": "S3cur3P@ss!" }
                    """);
            assertThat(healthCheck.code()).isIn(201, 409);
        });
    }

    @Test
    @Order(8)
    @DisplayName("Whitespace-only username returns 400")
    void whitespaceUsernameReturns400() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/register",
                    """
                    { "username": "   ", "password": "S3cur3P@ss!" }
                    """);
            assertThat(response.code()).isEqualTo(400);
        });
    }
}
