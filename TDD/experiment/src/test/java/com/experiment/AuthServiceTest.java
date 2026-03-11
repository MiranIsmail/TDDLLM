package com.experiment;

import com.experiment.controller.LogController;
import com.experiment.repository.SessionRepository;
import com.experiment.repository.UserRepository;
import com.experiment.service.AuthService;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


// -------------------------------------------------------------------------
// TASK: Write Unit tests for the AuthService functions
// -------------------------------------------------------------------------
public class AuthServiceTest {
    private Javalin app;
    private AuthService authService;



    @BeforeEach
        // here we can set up the environment for the tests, this runs before each test
    void setUp() {
        app = App.createApp(true);
        Database db = new Database(true);
        LogController log = LogController.getInstance();
        UserRepository userRepo = new UserRepository(db, log);
        SessionRepository sessRepo = new SessionRepository(db, log);
        authService = new AuthService(userRepo, sessRepo, log);
    }
    @AfterEach
        // here we can tear down the environment after each test so that the tests are not dependent
    void tearDown(){
        app.stop();
    }
    /**
    @Test
    void registeringDuplicateUsernameThrowsException() {
        authService.register("alice", "S3cur3P@ss!");

        // Registering the same username a second time should fail
        assertThatThrownBy(() -> authService.register("alice", "S3cur3P@ss!"))
                .isInstanceOf(IllegalArgumentException.class);
    }
    */
}
