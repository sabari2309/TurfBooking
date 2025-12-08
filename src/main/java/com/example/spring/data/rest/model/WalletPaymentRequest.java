package com.example.spring.data.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletPaymentRequest {
    private Long userId;
    private String amount;
    private String transactionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
