package com.anshu.payment_service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepo paymentRepo;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentService(PaymentRepo paymentRepo, KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.paymentRepo = paymentRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Payment createPaymentOrder(Integer bookingId) {
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(200.0); // Amount should be fetched from booking service or set by caller
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepo.save(payment);

        savedPayment.setStatus(PaymentStatus.SUCCESS);
        paymentRepo.save(savedPayment);

        publishPaymentEvent(savedPayment.getId(), bookingId, "SUCCESS");
        return savedPayment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    private void publishPaymentEvent(Integer paymentId, Integer bookingId, String status) {
        PaymentEvent event = new PaymentEvent();
        event.setBookingId(bookingId);
        event.setPaymentId(paymentId);
        event.setStatus(status);

        try {
            kafkaTemplate.send("payment-topic", event).whenComplete((result, ex) -> {
                if (ex != null) {
                    log.warn("Kafka publish failed for paymentId={}, bookingId={}, status={}", paymentId, bookingId,
                            status, ex);
                } else {
                    log.info("Kafka publish succeeded for paymentId={}, bookingId={}", paymentId, bookingId);
                }
            });
        } catch (Exception ex) {
            log.warn("Kafka publish failed immediately for paymentId={}, bookingId={}, status={}", paymentId,
                    bookingId, status, ex);
        }
    }
}