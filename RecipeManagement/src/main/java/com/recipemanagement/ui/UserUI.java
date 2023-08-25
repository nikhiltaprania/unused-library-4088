package com.recipemanagement.ui;

import com.recipemanagement.exception.RecipeNotFoundException;
import com.recipemanagement.exception.UserNotFoundException;
import com.recipemanagement.model.Like;
import com.recipemanagement.model.Recipe;
import com.recipemanagement.model.User;
import com.recipemanagement.model.UserType;
import com.recipemanagement.service.LikeService;
import com.recipemanagement.service.RecipeService;
import com.recipemanagement.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserUI {
    private final UserService userService;
    private final RecipeService recipeService;
    private final LikeService likeService;
    private Long loggedInUserId;

    public UserUI(UserService userService, RecipeService recipeService, LikeService likeService) {
        this.recipeService = recipeService;
        this.likeService = likeService;
        this.userService = userService;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome to Recipe Management System!");

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Register");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register(sc);

                case 2 -> {
                    loggedIn = login(sc);
                    if (loggedIn) {
                        userUi();
                    }
                }

                case 3 -> {
                    loggedIn = true;
                    System.out.println("Goodbye!");
                }

                default -> System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    public void userUi() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Recipes");
            System.out.println("2. Filter Recipes");
            System.out.println("3. Like Recipe");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewRecipes();
                case 2 -> filterRecipes(sc);
                case 3 -> likeRecipe(sc);
                case 4 -> exit = true;
                default -> System.out.println("Invalid choice. Please select again.");
            }
        }

        System.out.println("Logging out. Goodbye, Customer!");
    }

    private boolean login(Scanner sc) {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        boolean authenticated = userService.authenticateUser(username, password);

        if (authenticated) {
            System.out.println("Login successful!");
            loggedInUserId = userService.getUserIdByUsername(username);
            System.out.println("Welcome! " + username);

            return true;

        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    private void register(Scanner sc) {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setUserType(UserType.CUSTOMER);

        userService.addUser(newUser);
        System.out.println("Registration successful. Please log in.");
    }

    private void viewRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();

        System.out.println("Recipes available:");
        for (Recipe recipe : recipes) {
            System.out.println("- " + recipe.getName());
        }
    }

    private void filterRecipes(Scanner sc) {
        System.out.print("Enter ingredient to filter recipes: ");
        String ingredient = sc.nextLine();

        List<Recipe> filteredRecipes = recipeService.getRecipesByIngredient(ingredient);

        if (filteredRecipes.isEmpty()) {
            System.out.println("No recipes found with the given ingredient.");

        } else {
            System.out.println("Filtered Recipes:");
            for (Recipe recipe : filteredRecipes) {
                System.out.println("- " + recipe.getName());
            }
        }
    }

    private void likeRecipe(Scanner sc) {
        System.out.print("Enter Recipe ID to like: ");
        Long recipeId = sc.nextLong();
        sc.nextLine();

        try {
            Recipe recipe = recipeService.getRecipeById(recipeId);
            Like newLike = new Like(userService.getUserById(loggedInUserId), recipe, LocalDate.now());

            likeService.addLike(newLike);
            System.out.println("Recipe liked successfully!");

        } catch (RecipeNotFoundException | UserNotFoundException e) {
            System.out.println("Recipe not found.");
        }
    }
}