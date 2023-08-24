package com.recipemanagement.service;

import com.recipemanagement.dao.LikeDAOImpl;
import com.recipemanagement.dao.RecipeDAO;
import com.recipemanagement.exception.RecipeNotFoundException;
import com.recipemanagement.model.Recipe;
import com.recipemanagement.util.ConnectionManager;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeServiceImpl implements RecipeService {
    private final RecipeDAO recipeDAO;
    LikeService likeService = new LikeServiceImpl(new LikeDAOImpl(ConnectionManager.getEntityManager()));

    public RecipeServiceImpl(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeDAO.addRecipe(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipeDAO.updateRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeId) throws RecipeNotFoundException {
        recipeDAO.deleteRecipe(recipeId);
    }

    @Override
    public Recipe getRecipeById(Long recipeId) throws RecipeNotFoundException {
        return recipeDAO.getRecipeById(recipeId);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeDAO.getAllRecipes();
    }

    @Override
    public List<Recipe> getRecipesByIngredient(String ingredient) {
        return recipeDAO.getAllRecipes().stream()
                .filter(recipe -> recipe.getIngredients().toLowerCase().contains(ingredient.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getPopularRecipes() {
        List<Recipe> allRecipes = getAllRecipes();

        List<Recipe> popularRecipes = allRecipes.stream()
                .sorted((recipe1, recipe2) ->
                        Integer.compare(likeService.getLikeCountForRecipe(recipe2.getId()), likeService.getLikeCountForRecipe(recipe1.getId())))
                .collect(Collectors.toList());

        return popularRecipes;
    }
}