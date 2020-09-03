package com.demo.walletservice.service;

import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.model.WalletRequest;
import com.demo.walletservice.model.WalletTransaction;
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
    public Wallet addWallet(WalletRequest wallet) {
        final String phoneNumber = formatPhoneNumber(wallet.getPhoneNumber());
        Wallet newWallet = new Wallet();
        newWallet.setName(wallet.getName());
        newWallet.setEmail(wallet.getEmail());
        newWallet.setPhoneNumber(phoneNumber);
        newWallet.setBalance(0L);
        return walletRepository.save(newWallet);
    }

    @Override
    public Wallet updateWallet(Long walletId, WalletRequest walletrequest) {
        final String phoneNumber = formatPhoneNumber(walletrequest.getPhoneNumber());
        final Wallet updatedWallet = walletRepository.findById(walletId).orElse(null);
        if (updatedWallet != null) {
            updatedWallet.setName(walletrequest.getName());
            updatedWallet.setEmail(walletrequest.getEmail());
            updatedWallet.setPhoneNumber(phoneNumber);
            return walletRepository.save(updatedWallet);
        }
        return null;
    }

    @Override
    public Wallet topUpBalance(WalletTransaction walletTransaction) {
        final Wallet walletByPhoneNumber = getWalletByPhoneNumber(walletTransaction.getPhoneNumber());
        walletByPhoneNumber.setBalance(walletByPhoneNumber.getBalance() + walletTransaction.getAmount());
        return walletRepository.save(walletByPhoneNumber);
    }

    @Override
    public Wallet deductBalance(WalletTransaction walletTransaction) {
        final Wallet walletByPhoneNumber = getWalletByPhoneNumber(walletTransaction.getPhoneNumber());
        walletByPhoneNumber.setBalance(walletByPhoneNumber.getBalance() - walletTransaction.getAmount());
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
