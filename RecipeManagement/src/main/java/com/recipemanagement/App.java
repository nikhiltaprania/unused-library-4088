package com.recipemanagement;

import com.recipemanagement.dao.LikeDAOImpl;
import com.recipemanagement.dao.RecipeDAOImpl;
import com.recipemanagement.dao.UserDAOImpl;
import com.recipemanagement.model.User;
import com.recipemanagement.model.UserType;
import com.recipemanagement.service.*;
import com.recipemanagement.ui.AdminUI;
import com.recipemanagement.ui.UserUI;
import com.recipemanagement.util.ConnectionManager;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class App {
    private static final EntityManager em = ConnectionManager.getEntityManager();
    private static final UserService userService = new UserServiceImpl(new UserDAOImpl(em));
    private static final RecipeService recipeService = new RecipeServiceImpl(new RecipeDAOImpl(em));
    private static final LikeService likeService = new LikeServiceImpl(new LikeDAOImpl(em));

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Recipe Management System!");
            System.out.println("Login As:");
            System.out.println("1. Admin");
            System.out.println("2. Register As Admin");
            System.out.println("2. Customer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int userTypeChoice = sc.nextInt();
            sc.nextLine();

            switch (userTypeChoice) {
                case 1 -> {
                    AdminUI adminUI = new AdminUI(userService, recipeService, likeService);
                    adminUI.start();
                }

                case 2 -> registerAdmin(sc);

                case 3 -> {
                    UserUI userUI = new UserUI(userService, recipeService, likeService);
                    userUI.start();
                }

                case 0 -> {
                    System.out.println("\nThank you for using the Recipe Management System!");
                    System.out.println("Existing App...");
                    em.close();
                    return;
                }

                default -> System.out.println("Invalid choice. Exiting...");
            }
        }
    }

    public static void registerAdmin(Scanner sc) {
        User admin = new User();

        System.out.print("Enter Admin Username: ");
        admin.setUsername(sc.nextLine());

        System.out.print("Enter Admin Password: ");
        admin.setPassword(sc.nextLine());

        admin.setUserType(UserType.ADMIN);

        try {
            userService.addUser(admin);
            System.out.println("Admin registration successful.");
        } catch (Exception e) {
            System.out.println("Admin already registered.");
        }
    }


}