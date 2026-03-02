package com.experiment.controller;

import io.javalin.http.Context;

public class AdminController {
    private final LogController logController;

    public AdminController(LogController logController) {
        this.logController = logController;
    }

    public void handleLoad(Context ctx) {
        String example_data = """
        [
            {
                "id": 1,
                "username": "j_doe_99",
                "logged_time": 45,
                "sessions": [
                    {"start_time": "2026-03-02 08:00:00", "end_time": ""},
                    {"start_time": "2026-03-01 09:00:00", "end_time": "2026-03-01 17:00:00"},
                    {"start_time": "2026-02-28 08:30:00", "end_time": "2026-02-28 12:30:00"}
                ]
            },
            {
                "id": 2,
                "username": "root_admin",
                "logged_time": 120,
                "sessions": [
                    {"start_time": "2026-03-02 10:00:00", "end_time": "2026-03-02 11:30:00"}
                ]
            },
            {
                "id": 3,
                "username": "s_connor_dev",
                "logged_time": 88,
                "sessions": [
                    {"start_time": "2026-03-02 14:15:00", "end_time": ""},
                    {"start_time": "2026-02-27 07:00:00", "end_time": "2026-02-27 16:30:00"}
                ]
            },
            {
                "id": 4,
                "username": "a_skywalker",
                "logged_time": 12,
                "sessions": [
                    {"start_time": "2026-03-01 18:00:00", "end_time": "2026-03-01 22:00:00"}
                ]
            },
            {
                "id": 5,
                "username": "new_hire_user",
                "logged_time": 0,
                "sessions": []
            },
            {
                "id": 6,
                "username": "m_murdock_law",
                "logged_time": 210,
                "sessions": [
                    {"start_time": "2026-03-02 06:00:00", "end_time": ""},
                    {"start_time": "2026-03-01 06:00:00", "end_time": "2026-03-01 18:00:00"},
                    {"start_time": "2026-02-28 08:00:00", "end_time": "2026-02-28 20:00:00"},
                    {"start_time": "2026-02-27 08:00:00", "end_time": "2026-02-27 20:00:00"}
                ]
            }
        ]
        """;
        ctx.contentType("application/json").result(example_data);
    }

    public void handleOverride(Context ctx) {
        logController.changeInternalUser("POST", "/api/admin/override", "ADMIN_OVERRIDE: " + ctx.body());
        ctx.status(200).result("Logged");
    }
}