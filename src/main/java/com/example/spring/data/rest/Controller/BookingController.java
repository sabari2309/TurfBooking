package com.example.spring.data.rest.Controller;

import com.example.spring.data.rest.model.Booking;
import com.example.spring.data.rest.model.BookingRequest;
import com.example.spring.data.rest.model.User;
import com.example.spring.data.rest.repo.BookingRepository;
import com.example.spring.data.rest.repo.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingRepository bookingRepo;
    private final CustomerRepo customerRepo;

    public BookingController(BookingRepository bookingRepo, CustomerRepo customerRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
    }

    // ---------- SECURE BOOKING LOGIC ----------
    @Transactional
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {

        // 1️⃣ Prevent race condition (slot already booked?)
        boolean exists = bookingRepo.existsByTurfIdAndDateAndTimeSlot(
                request.getTurfId(),
                request.getDate(),
                request.getTimeSlot()
        );

        if (exists) {
            return ResponseEntity.status(409).body("❌ Slot already booked by another user.");
        }

        // 2️⃣ Fetch User
        User user = customerRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        int basePrice = request.getWalletUsed();

        // ---------- POINT VALIDATION ----------
        if (request.getPointsUsed() > user.getPoints()) {
            return ResponseEntity.badRequest().body("❌ Not enough points.");
        }

        // calculate discount
        int discount = request.getPointsUsed() * 20;
        int payableAfterPoints = Math.max(basePrice - discount, 0);

        // deduct points
        user.setPoints(user.getPoints() - request.getPointsUsed());

        // ---------- WALLET VALIDATION ----------
        if (request.getWalletUsed() > user.getWalletBalance()) {
            return ResponseEntity.badRequest().body("❌ Wallet balance too low.");
        }

        // deduct wallet
        int walletApplied = Math.min(request.getWalletUsed(), payableAfterPoints);
        payableAfterPoints -= walletApplied;
        user.setWalletBalance(user.getWalletBalance() - (walletApplied+discount));

        // 🔥 Should NEVER have remaining payable since no cash/UPI now
        if (payableAfterPoints > 0) {
            return ResponseEntity.badRequest().body("❌ Insufficient wallet balance. Please add money.");
        }

        // Reward
        user.setPoints(user.getPoints() + 1);
        customerRepo.save(user);

        // ---------- SAVE BOOKING ----------
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setTurfId(request.getTurfId());
        booking.setDate(request.getDate());
        booking.setTimeSlot(request.getTimeSlot());
        booking.setPointsUsed(request.getPointsUsed());
        booking.setWalletUsed(request.getWalletUsed());
        booking.setAmountPaid(request.getWalletUsed());

        bookingRepo.save(booking);

        return ResponseEntity.ok("🎉 Booking Confirmed Successfully! Paid via Wallet: ₹" + walletApplied);
    }


    // ---------- FETCH BOOKINGS ----------
    @GetMapping("/{turfId}/{date}")
    public List<Booking> getBookedSlots(@PathVariable Long turfId, @PathVariable String date) {
        return bookingRepo.findByTurfIdAndDate(turfId, date);
    }


    // ---------- RECEIPT ----------
    @GetMapping("/receipt/{id}")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable Long id) {

        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = customerRepo.findById(booking.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String receipt = """
                -------- TURF BOOKING RECEIPT --------

                Customer Name: %s
                Turf ID: %d
                Date: %s
                Time Slot: %s

                Points Used: %d
                Wallet Used: ₹%d
                Total Paid: ₹%d

                Thank you for booking with us!
                --------------------------------------
                """.formatted(
                user.getName(),
                booking.getTurfId(),
                booking.getDate(),
                booking.getTimeSlot(),
                booking.getPointsUsed(),
                booking.getWalletUsed(),
                booking.getAmountPaid()
        );

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=BookingReceipt_" + id + ".txt")
                .body(receipt.getBytes());
    }
}
