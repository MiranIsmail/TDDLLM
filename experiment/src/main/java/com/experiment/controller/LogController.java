package com.experiment.controller;

import com.experiment.model.LogEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogController {
    // The physical file on your hard drive
    private static final String FILE_PATH = "system_logs.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // We use a synchronized list to prevent threading issues
    private static List<LogEntry> logHistory = Collections.synchronizedList(new ArrayList<>());

    public LogController() {
        loadLogsFromFile();
    }

    // GET /api/internal/logs
    public void getAll(Context ctx) {
        ctx.json(logHistory);
    }

    // POST /api/internal/logs
    public void add(Context ctx) {
        LogEntry entry = ctx.bodyAsClass(LogEntry.class);
        logHistory.add(entry);
        saveLogsToFile();
        ctx.status(201);
    }

    // DELETE /api/internal/logs
    public void clear(Context ctx) {
        logHistory.clear();
        saveLogsToFile();
        ctx.status(204);
    }

    private void saveLogsToFile() {
        // We synchronize here so only one thread writes to the file at a time
        synchronized (logHistory) {
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), logHistory);
            } catch (IOException e) {
                System.err.println("Could not save logs to JSON: " + e.getMessage());
            }
        }
    }

    private void loadLogsFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                // Read the JSON file and convert back to our List
                List<LogEntry> loadedLogs = mapper.readValue(file, new TypeReference<List<LogEntry>>() {});
                logHistory.addAll(loadedLogs);
                System.out.println("Successfully loaded " + logHistory.size() + " logs from disk.");
            } catch (IOException e) {
                System.err.println("Could not load existing logs: " + e.getMessage());
            }
        }
    }
    public void addInternalTime(String method, String endpoint, String responseBody) {
        // 1. Generate the timestamp matching your UI format (HH:mm:ss)
        String timestamp = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));

        // 2. Create the LogEntry object (Assuming your model has this constructor)
        // Passing "200" for status and "User Interacted" as the request body
        LogEntry entry = new LogEntry(timestamp, method, endpoint, 200, "User Interacted", responseBody);

        // 3. Add to the list and save to the physical JSON file
        logHistory.add(entry);
        saveLogsToFile();

    }
    public void addInternalLogin(String method, String endpoint, String requestBody, String responseBody) {
        String timestamp = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        LogEntry entry = new LogEntry(timestamp, method, endpoint, 404, requestBody, responseBody);
        logHistory.add(entry);
        saveLogsToFile();
    }
}