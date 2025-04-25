package com.example.RentalService.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.Notification;
import com.example.RentalService.repo.NotificationRepository;
import com.example.RentalService.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public ResponseEntity<?> getnotificationByUserId(Integer id) {
		List<Notification> notifications = notificationRepository.findByUserId(id);
		
		if(notifications == null) {
			return  ResponseEntity.ok().body("No Notification Found");
		}
		return ResponseEntity.ok().body(notifications);

	}

	@Override
	public ResponseEntity<?> getRecentNotifications() {
		List<Notification> notifications=notificationRepository.findAll();
		if(notifications==null) {
			return ResponseEntity.ok("No notification found");
		}
	    notifications=notifications.reversed();
		return ResponseEntity.ok().body(notifications.subList(0, 4));
	}

}
