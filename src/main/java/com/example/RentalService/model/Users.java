package com.example.RentalService.model;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {

    // Property to store the unique user ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Property to store the username of the user
    private String username;

    // Property to store the password of the user
    private String password;

    // Property to store the email of the user
    private String email;

    // Property to store the role of the user (e.g., admin, customer)
    private String role;

    // Property to store the phone number of the user
    private BigInteger phoneNumber;


    // Constructors

    /**
     * Constructor to initialize the user with specific username, password, email, role, and phone number.
     * 
     * @param username the username of the user.
     * @param password the password of the user.
     * @param email the email of the user.
     * @param role the role of the user (e.g., admin, customer).
     * @param phoneNumber the phone number of the user.
     */
    public Users(String username, String password, String email, String role, BigInteger phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Default constructor for the Users class.
     */
    public Users() {

    }

    // Getters and Setters

    /**
     * Gets the unique ID of the user.
     * 
     * @return the user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for the user.
     * 
     * @param id the user ID to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     * 
     * @param username the username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     * 
     * @param password the password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for the user.
     * 
     * @param email the email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the role of the user (e.g., admin, customer).
     * 
     * @return the role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role for the user (e.g., admin, customer).
     * 
     * @param role the role to be set for the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return the phone number of the user.
     */
    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number for the user.
     * 
     * @param phoneNumber the phone number to be set for the user.
     */
    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
