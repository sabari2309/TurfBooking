package com.example.spring.data.rest.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();

    public String sendOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpStore.put(email, otp);

        Email from = new Email("yourappemail@gmail.com");
        Email to = new Email(email);
        String subject = "Your OTP Verification Code";
        Content content = new Content("text/plain", "Your OTP is: " + otp);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
            return "OTP sent successfully!";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to send email: " + ex.getMessage());
        }
    }

    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStore.get(email));
    }
}
