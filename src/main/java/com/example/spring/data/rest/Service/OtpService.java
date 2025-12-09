package com.example.spring.data.rest.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStore = new HashMap<>();

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        otpStore.put(email, otp);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Your OTP Verification Code");
        msg.setText("Your OTP is: " + otp);

        mailSender.send(msg);

        return "OTP sent successfully!";
    }

    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStore.get(email));
    }
}

