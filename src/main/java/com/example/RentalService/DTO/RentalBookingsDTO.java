package com.example.RentalService.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Rental_Bookings;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RentalBookingsDTO {
	private int bookingId;
	
	private String userName;
	
	private String equipmentName;	
	
	private int equipmentQuantity;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private BigDecimal totalAmount;
	
	private BookingStatus status;
	
	private boolean isReturned;
	
	/**
	 * Default constructor
	 * */
	public RentalBookingsDTO(){
		
	}
	
	/**
	 * Initializes booking DTO object based on booking entity object
	 * @param booking the booking entity object containing booking details.
	 * */
	public RentalBookingsDTO(Rental_Bookings booking){
		this.bookingId=booking.getBookingId();
		this.userName=booking.getUser().getUsername();
		this.equipmentName=booking.getEquipment().getName();
		this.equipmentQuantity=booking.getEquipmentQuantity();
		this.startDate=booking.getStartDate();
		this.endDate=booking.getEndDate();
		this.status=booking.getStatus();
		this.setTotalAmount(booking.getTotalPrice());
		this.setReturned(booking.isReturned());
	}

	@Override
	public String toString() {
		return "RentalBookingsDTO [bookingId=" + bookingId + ", userName=" + userName + ", equipmentName="
				+ equipmentName + ", equipmentQuantity=" + equipmentQuantity + ", startDate=" + startDate + ", endDate="
				+ endDate + ", totalAmount=" + totalAmount + ", status=" + status + ", isReturned=" + isReturned + "]";
	}
	
	

}
