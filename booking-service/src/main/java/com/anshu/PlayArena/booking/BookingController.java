package com.anshu.PlayArena.booking;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    Booking getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    // payments service is setting the status of booking to SUCCESS (API Endpoint)
    @PostMapping("/{bookingId}/confirm-payment/{paymentId}")
    Booking confirmPayment(@PathVariable Integer bookingId, @PathVariable Integer paymentId) {
        return bookingService.confirmPayment(bookingId, paymentId);
    }

    @PostMapping
    Booking createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request.slotId, request.userId);
    }
}
