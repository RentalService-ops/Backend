package com.example.RentalService.DTO;

import com.example.RentalService.model.Category;


public class CategoryDTO {
    
    /** The ID of the category. */
    private int categoryId;
    
    /** The name of the user associated with the category. */
    private String user;
    
    /** The name of the category. */
    private String name;
    
    /** The description of the category. */
    private String description;
    
    /**
     * Constructor that initializes the CategoryDTO using a Category model.
     * It extracts relevant information from the provided Category object.
     * 
     * @param category The Category model object to initialize the DTO.
     */
    public CategoryDTO(Category category) {
        this.setDescription(category.getDescription());
        this.setCategoryId(category.getCategoryId());
        this.setName(category.getName());
        this.setUser(category.getUser().getUsername());
    }
    
    /** 
     * Default constructor.
     * Initializes an empty CategoryDTO.
     */
    public CategoryDTO() {
        
    }

    /**
     * Gets the username associated with the category.
     * 
     * @return The username as a String.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the username associated with the category.
     * 
     * @param userName The username to set.
     */
    public void setUser(String userName) {
        this.user = userName;
    }

    /**
     * Gets the name of the category.
     * 
     * @return The category name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     * 
     * @param categoryName The category name to set.
     */
    public void setName(String categoryName) {
        this.name = categoryName;
    }

    /**
     * Gets the description of the category.
     * 
     * @return The category description as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category.
     * 
     * @param categoryDescription The category description to set.
     */
    public void setDescription(String categoryDescription) {
        this.description = categoryDescription;
    }

    /**
     * Gets the ID of the category.
     * 
     * @return The category ID as an integer.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the ID of the category.
     * 
     * @param categoryId The category ID to set.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
