package com.anshu.payment_service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;

    // Booking service URL
    private static final String BOOKING_SERVICE_URL = "http://localhost:8082";

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public Payment createPaymentOrder(Integer bookingId) {
        RestTemplate restTemplate = new RestTemplate();

        // Verify booking exists by calling booking-service
        try {
            String bookingUrl = BOOKING_SERVICE_URL + "/booking/" + bookingId;
            restTemplate.getForObject(bookingUrl, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Booking not found: " + e.getMessage());
        }

        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(0.0); // Amount should be fetched from booking service or set by caller
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepo.save(payment);

        // payments service is setting the status of booking to SUCCESS here
        try {
            String confirmUrl = BOOKING_SERVICE_URL + "/booking/" + bookingId + "/confirm-payment/"
                    + savedPayment.getId();
            restTemplate.postForObject(confirmUrl, null, Object.class);
            savedPayment.setStatus(PaymentStatus.SUCCESS);
            return paymentRepo.save(savedPayment);
        } catch (Exception e) {
            savedPayment.setStatus(PaymentStatus.FAILURE);
            paymentRepo.save(savedPayment);
            throw new RuntimeException("Payment created but booking confirmation failed: " + e.getMessage());
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }
}