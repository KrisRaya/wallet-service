package com.demo.walletservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WalletRequest {

    @NotNull(message = "Wallet name must be set")
    private String name;
    @NotNull(message = "Phone number must be set")
    @Size(min = 7, max = 13)
    private String phoneNumber;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
