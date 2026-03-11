package com.experiment;

import com.experiment.service.UserService;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

// -------------------------------------------------------------------------
// TASK: Write Unit tests for the UserService functions
// -------------------------------------------------------------------------
public class UserServiceTest {
    private Javalin app;
    private UserService userService;
    @BeforeEach
        // here we can set up the environment for the tests, this runs before each test
    void setUp() {
        app = App.createApp(true);
        userService = new UserService();
    }
    @AfterEach
        // here we can tear down the environment after each test so that the tests are not dependent
    void tearDown(){
        app.stop();
    }
/**
    @Test
    void usernameCannotBeNumericOnly() {
        assertThat(userService.validateUsername("12345")).isFalse();
    }
    @Test
    void validUsernameIsAccepted() {
        assertThat(userService.validateUsername("alice")).isTrue();
    }

    @Test // Not a real test, just an example
    void realWorkedHoursIsNotZero() {
        assertThat(userService.calculateRealWorkedHours(8)==7 ).isFalse();
    }
*/
}
