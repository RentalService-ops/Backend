package com.example.RentalService.service;

import org.springframework.http.ResponseEntity;

public interface NotificationService {
    
    /**
     * Retrieves all notifications for a specific user based on their user ID.
     *
     * @param id the ID of the user whose notifications are to be fetched
     * @return a ResponseEntity containing the list of notifications or an appropriate error message
     */
    ResponseEntity<?> getnotificationByUserId(Integer id);
    
}
