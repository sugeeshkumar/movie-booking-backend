package com.moviebooking.service;

import com.moviebooking.model.Screen;
import com.moviebooking.model.Seat;
import com.moviebooking.repository.ScreenRepository;
import com.moviebooking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public Seat addSeat(Long screenId, Seat seat) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new RuntimeException("Screen not found!"));
        seat.setScreen(screen);
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByScreen(Long screenId) {
        return seatRepository.findByScreenId(screenId);
    }

    public List<Seat> getAvailableSeats(Long showId) {
        return seatRepository.findAvailableSeatsByShow(showId);
    }

    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }
}