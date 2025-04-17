package com.example.RentalService.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private double amount;
    private LocalDate paymentDate;
    private String razorpayPaymentId;
    private String status;
    private String EqupmentName;
}
