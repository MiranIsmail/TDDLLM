package com.experiment.repository;

import com.experiment.Database;
import com.experiment.model.User;

import java.util.Optional;

/**
 * Handles persistence of User entities.
 *
 * YOU MUST IMPLEMENT ALL METHODS.
 * Use App.java as a reference for how this class is instantiated.
 */
public class UserRepository {

    private final Database db;

    public UserRepository(Database db) {
        this.db = db;
    }

    /**
     * Persists a new user to the database.
     *
     * @param username the desired username
     * @param hashedPassword the already-hashed password (never store plaintext)
     * @throws IllegalArgumentException if username already exists
     */
    public void save(String username, String hashedPassword) {
        // TODO: implement — INSERT INTO users (username, password) VALUES (?, ?)
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty if not
     */
    public Optional<User> findByUsername(String username) {
        // TODO: implement — SELECT * FROM users WHERE username = ?
        return Optional.empty();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the user ID
     * @return an Optional containing the User if found, or empty if not
     */
    public Optional<User> findById(int id) {
        // TODO: implement — SELECT * FROM users WHERE id = ?
        return Optional.empty();
    }
}
