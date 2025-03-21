package com.example.RentalService.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rental_Bookings {

    // Property to store the unique booking ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int booking_id;

    // Property to store the user who made the booking
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    // Property to store the renter associated with the booking
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="rental_id", nullable=false)
    private Users renter;

    // Property to store the equipment being booked
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="equipment_id",nullable=false)
    private Equipment equipment;

    // Property to store the quantity of equipment being booked
    @Column(nullable=false)
    private int equipment_quantity;
    
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;


    // Getters and Setters

    /**
     * Gets the quantity of equipment being booked.
     * 
     * @return the quantity of the equipment in the booking.
     */
    public int getEquipment_quantity() {
        return equipment_quantity;
    }

    /**
     * Sets the quantity of equipment being booked.
     * 
     * @param equipment_quantity the quantity to be set for the equipment in the booking.
     */
    public void setEquipment_quantity(int equipment_quantity) {
        this.equipment_quantity = equipment_quantity;
    }

    /**
     * Gets the unique booking ID for this rental booking.
     * 
     * @return the booking ID.
     */
    public int getId() {
        return this.booking_id;
    }

    /**
     * Gets the user ID of the person who made the booking.
     * 
     * @return the user ID of the user who made the booking.
     */
    public Users getUser() {
        return this.user;
    }

    /**
     * Sets the user associated with this booking.
     * 
     * @param user the user to be set for the booking.
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Gets the renter ID associated with this booking.
     * 
     * @return the renter ID.
     */
    public Users getRenter() {
        return this.renter;
    }

    /**
     * Sets the renter associated with this booking.
     * 
     * @param renter the renter to be set for the booking.
     */
    public void setRenter(Users renter) {
        this.renter = renter;
    }
    
    /**
     * Sets the equipment associated with this booking.
     * @param equipment: the equipment to be set with this booking.
     * */
    public void setEquipment(Equipment equipment) {
    	this.equipment=equipment;
    }
    
    /**
     * @return the corresponding ID of the equipment associated with corresponding Rental booking.
     * */
    public Equipment getEquipment() {
    	return this.equipment;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

}
