package com.example.RentalService.service;

import org.springframework.http.ResponseEntity;

public interface NotificationService {
	
    ResponseEntity<?> getnotificationByUserId(Integer id);

}
