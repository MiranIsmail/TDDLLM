package com.experiment.controller;

import io.javalin.http.Context;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UserButtonController {

    // You need a reference to your log controller or service
    private final LogController logController;

    public UserButtonController(LogController logController) {
        this.logController = logController;
    }

    public void handleStart(Context ctx) {
        logController.addInternalTime("START_ACTION", "/api/start", "Start button pressed");
        ctx.status(200).result("Started successfully");
    }

    public void handleStop(Context ctx) {
        logController.addInternalTime("STOP_ACTION", "/api/stop", "Stop button pressed");
        ctx.status(200).result("Stopped successfully");
    }
    public void handleLoad(Context ctx) {
        logController.addInternalTime("PAGE_LOAD", "/api/user/data", "Fetched session data");
        // This matches the structure: List of Users -> List of Sessions
        String example_data = """
    [
        {
            "id": 1,
            "username": "j_doe_99",
            "logged_time": 45,
            "sessions": [
                {"start_time": "2026-03-02 08:00:00", "end_time": ""},
                {"start_time": "2026-03-01 09:00:00", "end_time": "2026-03-01 17:21:03"},
                {"start_time": "2026-02-28 08:30:00", "end_time": "2026-02-28 12:30:00"}
            ]
        }
    ]
    """;

        ctx.contentType("application/json");
        ctx.result(example_data);
    }
}