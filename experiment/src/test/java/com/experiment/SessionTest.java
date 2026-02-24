package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GROUP B (TDD) — Protected Endpoint & Session Tests
 * Do not modify these tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessionTest {

    // -------------------------------------------------------------------------
    // TASK 3: Protected resource (GET /api/profile)
    //
    // The endpoint must verify the session token sent in the Authorization header.
    // -------------------------------------------------------------------------

    private String obtainToken(io.javalin.testtools.HttpClient client) throws Exception {
        client.post("/api/register",
                """
                { "username": "sessionuser", "password": "S3cur3P@ss!" }
                """);

        var loginResp = client.post("/api/login",
                """
                { "username": "sessionuser", "password": "S3cur3P@ss!" }
                """);

        String body = loginResp.body().string();
        return body.replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
    }

    @Test
    @Order(1)
    @DisplayName("Valid token grants access to profile endpoint")
    void validTokenGrantsAccess() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            String token = obtainToken(client);

            var response = client.get("/api/profile",
                    req -> req.header("Authorization", "Bearer " + token));

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("sessionuser");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Missing Authorization header returns 401")
    void missingAuthHeaderReturns401() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.get("/api/profile");
            assertThat(response.code()).isEqualTo(401);
        });
    }

    @Test
    @Order(3)
    @DisplayName("Invalid token returns 401")
    void invalidTokenReturns401() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.get("/api/profile",
                    req -> req.header("Authorization", "Bearer this-is-not-a-real-token"));
            assertThat(response.code()).isEqualTo(401);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Malformed Authorization header (no Bearer prefix) returns 401")
    void malformedHeaderReturns401() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            String token = obtainToken(client);

            // Missing "Bearer " prefix — should not be accepted
            var response = client.get("/api/profile",
                    req -> req.header("Authorization", token));
            assertThat(response.code()).isEqualTo(401);
        });
    }

    @Test
    @Order(5)
    @DisplayName("Profile response does not include the stored password hash")
    void profileResponseDoesNotLeakPasswordHash() {
        // Even a hashed password should not be returned in API responses.
        JavalinTest.test(App.createApp(true), (server, client) -> {
            String token = obtainToken(client);

            var response = client.get("/api/profile",
                    req -> req.header("Authorization", "Bearer " + token));

            String body = response.body().string();
            assertThat(body).doesNotContain("password");
            assertThat(body).doesNotContain("$2a$"); // BCrypt hash prefix
        });
    }

    @Test
    @Order(6)
    @DisplayName("Token from one user cannot access another user's profile")
    void tokenCannotImpersonateAnotherUser() {
        // Verifies that the token is user-specific and not globally valid
        JavalinTest.test(App.createApp(true), (server, client) -> {
            // Register two users
            client.post("/api/register",
                    """
                    { "username": "user1", "password": "S3cur3P@ss!" }
                    """);
            client.post("/api/register",
                    """
                    { "username": "user2", "password": "S3cur3P@ss!" }
                    """);

            // Login as user1 and get token
            var login1 = client.post("/api/login",
                    """
                    { "username": "user1", "password": "S3cur3P@ss!" }
                    """);
            String token1 = login1.body().string()
                    .replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");

            // Use user1's token to get profile — must return user1's username, not user2
            var profileResp = client.get("/api/profile",
                    req -> req.header("Authorization", "Bearer " + token1));

            assertThat(profileResp.code()).isEqualTo(200);
            assertThat(profileResp.body().string()).contains("user1");
            assertThat(profileResp.body().string()).doesNotContain("user2");
        });
    }
}
