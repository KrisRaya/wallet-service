package com.demo.walletservice.service;

import com.demo.walletservice.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    List<Wallet> fetchAll();

    Optional<Wallet> findById(Long id);

    Wallet addWallet(Wallet wallet);

    Wallet updateWallet(Wallet wallet);

    Wallet topUpBalance(Wallet wallet);

    Wallet deductBalance(Wallet wallet);

    Wallet getWalletByPhoneNumber(String phoneNumber);
}
