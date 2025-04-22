package com.example.RentalService.model;

import java.math.BigDecimal;

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
public class Equipment {
	
    // Property to store the unique ID of the equipment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentId;

    // Property to store the user associated with this equipment
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Property to store the category to which the equipment belongs
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "category_id")
    private Category category;

    // Property to store the name of the equipment
    @Column(nullable = false, length = 255, unique=true)
    private String name;

    // Property to store the description of the equipment
    @Column(columnDefinition = "TEXT")
    private String description;

    // Property to store the quantity of equipment available
    @Column(nullable = false)
    private int quantity;

    // Property to store the price per day of renting the equipment
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    // Property to store the URL for an image of the equipment
    @Column(length = 255)
    private String imageUrl;
    
    @Column()
    private boolean isActive;

    // Constructors

    public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
     * Default constructor for Equipment, required by JPA.
     */
    public Equipment() {
    }

    /**
     * Constructor to initialize Equipment with specific user, category, name, description, quantity, price per day, and image URL.
     * 
     * @param user the user associated with the equipment.
     * @param category the category to which the equipment belongs.
     * @param name the name of the equipment.
     * @param description the description of the equipment.
     * @param quantity the quantity of equipment available.
     * @param pricePerDay the price per day of renting the equipment.
     * @param imageUrl the URL for an image of the equipment.
     */
    public Equipment(Users user, Category category, String name, String description, int quantity, BigDecimal pricePerDay, String imageUrl) {
        this.user = user;
        this.category = category;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.pricePerDay = pricePerDay;
        this.imageUrl = imageUrl;
    }

}
