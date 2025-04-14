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

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@Builder
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

    /**
     * Gets the unique identifier of the address.
     * 
     * @return the id of the address.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the address.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the street of the address.
     * 
     * @return the street of the address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street of the address.
     *
     * @param street the street to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the city of the address.
     * 
     * @return the city of the address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the address.
     *
     * @param city the city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the state of the address.
     * 
     * @return the state of the address.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the address.
     *
     * @param state the state to set.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the postal code (zip code) of the address.
     * 
     * @return the postal code of the address.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the postal code (zip code) of the address.
     *
     * @param zipCode the zip code to set.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the country of the address.
     * 
     * @return the country of the address.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the address.
     *
     * @param country the country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the user associated with this address.
     * 
     * @return the user associated with the address.
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user associated with this address.
     *
     * @param user the user to set.
     */
    public void setUser(Users user) {
        this.user = user;
    }
    
    
}
