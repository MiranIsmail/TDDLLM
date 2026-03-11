package com.experiment.service;

import com.experiment.controller.LogController;
import com.experiment.model.Session;
import com.experiment.model.User;
import com.experiment.repository.SessionRepository;
import com.experiment.repository.UserRepository;

import java.util.Optional;

/**
 * Business logic for authentication.
 *
 * YOU MUST IMPLEMENT ALL METHODS.
 * This class sits between the HTTP layer (AuthController) and the database layer (repositories).
 */
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final LogController logController; // Used to log what happens

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, LogController logController) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.logController = logController;
    }

    /**
     * Registers a new user.
     *
     * @param username the desired username
     * @param password the plaintext password provided by the user
     * @throws IllegalArgumentException if username or password fail validation rules,
     *                                  or if the username is already taken
     */
    public void register(String username, String password) {
        // TODO: Implement registration logic
        logController.log("AuthService: register has been run with the parameters"+username+password);
    }

    /**
     * Authenticates a user and creates a session.
     *
     * @param username the provided username
     * @param password the provided plaintext password
     * @return a Session containing the token if credentials are valid
     * @throws IllegalArgumentException if credentials are invalid
     */
    public Session login(String username, String password) {
        // TODO: Implement login logic
        logController.log("AuthService: login has been run with the parameters"+username+password);
        throw new IllegalArgumentException("Not implemented yet");
    }

    /**
     * Looks up the user associated with a session token.
     *
     * @param token the session token from the request
     * @return an Optional containing the User if the token is valid and not expired
     */
    public Optional<User> getUserFromToken(String token) {
        // TODO: find session by token, then find user by session.getUserId()
        logController.log("AuthService: getUserFromToken has been run with the parameters"+token);
        return Optional.empty();
    }

    /**
     * Invalidates a session token.
     *
     * @param token the session token to invalidate
     */
    public void logout(String token) {
        // TODO: call sessionRepository.delete(token)
        logController.log("AuthService: logout has been run with the parameters"+token);
    }
}
