package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Login Tests
 */
class LoginTest {

    // -------------------------------------------------------------------------
    // TASK: User Login
    //
    // Implement the POST /api/login endpoint.
    // below is an example of a test and not a real test, this will most likely fail!!
    // -------------------------------------------------------------------------

    private static final String REGISTER_BODY =
            """
            { "username": "testuser", "password": "S3cur3P@ss!" }
            """;
/**
    @Test
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
    */
}
