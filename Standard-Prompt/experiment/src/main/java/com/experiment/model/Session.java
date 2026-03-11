package com.experiment.model;

import java.time.LocalDateTime;

/**
 * Represents an authenticated session. PROVIDED - do not modify.
 */
public class Session {
    private String token;
    private int userId;
    private LocalDateTime expiresAt;

    public Session() {}

    public Session(String token, int userId, LocalDateTime expiresAt) {
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    public String getToken()           { return token; }
    public int getUserId()             { return userId; }
    public LocalDateTime getExpiresAt(){ return expiresAt; }

    public void setToken(String token)             { this.token = token; }
    public void setUserId(int userId)              { this.userId = userId; }
    public void setExpiresAt(LocalDateTime exp)    { this.expiresAt = exp; }
}
