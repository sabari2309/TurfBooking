package com.example.spring.data.rest.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    private final Map<String, String> otpStore = new HashMap<>();

    // ---------------------- SEND OTP ----------------------
    public String sendOtp(String email) {

        String otp = generateOtp();
        otpStore.put(email, otp);

        try {
            String url = "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", brevoApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = """
            {
              "sender": { "name": "Turf App", "email": "sabareeswarangopal@gmail.com" },
              "to": [{ "email": "%s" }],
              "subject": "Your Email Verification OTP",
              "htmlContent": "<h2>Your OTP is: %s</h2>"
            }
            """.formatted(email, otp);

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            RestTemplate rest = new RestTemplate();
            rest.postForEntity(url, request, String.class);

            return "OTP sent to " + email;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Failed to send OTP: " + e.getMessage();
        }
    }

    // ---------------------- VERIFY OTP ----------------------
    public boolean verifyOtp(String email, String otp) {

        if (!otpStore.containsKey(email)) {
            return false;
        }

        boolean isMatch = otp.equals(otpStore.get(email));

        if (isMatch) {
            otpStore.remove(email); // OTP used → delete it
        }

        return isMatch;
    }

    // ---------------------- GENERATE OTP ----------------------
    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}
