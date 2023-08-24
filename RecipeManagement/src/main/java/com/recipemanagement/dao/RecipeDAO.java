package com.recipemanagement.dao;

import com.recipemanagement.exception.RecipeNotFoundException;
import com.recipemanagement.model.Recipe;

import java.util.List;

public interface RecipeDAO {
    void addRecipe(Recipe recipe);

    void updateRecipe(Recipe recipe);

    void deleteRecipe(Long recipeId) throws RecipeNotFoundException;

    Recipe getRecipeById(Long recipeId) throws RecipeNotFoundException;

    List<Recipe> getAllRecipes();
}