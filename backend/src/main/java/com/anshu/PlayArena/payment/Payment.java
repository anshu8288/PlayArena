package com.anshu.PlayArena.payment;

import com.anshu.PlayArena.booking.Booking;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Booking booking;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // SUCCESS, FAILED, PENDING

    private String transactionId;
}