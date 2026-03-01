package com.moviebooking.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // e.g. A1, A2, B1, B2

    @Column(name = "seat_row")
    private String row;

    private int number;        // e.g. 1, 2, 3

    @Enumerated(EnumType.STRING)
    private SeatType seatType; // REGULAR, PREMIUM, VIP

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    public enum SeatType {
        REGULAR, PREMIUM, VIP
    }
}