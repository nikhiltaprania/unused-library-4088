package com.recipemanagement.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;
    @Column(name = "recipe_name", nullable = false)
    private String name;
    @Column(name = "ingredients", nullable = false, columnDefinition = "TEXT")
    private String ingredients;
    @Column(name = "preparation_steps", nullable = false, columnDefinition = "TEXT")
    private String preparationSteps;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    public Recipe() {
    }

    public Recipe(String name, String ingredients, String preparationSteps, User user, Set<Like> likes) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
        this.user = user;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(String preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }
}