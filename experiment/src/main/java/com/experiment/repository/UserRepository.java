package com.experiment.repository;

import com.experiment.Database;
import com.experiment.controller.LogController;
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
    private final LogController logController; // Used to log what happens


    public UserRepository(Database db,LogController logController) {
        this.db = db;
        this.logController = logController;
    }

    /**
     * Persists a new user to the database.
     *
     * @param username the desired username
     * @param hashedPassword the already-hashed password (never store plaintext)
     * @throws IllegalArgumentException if username already exists
     */
    public void save(String username, String hashedPassword) {
        // TODO: implement
        logController.log("Database: Save has been run in UserRepository, the input parameters was"+username+hashedPassword);
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty if not
     */
    public Optional<User> findByUsername(String username) {
        // TODO: implement
        logController.log("Database: FindByUsername has been run in UserRepository, the input parameter was"+username);
        return Optional.empty();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the user ID
     * @return an Optional containing the User if found, or empty if not
     */
    public Optional<User> findById(int id) {
        // TODO: implement
        logController.log("Database: findById has been run in UserRepository, the input parameter was"+id);
        return Optional.empty();
    }
    /**
    * Save the logged hours of a user
     * see also UserService.calculateRealWorkedHours
     */
    public void saveLoggedHours() {
        // TODO: implement
        logController.log("Database: Save Logged hours has been run in UserRepository, the input parameters was");
    }
}
