package com.experiment.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents a user in the system. - do not modify.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private Levels role;

    public User() {}

    public User(int id, String username, String password, Levels role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Levels getRole() {
        return role;
    }

    public void setRole(Levels role) {
        this.role = role;
    }

    public int getId()           { return id; }
    public String getUsername()  { return username; }
    public String getPassword()  { return password; }

    public void setId(int id)               { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public static String stringifyUser(int id, String username, long loggedTime, List<String> starts, List<String> ends) {
        StringBuilder json = new StringBuilder();

        json.append("{\n");
        json.append("  \"id\": ").append(id).append(",\n");
        json.append("  \"username\": \"").append(username).append("\",\n");
        json.append("  \"logged_time\": ").append(loggedTime).append(",\n");
        json.append("  \"sessions\": [\n");

        for (int i = 0; i < starts.size(); i++) {
            String startTime = starts.get(i);
            String endTime = (i < ends.size() && ends.get(i) != null) ? ends.get(i) : "";

            json.append("    {\n");
            json.append("      \"start_time\": \"").append(startTime).append("\",\n");
            json.append("      \"end_time\": \"").append(endTime).append("\"\n");
            json.append("    }");

            if (i < starts.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}");

        return json.toString();
    }
    public static long calculateTotalLoggedTime(List<String> starts, List<String> ends) {
        long totalSeconds = 0;
        // The format matching your DB: "2026-03-02 08:00:00"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < starts.size(); i++) {
            String startStr = starts.get(i);
            String endStr = (i < ends.size()) ? ends.get(i) : "";

            // Only calculate if the session is finished (endStr is not empty/null)
            if (endStr != null && !endStr.isEmpty()) {
                try {
                    LocalDateTime start = LocalDateTime.parse(startStr, formatter);
                    LocalDateTime end = LocalDateTime.parse(endStr, formatter);

                    // Calculate difference in seconds
                    Duration duration = Duration.between(start, end);
                    totalSeconds += duration.getSeconds();
                } catch (Exception e) {
                    System.out.println("Error parsing dates at index " + i + ": " + e.getMessage());
                }
            }
        }
        return totalSeconds;
    }
}

