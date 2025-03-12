package com.example.RentalService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rental_Bookings {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int booking_id;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="user_id",nullable=false)
	private Users user;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="rental_id",nullable=false)
	private Users renter;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	private Equipment equipment_id;
	
	private int equipment_quantity;


	public int getEquipment_quantity() {
		return equipment_quantity;
	}

	public void setEquipment_quantity(int equipment_quantity) {
		this.equipment_quantity = equipment_quantity;
	}
	
	public int getId() {
		return this.booking_id;
	}
	
	public int getUserId() {
		return this.user.getId();
	}
	
	public void setUser(Users user) {
		this.user=user;
	}
	
	public int getRentalId() {
		return this.renter.getId();
	}
	
	public void setRenter(Users renter) {
		this.renter=renter;
	}
}
