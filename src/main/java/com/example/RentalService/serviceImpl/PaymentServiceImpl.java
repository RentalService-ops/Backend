package com.example.RentalService.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.CreatePaymentRequestDTO;
import com.example.RentalService.DTO.PaymentResponseDTO;
import com.example.RentalService.DTO.VerifyPaymentRequestDTO;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.model.Payment;
import com.example.RentalService.model.Rental_Bookings;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.EquipmentRepo;
import com.example.RentalService.repo.PaymentRepository;
import com.example.RentalService.repo.RentalBookingRepository;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Value("${razorpay.key_id}")
	private String keyId;

	@Value("${razorpay.key_secret}")
	private String keySecret;

	private final PaymentRepository paymentRepository;
	private final UserRepository usersRepository;
	private final RentalBookingRepository rentalBookingRepository;
	private final EquipmentRepo equipmentRepository;
	
	/**
	 * @return new RazorpayClient connection for processing payment requests based on keyId and keySecret provided.
	 */
	private RazorpayClient getRazorpayClient() throws RazorpayException {
		return new RazorpayClient(keyId, keySecret);
	}

	@Override
	public String createOrder(CreatePaymentRequestDTO request) throws RazorpayException {
		// Initialize Razorpay client
		RazorpayClient client = getRazorpayClient();

		// Create order request
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", request.getAmount() * 100); // Convert amount to paisa
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
		orderRequest.put("payment_capture", 1);
		// Create Razorpay order
		Order razorOrder = client.orders.create(orderRequest);

		// Fetch User and Booking information from DB
		Users user = usersRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		Rental_Bookings booking = rentalBookingRepository.findById(request.getBookingId())
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		booking.setStatus(BookingStatus.COMPLETED);

		rentalBookingRepository.save(booking);
		// Create Payment entity without payment ID and signature initially
		Payment payment = Payment.builder().razorpayOrderId(razorOrder.get("id"))
				.amount(request.getAmount().doubleValue())
				.currency("INR").status("CREATED") 
				.paymentDate(java.time.LocalDate.now())
				.user(user).order(booking) 
				.build();

		
		paymentRepository.save(payment);

		// Return the Razorpay order details
		return razorOrder.toString();
	}

	@Override
	public String verifyPayment(VerifyPaymentRequestDTO request) {
		// Fetch payment using Razorpay Order ID
		Payment payment = paymentRepository.findByRazorpayOrderId(request.getOrderId());
		if (payment == null) {
			return "Invalid Razorpay Order ID"; 
		}

		
		String data = request.getOrderId() + "|" + request.getPaymentId();
		String generatedSignature = null;

		try {
			generatedSignature = Utils.getHash(data, keySecret); 
		} catch (RazorpayException e) {
			return "Error generating signature"; 
		}

		// Compare the generated signature with the received signature
		if (generatedSignature.equals(request.getSignature())) {
			// Payment is verified, update payment status to 'PAID'
			payment.setRazorpayPaymentId(request.getPaymentId());
			payment.setRazorpaySignature(request.getSignature());
			payment.setStatus("PAID");

			// Save the updated payment record
			paymentRepository.save(payment);
			return "Payment Verified and Status Updated to PAID"; // Successful verification
		} else {
			return "Payment Verification Failed"; // Failed verification
		}
	}

	@Override
	public String handleFailedPayment(String paymentOrderId) {
		Payment payment = paymentRepository.findByRazorpayOrderId(paymentOrderId);
		Rental_Bookings booking = payment.getOrder();
		paymentRepository.deleteById(payment.getId());
		booking.setStatus(BookingStatus.APPROVED);
		rentalBookingRepository.save(booking);

		return "Payment attempt failed.If your payment attempt fails more than two times then the your booking will be rejected automatically.";
	}

	@Override
	public String handleRejectedPayment(String paymentOrderId) {
		Payment payment = paymentRepository.findByRazorpayOrderId(paymentOrderId);
		Rental_Bookings booking = payment.getOrder();
		booking.setStatus(BookingStatus.REJECTED);
		Equipment equipment = booking.getEquipment();
		equipment.setQuantity(equipment.getQuantity() + booking.getEquipmentQuantity());
		equipmentRepository.save(equipment);
		rentalBookingRepository.save(booking);
		paymentRepository.deleteById(payment.getId());
		return "Your payment attempt has failed more than twice so the current booking is rejected. Please re-book your order.";
	}

	@Override
	public ResponseEntity<?> getPaymentByUserId(int id) {
		List<PaymentResponseDTO> payments = paymentRepository.findAll().stream()
				.filter(pay -> pay.getUser().getId() == id)
				.map(pay -> new PaymentResponseDTO(pay.getAmount(), pay.getPaymentDate(), pay.getRazorpayPaymentId(),
						pay.getStatus(), pay.getOrder().getEquipment().getName()))
				.collect(Collectors.toList());

		if (payments.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No payments found for user ID: " + id);
		}

		return ResponseEntity.ok(payments);
	}

	@Override
	public ResponseEntity<?> getAllPaymentOfRental(int id) {

		List<PaymentResponseDTO> rentalPayemnt = paymentRepository.findAll().stream()
				.filter(payment -> payment.getOrder().getRenter().getId() == id)
				.sorted((p1, p2) -> p2.getPaymentDate().compareTo(p1.getPaymentDate())) 
				.map(payment -> new PaymentResponseDTO(payment)).toList();

		return ResponseEntity.ok(rentalPayemnt);
	}

	@Override
	@Transactional
	public void deletePaymentByUserId(int userId) {
		paymentRepository.deletePaymentByUserId(userId);
	}

}
