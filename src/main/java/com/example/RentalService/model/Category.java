package com.example.RentalService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Category {

    // Property to store the category ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    // Property to store the associated user
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false) // Fixed foreign key mapping
    private Users user;

    // Property to store the name of the category
    @Column(nullable = false, length = 255)
    private String name;

    // Property to store the description of the category
    @Column(columnDefinition = "TEXT")
    private String description;

    // Constructors

    /**
     * Default constructor for Category, required by JPA and Jackson.
     */
    public Category() {
        // Default constructor for Jackson
    }

    /**
     * Constructor to initialize Category with specific user, name, and description.
     * 
     * @param user the user associated with this category.
     * @param name the name of the category.
     * @param description the description of the category.
     */
    public Category(Users user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters

    /**
     * Gets the category ID.
     * 
     * @return the category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category ID.
     * 
     * @param categoryId the category ID to be set.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the user associated with this category.
     * 
     * @return the user associated with this category.
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user associated with this category.
     * 
     * @param user the user to be associated with this category.
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Gets the name of the category.
     * 
     * @return the name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     * 
     * @param name the name of the category to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the category.
     * 
     * @return the description of the category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category.
     * 
     * @param description the description of the category to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the Category object.
     * 
     * @return a string representation of the Category object with categoryId, user, name, and description.
     */
    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", user=" + user + ", name=" + name + ", description=" 
                + description + "]";
    }
}
