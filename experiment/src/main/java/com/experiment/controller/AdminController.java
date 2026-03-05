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
        List<Double> payList1 = Arrays.asList(0.00, 150.50, 80.25);
        double total1 = 230.75; // Finished value
        long time1 = User.calculateTotalLoggedTime(starts1, ends1);

        // --- USER 2 DATA ---
        List<String> starts2 = Arrays.asList("2026-03-02 10:00:00");
        List<String> ends2 = Arrays.asList("2026-03-02 11:30:00");
        List<Double> payList2 = Arrays.asList(45.00);
        double total2 = 45.00; // Finished value
        long time2 = User.calculateTotalLoggedTime(starts2, ends2);

        // --- USER 6 DATA (Example of multiple sessions) ---
        List<String> starts6 = Arrays.asList("2026-03-02 06:00:00", "2026-03-01 06:00:00", "2026-02-28 08:00:00");
        List<String> ends6 = Arrays.asList("", "2026-03-01 18:00:00", "2026-02-28 20:00:00");
        List<Double> payList6 = Arrays.asList(0.00, 200.00, 240.00);
        double total6 = 440.00; // Finished value
        long time6 = User.calculateTotalLoggedTime(starts6, ends6);

        try {
            List<String> example_data = Arrays.asList(
                    User.stringifyUser(1, "j_doe_99", time1, total1, payList1, starts1, ends1),
                    User.stringifyUser(2, "root_admin", time2, total2, payList2, starts2, ends2),
                    User.stringifyUser(6, "m_murdock_law", time6, total6, payList6, starts6, ends6)
            );

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