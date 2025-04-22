package com.example.RentalService.DTO;

import java.math.BigInteger;

import com.example.RentalService.model.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
