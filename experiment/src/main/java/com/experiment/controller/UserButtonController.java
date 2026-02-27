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
        logController.addInternal("START_ACTION", "/api/start", "Start button pressed");
        ctx.status(200).result("Started successfully");
    }

    public void handleStop(Context ctx) {
        logController.addInternal("STOP_ACTION", "/api/stop", "Stop button pressed");
        ctx.status(200).result("Stopped successfully");
    }
    public void handleLoad(Context ctx) {
        logController.addInternal("PAGE_LOAD", "/api/user/data", "Fetched session data");

        String example_data = """
        [
          {
            "date": "2026-02-27",
            "startTime": "08:00:00",
            "endTime": "10:30:00"
          },
          {
            "date": "2026-02-26",
            "startTime": "14:15:00",
            "endTime": "15:45:30"
          },
          {
            "date": "2026-02-25",
            "startTime": "09:00:00",
            "endTime": "09:45:00"
          }
        ]
        """;

        ctx.contentType("application/json");
        ctx.result(example_data);
    }
}