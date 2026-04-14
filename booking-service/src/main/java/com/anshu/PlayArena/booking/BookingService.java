package com.anshu.PlayArena.booking;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anshu.PlayArena.slot.Slot;
import com.anshu.PlayArena.slot.SlotRepo;
import com.anshu.PlayArena.slot.SlotStatus;

@Service
public class BookingService {
    private final BookingRepo bookingRepo;
    private final SlotRepo slotRepo;

    private static final String USER_SERVICE_URL = "http://localhost:8081";

    public BookingService(BookingRepo bookingRepo, SlotRepo slotRepo) {
        this.bookingRepo = bookingRepo;
        this.slotRepo = slotRepo;
    }

    public Booking createBooking(Integer slotId, Integer userId) {

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(USER_SERVICE_URL + "/users/" + userId, Object.class);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("User not found");
        }

        Slot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot already booked");
        }

        // HOLD the slot (important)
        slot.setStatus(SlotStatus.LOCKED);
        slotRepo.save(slot);

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setSlot(slot);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepo.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Booking getBookingById(Integer id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // payments service is setting the status of booking to SUCCESS (Logic)
    public Booking confirmPayment(Integer bookingId, Integer paymentId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setPaymentId(paymentId);
        booking.setStatus(BookingStatus.SUCCESS);

        Slot slot = booking.getSlot();
        slot.setStatus(SlotStatus.BOOKED);
        slotRepo.save(slot);

        return bookingRepo.save(booking);
    }
}
