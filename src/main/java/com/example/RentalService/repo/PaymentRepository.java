package com.example.RentalService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Payment;

/**
 * Repository interface for Payment entity.
 * Provides CRUD operations and custom query methods for managing payment transactions.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    /**
     * Retrieves a payment by its Razorpay order ID.
     *
     * @param razorpayOrderId the Razorpay order ID associated with the payment
     * @return the Payment entity if found, otherwise null
     */
    Payment findByRazorpayOrderId(String razorpayOrderId);
}
