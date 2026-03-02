package com.experiment.model;

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
}
