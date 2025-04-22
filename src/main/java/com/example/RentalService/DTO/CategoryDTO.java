package com.example.RentalService.DTO;

import com.example.RentalService.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
