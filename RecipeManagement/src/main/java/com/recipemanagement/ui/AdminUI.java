package com.recipemanagement.ui;

import com.recipemanagement.exception.RecipeNotFoundException;
import com.recipemanagement.exception.UserNotFoundException;
import com.recipemanagement.model.Recipe;
import com.recipemanagement.model.User;
import com.recipemanagement.model.UserType;
import com.recipemanagement.service.LikeService;
import com.recipemanagement.service.RecipeService;
import com.recipemanagement.service.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private final UserService userService;
    private final RecipeService recipeService;
    private final LikeService likeService;
    private User loggedInAdmin;

    public AdminUI(UserService userService, RecipeService recipeService, LikeService likeService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.likeService = likeService;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome to Recipe Management System!");

        boolean loggedIn = login(sc);
        if (!loggedIn) {
            System.out.println("Login failed. Exiting...");
            return;
        }

        System.out.println("\nWelcome, Admin: " + loggedInAdmin.getUsername());

        boolean exit = false;
        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Add Recipe");
            System.out.println("4. Update Recipe");
            System.out.println("5. Delete Recipe");
            System.out.println("6. View Likes for Recipe");
            System.out.println("7. Generate Recipe Reports");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addUser(sc);
                case 2 -> deleteUser(sc);
                case 3 -> addRecipe(sc);
                case 4 -> updateRecipe(sc);
                case 5 -> deleteRecipe(sc);
                case 6 -> viewLikesForRecipe(sc);
                case 7 -> generateReports();
                case 8 -> exit = true;
                default -> System.out.println("Invalid choice. Please select again.");
            }
        }

        System.out.println("Logging out. Goodbye, Admin: " + loggedInAdmin.getUsername());
    }

    private boolean login(Scanner sc) {
        System.out.print("Enter Admin Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();

        try {
            loggedInAdmin = userService.getUserByUsername(username);
            return loggedInAdmin.getPassword().equals(password) && loggedInAdmin.getUserType() == UserType.ADMIN;

        } catch (UserNotFoundException e) {
            return false;
        }
    }

    private void addUser(Scanner sc) {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Select UserType\nAdmin or Customer: ");
        String type = sc.next();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        while (true) {
            if (type.equalsIgnoreCase("admin")) {
                newUser.setUserType(UserType.ADMIN);
                break;
            } else if (type.equalsIgnoreCase("customer")) {
                newUser.setUserType(UserType.CUSTOMER);
                break;
            } else {
                System.out.println("Invalid");
            }
        }

        userService.addUser(newUser);
        System.out.println("User added successfully!");
    }

    private void deleteUser(Scanner sc) {
        System.out.print("Enter User ID to delete: ");
        Long userId = sc.nextLong();
        sc.nextLine();

        try {
            userService.deleteUser(userId);
            System.out.println("User deleted successfully!");

        } catch (UserNotFoundException e) {
            System.out.println("User not found.");
        }
    }

    private void addRecipe(Scanner sc) {
        System.out.print("Enter Recipe Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Ingredients: ");
        String ingredients = sc.nextLine();
        System.out.print("Enter Preparation Steps: ");
        String preparationSteps = sc.nextLine();

        Recipe newRecipe = new Recipe();
        newRecipe.setName(name);
        newRecipe.setIngredients(ingredients);
        newRecipe.setPreparationSteps(preparationSteps);

        recipeService.addRecipe(newRecipe);
        System.out.println("Recipe added successfully!");
    }

    private void updateRecipe(Scanner sc) {
        System.out.print("Enter Recipe ID to update: ");
        Long recipeId = sc.nextLong();
        sc.nextLine();

        try {
            Recipe existingRecipe = recipeService.getRecipeById(recipeId);
            System.out.print("Enter New Recipe Name: ");
            String newName = sc.nextLine();
            System.out.print("Enter New Ingredients: ");
            String newIngredients = sc.nextLine();
            System.out.print("Enter New Preparation Steps: ");
            String newPreparationSteps = sc.nextLine();

            existingRecipe.setName(newName);
            existingRecipe.setIngredients(newIngredients);
            existingRecipe.setPreparationSteps(newPreparationSteps);
            recipeService.updateRecipe(existingRecipe);

            System.out.println("Recipe updated successfully!");
        } catch (RecipeNotFoundException e) {
            System.out.println("Recipe not found.");
        }
    }

    private void deleteRecipe(Scanner sc) {
        System.out.print("Enter Recipe ID to delete: ");
        Long recipeId = sc.nextLong();
        sc.nextLine();

        try {
            recipeService.deleteRecipe(recipeId);
            System.out.println("Recipe deleted successfully!");

        } catch (RecipeNotFoundException e) {
            System.out.println("Recipe not found.");
        }
    }

    private void viewLikesForRecipe(Scanner sc) {
        System.out.print("Enter Recipe ID: ");
        Long recipeId = sc.nextLong();
        sc.nextLine();

        try {
            Recipe recipe = recipeService.getRecipeById(recipeId);
            int likeCount = likeService.getLikeCountForRecipe(recipeId);
            System.out.println("Recipe: " + recipe.getName());
            System.out.println("Likes: " + likeCount);

        } catch (RecipeNotFoundException e) {
            System.out.println("Recipe not found.");
        }
    }

    private void generateReports() {
        System.out.println("Generating Recipe Reports...");
        List<Recipe> popularRecipes = recipeService.getPopularRecipes();
        System.out.println("Popular Recipes:");

        for (Recipe recipe : popularRecipes) {
            System.out.println("- " + recipe.getName());
        }
    }
}