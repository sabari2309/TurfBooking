package com.example.spring.data.rest.repo;

import com.example.spring.data.rest.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Get booked slots for a specific turf on a date
    List<Booking> findByTurfIdAndDate(Long turfId, String date);

    List<Booking> findByUserId(Long userId);

    boolean existsByTurfIdAndDateAndTimeSlot(Long turfId, String date, String timeSlot);
}
