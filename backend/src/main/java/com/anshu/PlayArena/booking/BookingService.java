package com.anshu.PlayArena.booking;

import org.springframework.stereotype.Service;

import com.anshu.PlayArena.slot.Slot;
import com.anshu.PlayArena.slot.SlotRepo;
import com.anshu.PlayArena.slot.SlotStatus;
import com.anshu.PlayArena.user.UserRepo;

@Service
public class BookingService {
    private final BookingRepo bookingRepo;
    private final SlotRepo slotRepo;
    private final UserRepo userRepo;

    public BookingService(BookingRepo bookingRepo, SlotRepo slotRepo, UserRepo userRepo) {
        this.bookingRepo = bookingRepo;
        this.slotRepo = slotRepo;
        this.userRepo = userRepo;
    }

    public Booking createBooking(Integer slotId, Integer userId) {
        Slot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot already booked");
        }

        // HOLD the slot (important)
        slot.setStatus(SlotStatus.LOCKED);
        slotRepo.save(slot);

        Booking booking = new Booking();
        booking.setUser(userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        booking.setSlot(slot);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepo.save(booking);
    }
}
