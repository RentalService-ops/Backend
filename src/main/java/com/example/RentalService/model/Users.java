package com.example.RentalService.model;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Users {

    // Property to store the unique user ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    private BigInteger OTP;
    

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
}
