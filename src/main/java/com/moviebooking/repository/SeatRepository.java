package com.moviebooking.repository;

import com.moviebooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenId(Long screenId);
    @Query("SELECT s FROM Seat s WHERE s.screen.id = " +
            "(SELECT sh.screen.id FROM Show sh WHERE sh.id = :showId) " +
            "AND s.id NOT IN " +
            "(SELECT seat.id FROM Booking b JOIN b.seats seat WHERE b.show.id = :showId AND b.status = 'CONFIRMED')")
    List<Seat> findAvailableSeatsByShow(@Param("showId") Long showId);
}