package com.example.RentalService.DTO;

import java.math.BigInteger;

import com.example.RentalService.model.Users;

public class UsersDTO {
    
    // Property to store the username of the user
    private String username;
    
    // Property to store the phone number of the user
    private BigInteger phoneNo;
    
    // Property to store the email address of the user
    private String email;
    

    /**
     * Constructor to initialize UsersDTO with data from a Users model object.
     * 
     * @param user the Users object from which the data is copied.
     */
    public UsersDTO(Users user) {
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setPhoneNo(user.getPhoneNumber());
    }
    
    /**
     * Default constructor for UsersDTO.
     */
    public UsersDTO() {
        
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
     * Sets the username of the user.
     * 
     * @param username the username to be set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return the phone number of the user.
     */
    public BigInteger getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the phone number of the user.
     * 
     * @param phoneNo the phone number to be set for the user.
     */
    public void setPhoneNo(BigInteger phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email the email address to be set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
