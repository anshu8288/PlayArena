package com.anshu.PlayArena.payment;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anshu.PlayArena.booking.Booking;
import com.anshu.PlayArena.booking.BookingRepo;
import com.anshu.PlayArena.booking.BookingStatus;
import com.anshu.PlayArena.slot.Slot;
import com.anshu.PlayArena.slot.SlotRepo;
import com.anshu.PlayArena.slot.SlotStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentEventListener {

    private final BookingRepo bookingRepo;
    private final SlotRepo slotRepo;

    public PaymentEventListener(BookingRepo bookingRepo, SlotRepo slotRepo) {
        this.bookingRepo = bookingRepo;
        this.slotRepo = slotRepo;
    }

    @KafkaListener(topics = "payment-topic")
    @Transactional
    public void handlePaymentEvent(PaymentEvent event) {
        log.info("Received payment event: {}", event);

        Booking booking = bookingRepo.findById(event.getBookingId()).orElse(null);
        if (booking == null) {
            // Ignore stale or out-of-order events rather than retrying indefinitely.
            log.warn("Ignoring payment event because booking was not found: {}", event.getBookingId());
            return;
        }

        if ("SUCCESS".equals(event.getStatus())) {
            log.info("Payment successful for booking: {}", event.getBookingId());
            booking.setStatus(BookingStatus.SUCCESS);

            Slot slot = booking.getSlot();
            slot.setStatus(SlotStatus.BOOKED);
            slotRepo.save(slot);
        } else if ("FAILURE".equals(event.getStatus())) {
            log.info("Payment failed for booking: {}", event.getBookingId());
            booking.setStatus(BookingStatus.CANCELLED);

            Slot slot = booking.getSlot();
            slot.setStatus(SlotStatus.AVAILABLE);
            slotRepo.save(slot);
        } else {
            log.warn("Ignoring payment event with unsupported status: {}", event.getStatus());
            return;
        }

        bookingRepo.save(booking);
        log.info("Booking updated successfully for payment: {}", event.getPaymentId());
    }
}