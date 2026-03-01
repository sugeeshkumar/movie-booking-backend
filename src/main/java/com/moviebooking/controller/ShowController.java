package com.moviebooking.controller;

import com.moviebooking.model.Show;
import com.moviebooking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping("/movie/{movieId}/screen/{screenId}")
    public ResponseEntity<Show> addShow(
            @PathVariable Long movieId,
            @PathVariable Long screenId,
            @RequestBody Show show) {
        return ResponseEntity.ok(showService.addShow(movieId, screenId, show));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Show>> getShowsByScreen(@PathVariable Long screenId) {
        return ResponseEntity.ok(showService.getShowsByScreen(screenId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Show> cancelShow(@PathVariable Long id) {
        return ResponseEntity.ok(showService.cancelShow(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.ok("Show deleted successfully!");
    }
}