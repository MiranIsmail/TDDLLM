package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Logout Tests
 */
class LogoutTest {

    // -------------------------------------------------------------------------
    // TASK: Logout (POST /api/logout)
    //
    // The endpoint must invalidate the session token.
    // -------------------------------------------------------------------------
    private String obtainToken(io.javalin.testtools.HttpClient client) throws Exception {
        client.post("/api/register",
                """
                { "username": "logoutuser", "password": "S3cur3P@ss!" }
                """);

        var loginResp = client.post("/api/login",
                """
                { "username": "logoutuser", "password": "S3cur3P@ss!" }
                """);

        String body = loginResp.body().string();
        return body.replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
    }

    @Test
    @DisplayName("Logout returns 200")
    void logoutReturns200() {
        JavalinTest.test(App.createApp(true), (server, client) -> {
            String token = obtainToken(client);

            var response = client.post("/api/logout","",
                    req -> req.header("Authorization", "Bearer " + token));

            assertThat(response.code()).isEqualTo(200);
        });
    }
}