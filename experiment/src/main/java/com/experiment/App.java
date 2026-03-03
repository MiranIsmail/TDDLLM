package com.experiment;

import com.experiment.controller.AdminController;
import com.experiment.controller.AuthController;
import com.experiment.controller.LogController;
import com.experiment.controller.UserController;
import com.experiment.repository.SessionRepository;
import com.experiment.repository.UserRepository;
import com.experiment.service.AuthService;
import io.javalin.Javalin;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Javalin app = createApp(false);
        app.start(8080);
        System.out.println("\n  ✓ Server running → http://localhost:8080\n");
    }

    public static Javalin createApp(boolean testMode) {
        LogController logController = LogController.getInstance();
        Database db               = new Database(testMode);
        UserRepository userRepo   = new UserRepository(db,logController);
        SessionRepository sessRepo = new SessionRepository(db,logController);
        AuthService authService   = new AuthService(userRepo, sessRepo,logController);
        AuthController controller = new AuthController(authService, logController);
        AdminController adminController = new AdminController(logController);


        Javalin app = Javalin.create(config -> {
            config.useVirtualThreads = true;
        });

        // --- ROUTES ---
        app.get("/api/admin/users", adminController::handleLoad);
        app.post("/api/admin/override", adminController::handleOverride);
        app.get("/", ctx -> serveStatic(ctx, "/login.html"));
        UserController userController = new UserController(logController);
        app.post("/api/start", userController::handleStart);
        app.post("/api/stop", userController::handleStop);
        app.get("/api/user/data", userController::handleLoad);
        app.get("/admin", ctx -> serveStatic(ctx, "/admin_page.html"));
        app.get("/user", ctx -> serveStatic(ctx, "/user_page.html"));
        app.get("/login", ctx -> serveStatic(ctx, "/login.html"));
        app.get("/register", ctx -> serveStatic(ctx, "/register.html"));
        controller.registerRoutes(app);
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