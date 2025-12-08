package com.example.spring.data.rest.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {

    public int getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(int pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public int getWalletUsed() {
        return walletUsed;
    }

    public void setWalletUsed(int walletUsed) {
        this.walletUsed = walletUsed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int pointsUsed = 0;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int walletUsed = 0;


    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public int getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        AmountPaid = amountPaid;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }
    @Column(columnDefinition = "integer default 0")
    private int AmountPaid ;
    private Long turfId;
    private String PaymentMethod;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTurfId() {
        return turfId;
    }

    public void setTurfId(Long turfId) {
        this.turfId = turfId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    private Long userId;
    private String date;      // yyyy-MM-dd
    private String timeSlot;  // example: "6-7 AM"
    private String Sport;

    public String getSport() {
        return Sport;
    }

    public void setSport(String sport) {
        Sport = sport;
    }
}
