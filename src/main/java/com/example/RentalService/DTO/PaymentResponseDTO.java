package com.example.RentalService.DTO;

import java.time.LocalDate;

import com.example.RentalService.model.Payment;

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
    
    
public PaymentResponseDTO(Payment payment) {
		this.amount=payment.getAmount();
		this.paymentDate=payment.getPaymentDate();
		this.razorpayPaymentId=payment.getRazorpayPaymentId();
		this.status=payment.getStatus();
		this.EqupmentName=payment.getOrder().getEquipment().getName();
	}
}
