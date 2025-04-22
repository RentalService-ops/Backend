package com.example.RentalService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
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
    @Column(nullable = false, length = 255, unique=true)
    private String name;

    // Property to store the description of the category
    @Column(columnDefinition = "TEXT")
    private String description;


    /**
     * Default constructor for Category
     */
    public Category() {
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

}
