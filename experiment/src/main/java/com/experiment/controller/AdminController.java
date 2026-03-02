package com.experiment.controller;

import io.javalin.http.Context;

public class AdminController {
    private final LogController logController;

    public AdminController(LogController logController) {
        this.logController = logController;
    }

    public void handleLoad(Context ctx) {
        String userJson = """
        [
            {
                "id": 1,
                "username": "j_doe_99",
                "logged_time": 45,
                "sessions": [
                    {"start_time": "2026-03-02 08:00:00", "end_time": "--:--:--"},
                    {"start_time": "2026-03-01 09:00:00", "end_time": "2026-03-01 17:00:00"}
                ]
            },
            {
                "id": 2,
                "username": "root_admin",
                "logged_time": 120,
                "sessions": [
                    {"start_time": "2026-03-02 10:00:00", "end_time": "2026-03-02 11:30:00"}
                ]
            }
        ]
        """;
        ctx.contentType("application/json").result(userJson);
    }

    public void handleOverride(Context ctx) {
        logController.changeInternalUser("POST", "/api/admin/override", "ADMIN_OVERRIDE: " + ctx.body());
        ctx.status(200).result("Logged");
    }
}