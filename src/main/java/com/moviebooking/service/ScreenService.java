package com.moviebooking.service;

import com.moviebooking.model.Screen;
import com.moviebooking.model.Theatre;
import com.moviebooking.repository.ScreenRepository;
import com.moviebooking.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    public Screen addScreen(Long theatreId, Screen screen) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found!"));
        screen.setTheatre(theatre);
        return screenRepository.save(screen);
    }

    public List<Screen> getScreensByTheatre(Long theatreId) {
        return screenRepository.findByTheatreId(theatreId);
    }

    public Screen getScreenById(Long id) {
        return screenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screen not found!"));
    }

    public void deleteScreen(Long id) {
        screenRepository.deleteById(id);
    }
}