package com.recipemanagement.dao;

import com.recipemanagement.exception.UserNotFoundException;
import com.recipemanagement.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId) throws UserNotFoundException;

    User getUserById(Long userId) throws UserNotFoundException;

    Long getUserIdByUsername(String username);

    User getUserByUsername(String username) throws UserNotFoundException;

    List<User> getAllUsers();
}