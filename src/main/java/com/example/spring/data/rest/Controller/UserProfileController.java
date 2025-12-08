package com.example.spring.data.rest.Controller;
import com.example.spring.data.rest.model.Turf;
import com.example.spring.data.rest.model.*;
import com.example.spring.data.rest.repo.BookingRepository;
import com.example.spring.data.rest.repo.CustomerRepo;
import com.example.spring.data.rest.repo.TurfRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/profile", produces = "application/json; charset=UTF-8")
public class UserProfileController {

    private final CustomerRepo customerRepo;
    private final BookingRepository bookingRepo;
    private final TurfRepository turfRepo;

    public UserProfileController(CustomerRepo c, BookingRepository br, TurfRepository t) {
        this.customerRepo = c;
        this.bookingRepo = br;
        this.turfRepo = t;
    }

    @GetMapping("/{userId}")
    public Map<String, Object> getProfile(@PathVariable Long userId) {

        User user = customerRepo.findById(userId).orElseThrow();
        List<Map<String, Object>> bookingList = new ArrayList<>();

        List<Booking> bookings = bookingRepo.findByUserId(userId);

        for (Booking b : bookings) {
            Turf t = turfRepo.findById(b.getTurfId()).orElseThrow();

            bookingList.add(Map.of(
                    "id", b.getId(),
                    "turfName", t.getName(),
                    "sport", t.getSportType(),
                    "date", b.getDate(),
                    "timeSlot", b.getTimeSlot(),
                    "amountPaid", b.getAmountPaid()
            ));
        }

        return Map.of("user", user, "bookings", bookingList);
    }


    @PostMapping("/wallet/add")
    public void addMoney(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        int amount = Integer.parseInt(request.get("amount").toString());

        User user = customerRepo.findById(userId).orElseThrow();
        user.setWalletBalance(user.getWalletBalance() + amount);
        customerRepo.save(user);
    }
}

