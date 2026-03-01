package com.moviebooking.service;

import com.moviebooking.model.*;
import com.moviebooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Booking createBooking(Long userId, Long showId, List<Long> seatIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found!"));

        if (show.getStatus() != Show.ShowStatus.ACTIVE) {
            throw new RuntimeException("Show is not active!");
        }

        List<Seat> seats = seatRepository.findAllById(seatIds);

        // Check for already booked seats
        List<Seat> availableSeats = seatRepository.findAvailableSeatsByShow(showId);
        List<Long> availableSeatIds = availableSeats.stream()
                .map(Seat::getId)
                .toList();

        for (Seat seat : seats) {
            if (!availableSeatIds.contains(seat.getId())) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already booked!");
            }
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeats(seats);
        booking.setTotalAmount(show.getPrice() * seats.size());
        booking.setBookingTime(java.time.LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));
    }

    @Transactional
    public Booking cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking already cancelled!");
        }
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}