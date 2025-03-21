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

@Entity
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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Property to store the name of the equipment
    @Column(nullable = false, length = 255)
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

    // Constructors

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

    // Getters and Setters

    /**
     * Gets the unique ID of the equipment.
     * 
     * @return the equipment ID.
     */
    public int getEquipmentId() {
        return equipmentId;
    }

    /**
     * Sets the unique ID for the equipment.
     * 
     * @param equipmentId the equipment ID to be set.
     */
    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * Gets the user associated with this equipment.
     * 
     * @return the user associated with the equipment.
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user associated with this equipment.
     * 
     * @param user the user to be set for the equipment.
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Gets the category to which the equipment belongs.
     * 
     * @return the category of the equipment.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category to which the equipment belongs.
     * 
     * @param category the category to be set for the equipment.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the name of the equipment.
     * 
     * @return the name of the equipment.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the equipment.
     * 
     * @param name the name to be set for the equipment.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the equipment.
     * 
     * @return the description of the equipment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the equipment.
     * 
     * @param description the description to be set for the equipment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the quantity of equipment available.
     * 
     * @return the quantity of the equipment.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of equipment available.
     * 
     * @param quantity the quantity to be set for the equipment.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price per day for renting the equipment.
     * 
     * @return the price per day of the equipment.
     */
    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    /**
     * Sets the price per day for renting the equipment.
     * 
     * @param pricePerDay the price to be set for renting the equipment.
     */
    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    /**
     * Gets the URL for an image of the equipment.
     * 
     * @return the image URL of the equipment.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL for an image of the equipment.
     * 
     * @param imageUrl the image URL to be set for the equipment.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
