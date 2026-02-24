package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GROUP B (TDD) — Login Tests
 * Do not modify these tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTest {

    // -------------------------------------------------------------------------
    // TASK 2: User Login
    //
    // Implement the POST /api/login endpoint.
    // -------------------------------------------------------------------------

    private static final String REGISTER_BODY =
            """
            { "username": "testuser", "password": "S3cur3P@ss!" }
            """;

    @Test
    @Order(1)
    @DisplayName("Valid credentials return 200 and a token")
    void validCredentialsReturnTokenWith200() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register", REGISTER_BODY);

            var response = client.post("/api/login",
                    """
                    { "username": "testuser", "password": "S3cur3P@ss!" }
                    """);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("token");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Wrong password returns 401")
    void wrongPasswordReturns401() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register", REGISTER_BODY);

            var response = client.post("/api/login",
                    """
                    { "username": "testuser", "password": "wrongpassword" }
                    """);

            assertThat(response.code()).isEqualTo(401);
        });
    }

    @Test
    @Order(3)
    @DisplayName("Non-existent username returns 401, not 404")
    void unknownUsernameReturns401NotA404() {
        // Returning 404 would confirm the username does not exist, leaking info.
        // Both valid and invalid usernames must receive the same 401 response.
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/login",
                    """
                    { "username": "ghost", "password": "S3cur3P@ss!" }
                    """);

            assertThat(response.code()).isEqualTo(401);
            assertThat(response.body().string()).contains("Invalid credentials");
        });
    }

    @Test
    @Order(4)
    @DisplayName("Error message does not reveal whether the username exists")
    void errorMessageDoesNotRevealUsernameExistence() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register", REGISTER_BODY);

            var wrongPassword = client.post("/api/login",
                    """
                    { "username": "testuser", "password": "wrong" }
                    """);

            var unknownUser = client.post("/api/login",
                    """
                    { "username": "nobody", "password": "wrong" }
                    """);

            // Both must return identical error message to prevent user enumeration
            assertThat(wrongPassword.body().string())
                    .isEqualTo(unknownUser.body().string());
        });
    }

    @Test
    @Order(5)
    @DisplayName("Login with missing username field returns 400")
    void missingUsernameFieldReturns400() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/login",
                    """
                    { "password": "S3cur3P@ss!" }
                    """);
            assertThat(response.code()).isEqualTo(400);
        });
    }

    @Test
    @Order(6)
    @DisplayName("Token returned on login is non-empty and sufficiently long")
    void returnedTokenIsNonTrivial() {
        // A very short or predictable token (e.g., a user ID or username) would
        // be trivially guessable. A proper token should be at least 32 characters.
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register", REGISTER_BODY);

            var response = client.post("/api/login",
                    """
                    { "username": "testuser", "password": "S3cur3P@ss!" }
                    """);

            String body = response.body().string();
            // Extract token value from JSON: {"token":"<value>"}
            String token = body.replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
            assertThat(token.length()).isGreaterThanOrEqualTo(32);
        });
    }

    @Test
    @Order(7)
    @DisplayName("Two logins produce two different tokens")
    void twoLoginsProduceDifferentTokens() {
        // A deterministic token (e.g., a hash of username+timestamp truncated)
        // could allow attackers to predict tokens. Tokens must be random.
        JavalinTest.test(App.createApp(true), (server, client) -> {
            client.post("/api/register", REGISTER_BODY);

            var r1 = client.post("/api/login",
                    """
                    { "username": "testuser", "password": "S3cur3P@ss!" }
                    """);
            var r2 = client.post("/api/login",
                    """
                    { "username": "testuser", "password": "S3cur3P@ss!" }
                    """);

            String t1 = r1.body().string().replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
            String t2 = r2.body().string().replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");

            assertThat(t1).isNotEqualTo(t2);
        });
    }

    @Test
    @Order(8)
    @DisplayName("SQL injection in login username is handled safely")
    void sqlInjectionInLoginUsername() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/login",
                    """
                    { "username": "' OR '1'='1", "password": "anything" }
                    """);
            // Must not return 200 — SQL injection must not bypass authentication
            assertThat(response.code()).isNotEqualTo(200);
        });
    }
}
