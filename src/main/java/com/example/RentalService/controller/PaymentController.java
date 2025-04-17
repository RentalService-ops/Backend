package com.example.RentalService.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.CreatePaymentRequestDTO;
import com.example.RentalService.DTO.VerifyPaymentRequestDTO;
import com.example.RentalService.service.PaymentService;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasAnyRole('user', 'rental')")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody CreatePaymentRequestDTO request) {
        try {
            String order = paymentService.createOrder(request);
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody VerifyPaymentRequestDTO request) {
        String result = paymentService.verifyPayment(request);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/failed-payment/{paymentId}")
    public ResponseEntity<String> handleFailedPayment(@PathVariable("paymentId") String paymentId){
    	return ResponseEntity.ok(paymentService.handleFailedPayment(paymentId));
    }
    
    @PostMapping("/reject-payment/{paymentId}")
    public ResponseEntity<String> handleRejectPayment(@PathVariable("paymentId") String paymentId){
    	return ResponseEntity.ok(paymentService.handleRejectedPayment(paymentId));
    }
    
    @GetMapping("/getPayment/{id}")
    public ResponseEntity<?> getPayment(@PathVariable int id){
    	return paymentService.getPaymentByUserId(id);
    }
}
