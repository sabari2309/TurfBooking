package com.example.spring.data.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletRequest {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private double amount;
}
