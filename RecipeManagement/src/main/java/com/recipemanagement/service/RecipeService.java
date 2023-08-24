package com.recipemanagement.service;

import com.recipemanagement.exception.RecipeNotFoundException;
import com.recipemanagement.model.Recipe;

import java.util.List;

public interface RecipeService {
    void addRecipe(Recipe recipe);

    void updateRecipe(Recipe recipe);

    void deleteRecipe(Long recipeId) throws RecipeNotFoundException;

    Recipe getRecipeById(Long recipeId) throws RecipeNotFoundException;

    List<Recipe> getAllRecipes();

    List<Recipe> getRecipesByIngredient(String ingredient);

    List<Recipe> getPopularRecipes();
}