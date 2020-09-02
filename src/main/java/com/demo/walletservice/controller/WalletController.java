package com.demo.walletservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
