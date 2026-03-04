package com.experiment.service;

import com.experiment.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     1. TEMPLATE: Authorize Username
     */
    public boolean validateUsername(String username) {
        // Implement validation

        // Example where username is not allowed to be an integer
        try {
            Integer.parseInt(username);
            System.out.println("username is not allowed the be an integer");
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     2. TEMPLATE: Authorize Password
     */
    public boolean validatePassword(String password) {
        // Implement validation
        return true;
    }

    /**
     3. TEMPLATE: Input Start and End Times
     */
    public boolean validateStartAndEnd(List<String> starts, List<String> ends) {
        // Implement validation

        return true;
    }

    /**
     4. TEMPLATE: Create new user
     */
    public boolean validateNewUser(int id, String name, String password) {
        if (!this.validatePassword(name)) {
            System.out.println("username validation failed");
            return false;
        }
        if (!this.validatePassword(name)) {
            System.out.println("password validation failed");

            return false;
        }
        return true;
    }
}