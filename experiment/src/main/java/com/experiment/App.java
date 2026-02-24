package com.experiment;

import com.experiment.controller.AuthController;
import com.experiment.repository.SessionRepository;
import com.experiment.repository.UserRepository;
import com.experiment.service.AuthService;
import io.javalin.Javalin;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Application entry point. PROVIDED - do not modify.
 *
 * Wires together the database, repositories, service, and controller.
 * Your task is to implement the classes listed below.
 *
 * Test UI is available at http://localhost:7070 once the app is running.
 */
public class App {

    public static void main(String[] args) {
        Javalin app = createApp(false);
        app.start(7070);
        System.out.println("\n  ✓ Server running → http://localhost:7070\n");
    }

    /**
     * Creates and configures the Javalin application.
     *
     * @param testMode if true, uses a separate in-memory DB instance for tests
     * @return configured Javalin instance (not started)
     */
    public static Javalin createApp(boolean testMode) {
        Database db               = new Database(testMode);
        UserRepository userRepo   = new UserRepository(db);        // YOU IMPLEMENT THIS
        SessionRepository sessRepo = new SessionRepository(db);    // YOU IMPLEMENT THIS
        AuthService authService   = new AuthService(userRepo, sessRepo); // YOU IMPLEMENT THIS
        AuthController controller = new AuthController(authService);     // YOU IMPLEMENT THIS

        Javalin app = Javalin.create(config -> {
            config.useVirtualThreads = true;
        });

        // Serve the browser test console at root — PROVIDED, do not modify
        app.get("/", ctx -> {
            try (InputStream is = App.class.getResourceAsStream("/test-ui.html")) {
                if (is == null) {
                    ctx.result("Test UI not found").status(500);
                    return;
                }
                ctx.contentType("text/html; charset=utf-8");
                ctx.result(new String(is.readAllBytes(), StandardCharsets.UTF_8));
            }
        });

        controller.registerRoutes(app);
        return app;
    }
}
