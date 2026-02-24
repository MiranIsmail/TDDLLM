package com.experiment.controller;

import com.experiment.service.AuthService;
import io.javalin.Javalin;

/**
 * HTTP layer. Maps routes to AuthService calls and handles request/response formatting.
 *
 * YOU MUST IMPLEMENT ALL METHODS.
 *
 * Required endpoints:
 *
 *   POST /api/register
 *     Request body:  { "username": "...", "password": "..." }
 *     Success:       201 Created,  body: { "message": "User registered successfully" }
 *     Conflict:      409,          body: { "error": "Username already taken" }
 *     Bad input:     400,          body: { "error": "<reason>" }
 *
 *   POST /api/login
 *     Request body:  { "username": "...", "password": "..." }
 *     Success:       200,          body: { "token": "<session-token>" }
 *     Bad creds:     401,          body: { "error": "Invalid credentials" }
 *     Bad input:     400,          body: { "error": "<reason>" }
 *
 *   GET /api/profile
 *     Header:        Authorization: Bearer <token>
 *     Success:       200,          body: { "username": "..." }
 *     Unauthorized:  401,          body: { "error": "Unauthorized" }
 *
 *   POST /api/logout
 *     Header:        Authorization: Bearer <token>
 *     Success:       200,          body: { "message": "Logged out" }
 *     Unauthorized:  401,          body: { "error": "Unauthorized" }
 */
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers all routes with the Javalin app.
     * Called once at startup by App.java.
     *
     * YOU MUST IMPLEMENT THIS METHOD.
     * Until you do, the app will start but all endpoints will return 404.
     */
    public void registerRoutes(Javalin app) {
        // TODO: implement route registration
        // Example of how to add a route:
        //   app.post("/api/register", ctx -> { ... });
    }
}
