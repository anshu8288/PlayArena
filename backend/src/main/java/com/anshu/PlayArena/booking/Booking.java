package com.anshu.PlayArena.booking;

import com.anshu.PlayArena.payment.Payment;
import com.anshu.PlayArena.slot.Slot;
import com.anshu.PlayArena.user.User;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "bookings")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    /*
     * 
     * By default:
     * ManyToOne → EAGER (bad sometimes)
     * 
     * @ManyToOne(fetch = FetchType.LAZY)
     * private Slot slot;
     * 👉 Prevents unnecessary data loading
     * 
     * Consider bidirectional mapping (later stage)
     * In Slot:
     * @OneToMany(mappedBy = "slot")
     * private List<Booking> bookings;
     * 👉 Useful for:
     * Fetch all bookings of a slot
     * Analytics
     * 
     */
    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*
     * cascade
     * 
     * @OneToOne(cascade = CascadeType.ALL)
     * @JoinColumn(name = "payment_id")
     * private Payment payment;
     * 👉 Now: Save booking → payment auto saved
     */
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Booking() {
    }

    public Booking(BookingStatus status, Slot slot, User user, Payment payment) {
        this.status = status;
        this.slot = slot;
        this.user = user;
        this.payment = payment;
    }

}
