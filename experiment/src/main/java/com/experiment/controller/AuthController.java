package com.experiment.controller;

import com.experiment.service.AuthService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AuthController {

    private final AuthService authService;
    private final LogController logController; // Used to log what happens

    public AuthController(AuthService authService, LogController logController) {
        this.authService = authService;
        this.logController = logController;
    }

    public void registerRoutes(Javalin app) {
        // Map the routes to the handler methods
        app.post("/api/register", this::handleRegister);
        app.post("/api/login", this::handleLogin);
        app.get("/api/profile", this::handleProfile);
        app.post("/api/logout", this::handleLogout);
    }

    private void handleRegister(Context ctx) {
        // Standard registration logic (Placeholder for now)
        LogController.getInstance().log("New user registered:");//example on how to use the logger
        ctx.status(201).result("{\"message\": \"User registered successfully\"}");
    }
    private void handleLogin(Context ctx) {
        String userJson = ctx.body();

        logController.addInternalLogin(
                "POST",
                "/api/register",
                userJson,
                "{\"raw\": \"Endpoint POST /api/register not found\"}"
        );

        ctx.status(404).result("{\"raw\": \"Endpoint POST /api/register not found\"}");
    }


    private void handleProfile(Context ctx) {
        // Standard profile logic (Placeholder for now)
        ctx.status(200).result("{\"username\": \"root\"}");
    }

    private void handleLogout(Context ctx) {
        // Standard logout logic (Placeholder for now)
        ctx.status(200).result("{\"message\": \"Logged out\"}");
    }
}