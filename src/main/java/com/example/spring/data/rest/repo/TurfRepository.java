package com.example.spring.data.rest.repo;

import com.example.spring.data.rest.model.Turf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurfRepository extends JpaRepository<Turf, Long> {
    List<Turf> findBySportType(String sportType);
    List<Turf> findByLocationIgnoreCase(String location);

    List<Turf> findByLocationIgnoreCaseAndSportTypeIgnoreCase(String location, String sportType);
}

