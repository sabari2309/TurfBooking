package com.example.spring.data.rest.Controller;

import com.example.spring.data.rest.model.Turf;
import com.example.spring.data.rest.repo.TurfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/turfs")
@CrossOrigin(origins = "*")
public class TurfController {

    @Autowired
    TurfRepository repo;

    @GetMapping
    public List<Turf> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Turf getOne(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @GetMapping("/type/{sport}")
    public List<Turf> getTurfsBySport(@PathVariable String sport) {
        return repo.findBySportType(sport);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTurf(@RequestBody Turf turf){
        repo.save(turf);
        return ResponseEntity.ok(Map.of("message","Turf Added"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTurf(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.status(404).body("Turf not found");
        }

        repo.deleteById(id);

        return ResponseEntity.ok(Map.of("message", "Turf deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTurf(@PathVariable Long id, @RequestBody Turf updatedTurf){
        if (!repo.existsById(id)) {
            return ResponseEntity.status(404).body("Turf not found");
        }
        Optional<Turf> existing=repo.findById(id);
        if(!existing.isPresent()) {
            return ResponseEntity.status(404).body("Turf not found");
        }
            existing.get().setName(updatedTurf.getName());
            existing.get().setLocation(updatedTurf.getLocation());
            existing.get().setSportType(updatedTurf.getSportType());
            existing.get().setPrice(updatedTurf.getPrice());
            repo.save(existing.get());
            return ResponseEntity.ok(Map.of("Message", "Updated Successfully"));
    }
}

