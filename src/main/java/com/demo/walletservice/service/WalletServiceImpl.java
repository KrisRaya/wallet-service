package com.demo.walletservice.service;

import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public List<Wallet> fetchAll() {
        return walletRepository.findAll();
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public Wallet addWallet(Wallet wallet) {
        final String phoneNumber = formatPhoneNumber(wallet.getPhoneNumber());
        wallet.setPhoneNumber(phoneNumber);
        wallet.setBalance(0L);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet updateWallet(Wallet wallet) {
        wallet.setName(wallet.getName());
        wallet.setEmail(wallet.getEmail());
        wallet.setPhoneNumber(wallet.getPhoneNumber());
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet topUpBalance(Wallet wallet) {
        final Wallet walletByPhoneNumber = getWalletByPhoneNumber(wallet.getPhoneNumber());
        walletByPhoneNumber.setBalance(walletByPhoneNumber.getBalance() + wallet.getBalance());
        return walletRepository.save(walletByPhoneNumber);
    }

    @Override
    public Wallet deductBalance(Wallet wallet) {
        final Wallet walletByPhoneNumber = getWalletByPhoneNumber(wallet.getPhoneNumber());
        walletByPhoneNumber.setBalance(walletByPhoneNumber.getBalance() - wallet.getBalance());
        return walletRepository.save(walletByPhoneNumber);
    }

    @Override
    public Wallet getWalletByPhoneNumber(String phoneNumber) {
        final String formatPhoneNumber = formatPhoneNumber(phoneNumber);
        final Optional<Wallet> topUpWallet = walletRepository.findByPhoneNumber(formatPhoneNumber);
        return topUpWallet.orElse(null);
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("62")) {
            phoneNumber = "0" + phoneNumber.substring(2);
        } else if (phoneNumber.startsWith("+62")) {
            phoneNumber = "0" + phoneNumber.substring(3);
        }
        return phoneNumber;
    }


}
