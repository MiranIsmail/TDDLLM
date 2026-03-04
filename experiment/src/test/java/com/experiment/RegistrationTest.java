package com.experiment;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Registration Tests
 *
 */
class RegistrationTest {

    // -------------------------------------------------------------------------
    // TASK: User Registration
    //
    // Implement the POST /api/register endpoint.
    // below is an example of a test and not a real test, this will most likely fail!!
    // -------------------------------------------------------------------------

    @Test
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
}
