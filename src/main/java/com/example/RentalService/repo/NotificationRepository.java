package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Notification;

/**
 * Repository interface for Notification entity.
 * Provides CRUD operations and custom query methods related to user notifications.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retrieves a list of notifications for a specific user by their user ID.
     *
     * @param id the ID of the user
     * @return list of notifications associated with the given user
     */
    List<Notification> findByUserId(Integer id);
}
