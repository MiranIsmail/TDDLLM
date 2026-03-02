package com.experiment.controller;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Universal Logger.
 * Handles terminal output via SLF4J.
 */
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    private static LogController instance;

    public LogController() {}

    public static synchronized LogController getInstance() {
        if (instance == null) {
            instance = new LogController();
        }
        return instance;
    }

    /**
     * The Universal Log Method
     * Use this for general system messages.
     * Usage: LogController.getInstance().log("Database initialized");
     */
    public void log(String message) {
        logger.info(message);
    }

    // --- JAVALIN HANDLERS ---

    public void add(Context ctx) {
        // Log an external request body directly to the terminal
        String body = ctx.body();
        logger.info("External API Log: {}", body);
        ctx.status(201);
    }

    public void clear(Context ctx) {
        logger.warn("Clear logs requested - No action taken (Persistence disabled).");
        ctx.status(204);
    }

    // --- INTERNAL HELPER LOGS ---
    // These now simply format messages for the terminal console.

    public void addInternalTime(String method, String endpoint, String action) {
        logger.info("[ACTION] {} {} | Details: {}", method, endpoint, action);
    }

    public void addInternalLogin(String method, String endpoint, String user, String result) {
        logger.warn("[AUTH] {} {} | User: {} | Result: {}", method, endpoint, user, result);
    }

    public void changeInternalUser(String method, String endpoint, String details) {
        logger.info("[ADMIN] {} {} | Change: {}", method, endpoint, details);
    }
}