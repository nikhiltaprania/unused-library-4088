package com.recipemanagement.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionManager {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("Connected");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}