package com.recipemanagement.service;

import com.recipemanagement.exception.UserNotFoundException;
import com.recipemanagement.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId) throws UserNotFoundException;

    User getUserById(Long userId) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

    Long getUserIdByUsername(String username);

    List<User> getAllUsers();

    boolean authenticateUser(String username, String password);

    boolean isUsernameAvailable(String username);
}