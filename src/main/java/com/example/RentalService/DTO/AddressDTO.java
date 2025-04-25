package com.example.RentalService.DTO;

import com.example.RentalService.model.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private int id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public AddressDTO() {}

    public AddressDTO(int id, String street, String city, String state, String zipCode, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
    
    public AddressDTO(Address address) {
    	this.id= address.getId();
    	this.street = address.getStreet();
    	this.state = address.getState();
    	this.city = address.getCity();
    	this.country = address.getCountry();
    	this.zipCode= address.getZipCode();
    }

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + ", country=" + country + "]";
	}
    
    
}