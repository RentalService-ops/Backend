package com.example.RentalService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String street; // Street address
    private String city;   // City of the address
    private String state;  // State or province of the address
    private String zipCode; // Postal code of the address
    private String country; // Country of the address

    @ManyToOne
    @JoinColumn(name = "Users_id", nullable = false)
    private Users user; // The user associated with this address

    /**
     * Default constructor.
     */
    public Address() {}

    /**
     * Parameterized constructor to initialize an Address object with given details.
     *
     * @param street The street address.
     * @param city The city of the address.
     * @param state The state or province of the address.
     * @param zipCode The postal code of the address.
     * @param country The country of the address.
     * @param user The user associated with the address.
     */
    public Address(String street, String city, String state, String zipCode, String country, Users user) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.user = user;
    }


    
}
