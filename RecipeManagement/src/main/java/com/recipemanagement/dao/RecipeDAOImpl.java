package com.recipemanagement.dao;

import com.recipemanagement.exception.RecipeNotFoundException;
import com.recipemanagement.model.Recipe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class RecipeDAOImpl implements RecipeDAO {
    private final EntityManager em;

    public RecipeDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addRecipe(Recipe recipe) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            em.persist(recipe);

            et.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            em.merge(recipe);

            et.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteRecipe(Long recipeId) throws RecipeNotFoundException {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            Recipe recipe = em.find(Recipe.class, recipeId);
            if (recipe == null) {
                throw new RecipeNotFoundException("Recipe with ID " + recipeId + " not found.");
            }
            em.remove(recipe);

            et.commit();

        } catch (Exception e) {
            throw new RecipeNotFoundException(e.getMessage());
        }
    }

    @Override
    public Recipe getRecipeById(Long recipeId) throws RecipeNotFoundException {
        try {
            Recipe recipe = em.find(Recipe.class, recipeId);
            if (recipe == null) {
                throw new RecipeNotFoundException("Recipe with ID " + recipeId + " not found.");
            }
            return recipe;

        } catch (Exception e) {
            throw new RecipeNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = null;
        try {
            Query query = em.createQuery("SELECT r FROM Recipe r");
            recipes = query.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return recipes;
    }
}