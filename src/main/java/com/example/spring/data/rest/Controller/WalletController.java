package com.example.spring.data.rest.Controller;
import com.example.spring.data.rest.model.Turf;
import com.example.spring.data.rest.model.*;
import com.example.spring.data.rest.repo.BookingRepository;
import com.example.spring.data.rest.repo.CustomerRepo;
import com.example.spring.data.rest.repo.TurfRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/wallet")
@CrossOrigin("*")
public class WalletController {

    private final CustomerRepo customerRepo;

    public WalletController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMoneyToWallet(@RequestBody WalletRequest request) {

        User user = customerRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        double newBalance = user.getWalletBalance() + request.getAmount();
        user.setWalletBalance((int) newBalance);

        customerRepo.save(user);

        return ResponseEntity.ok("Wallet updated successfully");
    }
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody WalletPaymentRequest request) {

        // Find user
        User user = customerRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // Update wallet
        user.setWalletBalance(user.getWalletBalance() + Integer.parseInt(request.getAmount()));

        // Save updated user
        customerRepo.save(user);

        return "Wallet Updated Successfully";
    }
}

