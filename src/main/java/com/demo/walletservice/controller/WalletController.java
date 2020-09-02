package com.demo.walletservice.controller;

import com.demo.walletservice.common.ErrorMessage;
import com.demo.walletservice.common.ResponseWrapper;
import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.service.WalletService;
import com.demo.walletservice.validator.WalletValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletValidator walletValidator;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseWrapper> getAll() {
        final List<Wallet> wallets = walletService.fetchAll();
        return ResponseEntity.ok(new ResponseWrapper(wallets, Collections.singletonMap("status", HttpStatus.OK)));
    }

    @GetMapping("/findByPhoneNumber/{phoneNumber}")
    public ResponseEntity<ResponseWrapper> findByPhoneNumber(@PathVariable String phoneNumber) {
        final Wallet wallets = walletService.getWalletByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(new ResponseWrapper(wallets, Collections.singletonMap("status", HttpStatus.OK)));
    }

    @PostMapping("/addWallet")
    public ResponseEntity<ResponseWrapper> addWallet(@RequestBody Wallet wallet) {
        final List<ErrorMessage> errorMessages = walletValidator.addWalletValidation(wallet);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity(new ResponseWrapper(Collections.singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorMessages), HttpStatus.NOT_ACCEPTABLE);
        }

        final Wallet newWallet = walletService.addWallet(wallet);
        return ResponseEntity.ok(new ResponseWrapper(newWallet, Collections.singletonMap("status", HttpStatus.OK)));
    }

    @PostMapping("/topUpBalance")
    public ResponseEntity<ResponseWrapper> topUpBalance(@RequestBody Wallet wallet) {
        final List<ErrorMessage> errorMessages = walletValidator.topUpBalanceValidation(wallet);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity(new ResponseWrapper(Collections.singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorMessages), HttpStatus.NOT_ACCEPTABLE);
        }

        Wallet newWallet = walletService.topUpBalance(wallet);

        Wallet walletByPhoneNumber = walletService.getWalletByPhoneNumber(wallet.getPhoneNumber());
        walletByPhoneNumber.setBalance(wallet.getBalance());

        restTemplate.postForObject("http://localhost:8080/transaction/doTopUp", walletByPhoneNumber, Wallet.class);
        return ResponseEntity.ok(new ResponseWrapper(newWallet, Collections.singletonMap("status", HttpStatus.OK)));
    }

    @GetMapping("/bulkcreate")
    public String bulkCreate() {
        Wallet wallet = new Wallet();
        wallet.setName("abc");
        wallet.setId(1L);
        wallet.setEmail("abc");
        wallet.setPhoneNumber("abc");
        wallet.setBalance(2222L);
        walletService.addWallet(wallet);
        return "Wallet created";
    }
}
