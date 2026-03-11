package com.experiment.controller;

import com.experiment.model.User;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;

public class UserController {

    // You need a reference to your log controller or service
    private final LogController logController;

    public UserController(LogController logController) {
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

            // Mock data to simulate DB output
            // 6 Start Times (Newest to Oldest)
            // you need to fetch this data from the db yourselfs and save it in this list structure
            List<String> starts = Arrays.asList(
                    "2026-03-03 08:00:00", // newest
                    "2026-03-02 08:30:00",
                    "2026-03-01 09:00:00",
                    "2026-02-28 08:15:00",
                    "2026-02-27 08:00:00",
                    "2026-02-26 09:30:00"  // Oldest
            );

            // 6 End Times (Matching indices)
            List<String> ends = Arrays.asList(
                    "",                    // Active session
                    "2026-03-02 17:00:00",
                    "2026-03-01 17:30:00",
                    "2026-02-28 12:00:00",
                    "2026-02-27 16:45:00",
                    "2026-02-26 18:00:00" // oldest
            );

        long LoggedTime = User.calculateTotalLoggedTime(starts, ends);
            // Call your function
            String example_data = User.stringifyUser(1, "j_doe_99", LoggedTime, starts, ends);

        ctx.contentType("application/json");
        ctx.result(example_data);
    }
}