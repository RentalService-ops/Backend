package com.example.RentalService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByRazorpayOrderId(String razorpayOrderId);

}
