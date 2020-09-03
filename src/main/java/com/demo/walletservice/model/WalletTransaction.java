package com.demo.walletservice.model;

import javax.validation.constraints.NotNull;

public class WalletTransaction {

    @NotNull(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotNull(message = "amount cannot be null or empty")
    private Long amount;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
