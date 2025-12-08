package com.example.spring.data.rest.model;

import lombok.Data;

@Data
public class BookingRequest {
    private Long turfId;
    private Long userId;
    private String date;

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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    private int amountPaid; // Final payable amount

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(int pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    private int pointsUsed;
    private String timeSlot;
    private String paymentMethod;

    public int getWalletUsed() {
        return walletUsed;
    }

    public void setWalletUsed(int walletUsed) {
        this.walletUsed = walletUsed;
    }

    private int walletUsed;
}
