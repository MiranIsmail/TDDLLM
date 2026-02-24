package com.experiment.repository;

import com.experiment.Database;
import com.experiment.model.Session;

import java.util.Optional;

/**
 * Handles persistence of Session entities.
 *
 * YOU MUST IMPLEMENT ALL METHODS.
 */
public class SessionRepository {

    private final Database db;

    public SessionRepository(Database db) {
        this.db = db;
    }

    /**
     * Persists a new session token linked to a user.
     *
     * @param session the session to store
     */
    public void save(Session session) {
        // TODO: implement — INSERT INTO sessions (token, user_id, expires_at) VALUES (?, ?, ?)
    }

    /**
     * Finds a session by its token.
     *
     * @param token the session token
     * @return an Optional containing the Session if found and not expired, or empty
     */
    public Optional<Session> findByToken(String token) {
        // TODO: implement — SELECT * FROM sessions WHERE token = ? AND expires_at > NOW()
        return Optional.empty();
    }

    /**
     * Deletes a session, effectively logging the user out.
     *
     * @param token the session token to invalidate
     */
    public void delete(String token) {
        // TODO: implement — DELETE FROM sessions WHERE token = ?
    }
}
