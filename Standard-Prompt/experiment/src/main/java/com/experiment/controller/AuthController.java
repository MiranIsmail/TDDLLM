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
        // TODO: Implement
        String body = ctx.body();
        LogController.getInstance().log("AuthController: Initialize has been pressed with the parameters:"+body);//example on how to use the logger
        ctx.status(201).result("registered successfully"); // placeholder
    }
    private void handleLogin(Context ctx) {
        String body = ctx.body();
        LogController.getInstance().log("AuthController:Login has been pressed with the parameters:"+body);

        // TODO: Implement

    }


    private void handleProfile(Context ctx) {
        // Standard profile logic (Placeholder for now)
        String body = ctx.body();
        LogController.getInstance().log("AuthController:Profile has been pressed with the parameters:"+body);
        // TODO: Implement
    }

    private void handleLogout(Context ctx) {
        // Standard logout logic (Placeholder for now)
        String body = ctx.body();
        LogController.getInstance().log("AuthController:Logout has been pressed with the parameters:"+body);
        // TODO: Implement
    }
}