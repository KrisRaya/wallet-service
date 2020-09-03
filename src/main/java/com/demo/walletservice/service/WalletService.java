package com.demo.walletservice.service;

import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.model.WalletRequest;
import com.demo.walletservice.model.WalletTransaction;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    List<Wallet> fetchAll();

    Optional<Wallet> findById(Long id);

    Wallet addWallet(WalletRequest wallet);

    Wallet updateWallet(Long walletId, WalletRequest wallet);

    Wallet topUpBalance(WalletTransaction walletTransaction);

    Wallet deductBalance(WalletTransaction walletTransaction);

    Wallet getWalletByPhoneNumber(String phoneNumber);
}
