package com.example.spring.data.rest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    // Temporary in-memory storage for OTPs
    private final Map<String, String> otpStore = new HashMap<>();

    // ---------------------- SEND OTP ----------------------
    public String sendOtp(String email) {

        String otp = generateOtp();
        otpStore.put(email, otp);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your Email Verification OTP");
            message.setText("Your OTP for verification is: " + otp);

            mailSender.send(message);

            return "OTP sent to " + email;
        }
        catch (Exception e) {
            return "Failed to send OTP: " + e.getMessage();
        }
    }

    // ---------------------- VERIFY OTP ----------------------
    public boolean verifyOtp(String email, String otp) {

        if (!otpStore.containsKey(email)) {
            return false;
        }

        boolean isMatch = otpStore.get(email).equals(otp);

        if (isMatch) {
            otpStore.remove(email); // remove OTP after successful verification
        }

        return isMatch;
    }

    // ---------------------- GENERATE OTP ----------------------
    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999)); // 6-digit OTP
    }
}
