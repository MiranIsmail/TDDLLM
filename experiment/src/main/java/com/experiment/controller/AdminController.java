package com.experiment.controller;

import com.experiment.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminController {
    private final LogController logController;

    public AdminController(LogController logController) {
        this.logController = logController;
    }

    public void handleLoad(Context ctx) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> all_users = new ArrayList<>();

        // --- USER 1 DATA ---
        List<String> starts1 = Arrays.asList("2026-03-02 08:00:00", "2026-03-01 09:00:00", "2026-02-28 08:30:00");
        List<String> ends1 = Arrays.asList("", "2026-03-01 17:00:00", "2026-02-28 12:30:00");
        long time1 = User.calculateTotalLoggedTime(starts1, ends1);

        // --- USER 2 DATA ---
        List<String> starts2 = Arrays.asList("2026-03-02 10:00:00");
        List<String> ends2 = Arrays.asList("2026-03-02 11:30:00");
        long time2 = User.calculateTotalLoggedTime(starts2, ends2);

        // --- USER 3 DATA ---
        List<String> starts3 = Arrays.asList("2026-03-02 14:15:00", "2026-02-27 07:00:00");
        List<String> ends3 = Arrays.asList("", "2026-02-27 16:30:00");
        long time3 = User.calculateTotalLoggedTime(starts3, ends3);

        // --- USER 4 DATA ---
        List<String> starts4 = Arrays.asList("2026-03-01 18:00:00");
        List<String> ends4 = Arrays.asList("2026-03-01 22:00:00");
        long time4 = User.calculateTotalLoggedTime(starts4, ends4);

        // --- USER 5 DATA ---
        List<String> starts5 = Arrays.asList();
        List<String> ends5 = Arrays.asList();
        long time5 = User.calculateTotalLoggedTime(starts5, ends5);

        // --- USER 6 DATA ---
        List<String> starts6 = Arrays.asList("2026-03-02 06:00:00", "2026-03-01 06:00:00", "2026-02-28 08:00:00", "2026-02-27 08:00:00");
        List<String> ends6 = Arrays.asList("", "2026-03-01 18:00:00", "2026-02-28 20:00:00", "2026-02-27 20:00:00");
        long time6 = User.calculateTotalLoggedTime(starts6, ends6);

        try {
            // Now stringify them using the calculated times
            List<String> example_data = Arrays.asList(
                    User.stringifyUser(1, "j_doe_99", time1, starts1, ends1),
                    User.stringifyUser(2, "root_admin", time2, starts2, ends2),
                    User.stringifyUser(3, "s_connor_dev", time3, starts3, ends3),
                    User.stringifyUser(4, "a_skywalker", time4, starts4, ends4),
                    User.stringifyUser(5, "new_hire_user", time5, starts5, ends5),
                    User.stringifyUser(6, "m_murdock_law", time6, starts6, ends6)
            );

            // Parse into objects so Javalin formats [ ] in order to handle it frontend
            for (String s : example_data) {
                all_users.add(mapper.readTree(s));
            }

            ctx.json(all_users);

        } catch (Exception e) {
            ctx.status(500).result("JSON Error: " + e.getMessage());
        }
    }

    public void handleOverride(Context ctx) {
        logController.changeInternalUser("POST", "/api/admin/override", "ADMIN_OVERRIDE: " + ctx.body());
        ctx.status(200).result("Logged");
    }
}