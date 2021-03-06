package com.demo.walletservice.controller;

import com.demo.walletservice.common.ErrorMessage;
import com.demo.walletservice.common.ResponseWrapper;
import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.model.WalletRequest;
import com.demo.walletservice.model.WalletTransaction;
import com.demo.walletservice.service.WalletService;
import com.demo.walletservice.validator.WalletValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class WalletController {

    private static final String STATUS = "status";

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletValidator walletValidator;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseWrapper> getAll() {
        final List<Wallet> wallets = walletService.fetchAll();
        return ResponseEntity.ok(new ResponseWrapper(wallets, Collections.singletonMap(STATUS, HttpStatus.OK)));
    }

    @GetMapping("/findByPhoneNumber/{phoneNumber}")
    public Wallet findByPhoneNumber(@PathVariable String phoneNumber) {
        return walletService.getWalletByPhoneNumber(phoneNumber);
    }

    @PostMapping("/addWallet")
    public ResponseEntity<ResponseWrapper> addWallet(@RequestBody WalletRequest wallet) {
        final List<ErrorMessage> errorMessages = walletValidator.addWalletValidation(wallet);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity(new ResponseWrapper(Collections.singletonMap(STATUS, HttpStatus.NOT_ACCEPTABLE), errorMessages), HttpStatus.NOT_ACCEPTABLE);
        }

        final Wallet newWallet = walletService.addWallet(wallet);
        return ResponseEntity.ok(new ResponseWrapper(newWallet, Collections.singletonMap(STATUS, HttpStatus.OK)));
    }

    @PostMapping("/updateWallet/{walletId}")
    public ResponseEntity<ResponseWrapper> updateWallet(@PathVariable Long walletId, @RequestBody WalletRequest wallet) {
        final List<ErrorMessage> errorMessages = walletValidator.updateWalletValidation(walletId, wallet);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity(new ResponseWrapper(Collections.singletonMap(STATUS, HttpStatus.NOT_ACCEPTABLE), errorMessages), HttpStatus.NOT_ACCEPTABLE);
        }

        final Wallet updatedWallet = walletService.updateWallet(walletId, wallet);
        return ResponseEntity.ok(new ResponseWrapper(updatedWallet, Collections.singletonMap(STATUS, HttpStatus.OK)));
    }

    @PostMapping("/topUpBalance")
    public ResponseEntity<ResponseWrapper> topUpBalance(@RequestBody WalletTransaction transaction) {
        final List<ErrorMessage> errorMessages = walletValidator.topUpBalanceValidation(transaction);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity(new ResponseWrapper(Collections.singletonMap(STATUS, HttpStatus.NOT_ACCEPTABLE), errorMessages), HttpStatus.NOT_ACCEPTABLE);
        }

        Wallet newWallet = walletService.topUpBalance(transaction);

        Wallet walletByPhoneNumber = walletService.getWalletByPhoneNumber(transaction.getPhoneNumber());
        walletByPhoneNumber.setBalance(transaction.getAmount());

        restTemplate.postForObject("http://transaction-service/doTopUp", walletByPhoneNumber, Wallet.class);
        return ResponseEntity.ok(new ResponseWrapper(newWallet, Collections.singletonMap(STATUS, HttpStatus.OK)));
    }

    @PostMapping("/deductBalance")
    public ResponseEntity<ResponseWrapper> deductBalance(@RequestBody WalletTransaction walletTransaction) {
        final List<ErrorMessage> errorMessages = walletValidator.deductBalanceValidation(walletTransaction);
        if (!errorMessages.isEmpty()) {
            return new ResponseEntity(new ResponseWrapper(Collections.singletonMap(STATUS, HttpStatus.NOT_ACCEPTABLE), errorMessages), HttpStatus.NOT_ACCEPTABLE);
        }

        Wallet newWallet = walletService.deductBalance(walletTransaction);
        return ResponseEntity.ok(new ResponseWrapper(newWallet, Collections.singletonMap(STATUS, HttpStatus.OK)));
    }
}
