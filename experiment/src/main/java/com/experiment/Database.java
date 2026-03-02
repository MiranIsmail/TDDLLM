package com.experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database utility class.
 *
 * Production mode: writes to ./auth-experiment.db (persists across restarts)
 * Test mode:       uses an in-memory SQLite instance (wiped between test runs)
 */
public class Database {

    // SQLite connection URLs
    private static final String PROD_URL = "jdbc:sqlite:auth-experiment.db";
    private static final String TEST_URL = "jdbc:sqlite::memory:";

    private final String url;

    public Database(boolean testMode) {
        this.url = testMode ? TEST_URL : PROD_URL;
        initialize();
    }

    private void initialize() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // SQLite requires 'AUTOINCREMENT' (no underscore)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id        INTEGER PRIMARY KEY AUTOINCREMENT,
                    username  VARCHAR(100) NOT NULL UNIQUE,
                    password  VARCHAR(255) NOT NULL,
                    user_role  VARCHAR(32) NOT NULL DEFAULT 'USER',                    
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS sessions (
                    token      VARCHAR(255) PRIMARY KEY,
                    user_id    INTEGER NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    expires_at TIMESTAMP NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS data (
                    user_id    INTEGER NOT NULL,
                    start_time TIMESTAMP NOT NULL,
                    end_time TIMESTAMP NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public Connection getConnection() throws SQLException {
        // SQLite doesn't require username/password credentials
        Connection conn = DriverManager.getConnection(url);

        // SQLite does not enforce foreign keys by default.
        // We must enable them on every new connection.
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conn;
    }

    /** Clears all data between tests without dropping schema */
    public void clearAll() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM sessions");
            stmt.execute("DELETE FROM users");
            stmt.execute("DELETE FROM data");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear database", e);
        }
    }
}