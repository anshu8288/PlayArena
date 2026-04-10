package com.anshu.PlayArena.payment;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @PostMapping("/{bookingId}")
    public Payment makePayment(@PathVariable Integer bookingId) {
        return makePayment(bookingId);
    }
}
