package com.recipemanagement.service;

import com.recipemanagement.dao.UserDAO;
import com.recipemanagement.exception.UserNotFoundException;
import com.recipemanagement.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        userDAO.deleteUser(userId);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        return userDAO.getUserById(userId);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userDAO.getUserIdByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return userDAO.getAllUsers().stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return userDAO.getAllUsers().stream()
                .noneMatch(user -> user.getUsername().equals(username));
    }
}