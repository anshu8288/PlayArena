package com.anshu.PlayArena.payment;

import org.springframework.stereotype.Service;

import com.anshu.PlayArena.booking.Booking;
import com.anshu.PlayArena.booking.BookingRepo;
import com.anshu.PlayArena.booking.BookingStatus;
import com.anshu.PlayArena.slot.Slot;
import com.anshu.PlayArena.slot.SlotStatus;

@Service
public class PaymentService {

    private final BookingRepo bookingRepo;
    private final PaymentRepo paymentRepo;

    public PaymentService(BookingRepo bookingRepo, PaymentRepo paymentRepo) {
        this.bookingRepo = bookingRepo;
        this.paymentRepo = paymentRepo;
    }

    public Payment makePayment(Integer bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setStatus(PaymentStatus.SUCCESS);

        // mark booking confirmed
        booking.setStatus(BookingStatus.SUCCESS);

        // mark slot unavailable
        Slot slot = booking.getSlot();
        slot.setStatus(SlotStatus.BOOKED);

        return paymentRepo.save(payment);
    }
}