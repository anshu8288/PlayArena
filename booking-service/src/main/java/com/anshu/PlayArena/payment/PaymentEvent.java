package com.anshu.PlayArena.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private Integer bookingId;
    private Integer paymentId;
    private String status;
}