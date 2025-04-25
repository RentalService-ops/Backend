package com.example.RentalService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.RentalService.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    /**
     * Retrieves a payment by its Razorpay order ID.
     *
     * @param razorpayOrderId the Razorpay order ID associated with the payment
     * @return the Payment entity if found, otherwise null
     */
    Payment findByRazorpayOrderId(String razorpayOrderId);
    
    /**
     * Deletes all payments based on provided userId.
     * @param userId the id of the user whose payment details are to be deleted.
     * */
    @Modifying  //Annotation to add when using update , delete queries.
    void deletePaymentByUserId(int userId);
}
