package com.experiment.controller;

import io.javalin.http.Context;

public class AdminController {
    // Reference to your existing log controller
    private final LogController logController;

    // Updated constructor to allow logging
    public AdminController(LogController logController) {
        this.logController = logController;
    }

    public void handleLoad(Context ctx) {
        String userJson = """
        [
            {
                "userId": "j_doe_99",
                "startTime": "08:15:22",
                "stopTime": "--:--:--",
                "hours": "8.5h"
            },
            {
                "userId": "root_admin",
                "startTime": "09:00:00",
                "stopTime": "--:--:--",
                "hours": "1.0h"
            },
            {
                "userId": "m_smith_dev",
                "startTime": "07:30:15",
                "stopTime": "17:30:45",
                "hours": "10.0h"
            }
        ]
        """;

        ctx.contentType("application/json");
        ctx.result(userJson);
    }

    /**
     * Captures the data from the sidebar and logs it.
     */
    public void handleOverride(Context ctx) {
        // Get the JSON data sent from the sidebar Commit button
        String overrideBody = ctx.body();

        // Call the new dedicated admin log method
        logController.changeInternalUser(
                "POST",
                "/api/admin/override",
                "OVERRIDE_DATA: " + overrideBody
        );

        ctx.status(200).result("Override logged.");
    }
}