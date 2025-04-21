package com.example.RentalService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBookingsDTO {
	
	private String name;
	
	private int totalBookings;
	
	private int completedBookings;
	
	private int rejectedBookings;
	
	private int cancelledBookings;
	
	private int  approvedBookings;
	
	private int pendingBookings;

}
