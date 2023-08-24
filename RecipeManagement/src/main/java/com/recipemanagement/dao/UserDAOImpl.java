package com.recipemanagement.dao;

import com.recipemanagement.exception.UserNotFoundException;
import com.recipemanagement.model.User;
import jakarta.persistence.*;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final EntityManager em;

    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addUser(User user) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            em.persist(user);

            et.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            em.merge(user);

            et.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            User user = em.find(User.class, userId);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
            em.remove(user);

            et.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
            return user;

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public Long getUserIdByUsername(String username) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT u.id FROM User u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
            query.setParameter("username", username);

            List<User> users = query.getResultList();
            if (users.isEmpty()) {
                throw new UserNotFoundException("User with username " + username + " not found.");
            }
            return users.get(0);

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            Query query = em.createQuery("SELECT u FROM User u");
            users = query.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return users;
    }
}