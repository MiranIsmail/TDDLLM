package com.experiment.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LogEntry(
        String timestamp,
        String method,
        String endpoint,
        int status,
        String requestBody,
        String responseBody
) {
    // Helper to create an entry with a current timestamp automatically
    public static LogEntry create(String method, String endpoint, int status, String req, String res) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return new LogEntry(ts, method, endpoint, status, req, res);
    }
}