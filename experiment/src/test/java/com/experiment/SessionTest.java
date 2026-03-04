package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Protected Endpoint & Session Tests
 */
class SessionTest {

    // -------------------------------------------------------------------------
    // TASK: Protected resource (GET /api/profile)
    //
    // The endpoint must verify the session token sent in the Authorization header.
    // below is an example of a test and not a real test, this will most likely fail!!
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
}
