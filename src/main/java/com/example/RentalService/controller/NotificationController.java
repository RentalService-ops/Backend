package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	//Notification service object for interacting with the database.
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * Retrieves notifications for a specific user based on the user ID.
	 * @param id the ID of the user whose notifications are to be retrieved
	 * @return a ResponseEntity containing the user's notifications or an appropriate error message
	 */
    @PreAuthorize("hasAnyRole('ROLE_user')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotificationByUserId(@PathVariable("id") int id){
		return notificationService.getnotificationByUserId(id);
	}
}
