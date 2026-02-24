package com.experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database utility class. PROVIDED - do not modify.
 *
 * Production mode: writes to ./auth-experiment.db (persists across restarts)
 * Test mode:       uses a named in-memory H2 instance (wiped between test runs)
 */
public class Database {

    private static final String PROD_URL = "jdbc:h2:file:./auth-experiment;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE";
    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";

    private final String url;

    public Database(boolean testMode) {
        this.url = testMode ? TEST_URL : PROD_URL;
        initialize();
    }

    private void initialize() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id        INTEGER PRIMARY KEY AUTO_INCREMENT,
                    username  VARCHAR(100) NOT NULL UNIQUE,
                    password  VARCHAR(255) NOT NULL,
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

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, "sa", "");
    }

    /** Clears all data between tests without dropping schema */
    public void clearAll() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM sessions");
            stmt.execute("DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear database", e);
        }
    }
}
