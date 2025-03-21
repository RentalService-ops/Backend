package com.example.RentalService.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Rental_Bookings;

public class RentalBookingsDTO {
	private int bookingId;
	
	private String userName;
	
	private String equipmentName;	
	
	private int equipmentQuantity;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private BigDecimal totalAmount;
	
	private BookingStatus status;
	
	public RentalBookingsDTO(){
		
	}
	
	public RentalBookingsDTO(Rental_Bookings booking){
		this.bookingId=booking.getId();
		this.userName=booking.getUser().getUsername();
		this.equipmentName=booking.getEquipment().getName();
		this.equipmentQuantity=booking.getEquipment_quantity();
		this.startDate=booking.getStartDate();
		this.endDate=booking.getEndDate();
		this.status=booking.getStatus();
		Integer equipmentQuantity=booking.getEquipment_quantity();
		this.setTotalAmount(booking.getEquipment().getPricePerDay().multiply(
				BigDecimal.valueOf(equipmentQuantity.longValue())));
	}
	
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public void setEquipmentName(String name) {
		this.equipmentName=name;
	}
	
	public String getEquipmentName() {
		return this.equipmentName;
	}

	public int getEquipmentQuantity() {
		return equipmentQuantity;
	}

	public void setEquipmentQuantity(int equipmentQuantity) {
		this.equipmentQuantity = equipmentQuantity;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

}
