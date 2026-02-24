package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GROUP B (TDD) — Logout Tests
 * Do not modify these tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogoutTest {

    // -------------------------------------------------------------------------
    // TASK 4: Logout (POST /api/logout)
    //
    // The endpoint must invalidate the session token.
    // -------------------------------------------------------------------------

    private String registerAndLogin(io.javalin.testtools.HttpClient client, String username) throws Exception {
        client.post("/api/register",
                String.format("""
                { "username": "%s", "password": "S3cur3P@ss!" }
                """, username));

        var loginResp = client.post("/api/login",
                String.format("""
                { "username": "%s", "password": "S3cur3P@ss!" }
                """, username));

        String body = loginResp.body().string();
        return body.replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
    }

    @Test
    @Order(1)
    @DisplayName("Logout with valid token returns 200")
    void logoutWithValidTokenReturns200() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            String token = registerAndLogin(client, "logoutuser");

            var response = client.post("/api/logout",
                    req -> req.header("Authorization", "Bearer " + token));

            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    @Order(2)
    @DisplayName("Token is invalidated after logout")
    void tokenIsInvalidatedAfterLogout() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            String token = registerAndLogin(client, "logoutuser2");

            // Logout
            client.post("/api/logout",
                    req -> req.header("Authorization", "Bearer " + token));

            // Attempt to use the invalidated token
            var profileResp = client.get("/api/profile",
                    req -> req.header("Authorization", "Bearer " + token));

            assertThat(profileResp.code()).isEqualTo(401);
        });
    }

    @Test
    @Order(3)
    @DisplayName("Logout without a token returns 401")
    void logoutWithoutTokenReturns401() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            var response = client.post("/api/logout");
            assertThat(response.code()).isEqualTo(401);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Logging out does not invalidate other active sessions")
    void logoutOnlyInvalidatesOwnSession() {
        // If logout deletes all sessions for a user instead of just the given token,
        // this test will fail — users should be able to be logged in from multiple devices
        JavalinTest.test(App.createApp(true), (server, client) -> {
            // Register once, login twice (simulating two devices)
            client.post("/api/register",
                    """
                    { "username": "multidevice", "password": "S3cur3P@ss!" }
                    """);

            var login1 = client.post("/api/login",
                    """
                    { "username": "multidevice", "password": "S3cur3P@ss!" }
                    """);
            var login2 = client.post("/api/login",
                    """
                    { "username": "multidevice", "password": "S3cur3P@ss!" }
                    """);

            String token1 = login1.body().string()
                    .replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
            String token2 = login2.body().string()
                    .replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");

            // Logout with token1
            client.post("/api/logout",
                    req -> req.header("Authorization", "Bearer " + token1));

            // token2 must still be valid
            var profileResp = client.get("/api/profile",
                    req -> req.header("Authorization", "Bearer " + token2));

            assertThat(profileResp.code()).isEqualTo(200);
        });
    }
}
