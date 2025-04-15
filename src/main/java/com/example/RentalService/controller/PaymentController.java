package com.example.RentalService.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
