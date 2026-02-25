package com.experiment;

import com.experiment.controller.AuthController;
import com.experiment.controller.LogController; // Import this
import com.experiment.repository.SessionRepository;
import com.experiment.repository.UserRepository;
import com.experiment.service.AuthService;
import io.javalin.Javalin;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) {
        Javalin app = createApp(false);
        app.start(8080);
        System.out.println("\n  ✓ Server running → http://localhost:8080\n");
    }

    public static Javalin createApp(boolean testMode) {
        Database db               = new Database(testMode);
        UserRepository userRepo   = new UserRepository(db);
        SessionRepository sessRepo = new SessionRepository(db);
        AuthService authService   = new AuthService(userRepo, sessRepo);
        AuthController controller = new AuthController(authService);

        // 1. Initialize the new LogController
        LogController logController = new LogController();

        Javalin app = Javalin.create(config -> {
            config.useVirtualThreads = true;
        });

        // --- UI ROUTES ---

        // Root Console
        app.get("/", ctx -> serveStatic(ctx, "/test-ui.html"));

        // 2. Add route for the Log Page
        app.get("/logs", ctx -> serveStatic(ctx, "/log.html"));

        // --- API ROUTES ---

        controller.registerRoutes(app);

        // 3. Register the Internal Log API routes
        app.get("/api/internal/logs", logController::getAll);
        app.post("/api/internal/logs", logController::add);
        app.delete("/api/internal/logs", logController::clear);

        return app;
    }

    /**
     * Helper to keep the route definitions clean
     */
    private static void serveStatic(io.javalin.http.Context ctx, String resourcePath) {
        try (InputStream is = App.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                ctx.result("Resource not found: " + resourcePath).status(404);
                return;
            }
            ctx.contentType("text/html; charset=utf-8");
            ctx.result(new String(is.readAllBytes(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            ctx.status(500).result("Error loading resource");
        }
    }
}