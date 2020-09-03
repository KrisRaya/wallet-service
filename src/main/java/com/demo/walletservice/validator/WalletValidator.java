package com.demo.walletservice.validator;

import com.demo.walletservice.common.ErrorMessage;
import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.model.WalletRequest;
import com.demo.walletservice.model.WalletTransaction;
import com.demo.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WalletValidator {

    @Autowired
    private WalletService walletService;


    public List<ErrorMessage> walletValidation(WalletRequest wallet) {
        final List<ErrorMessage> errorMessages = new ArrayList<>();
        if (wallet.getName().isEmpty()) {
            errorMessages.add(new ErrorMessage("Name", "Name must be set"));
        }
        if (wallet.getPhoneNumber().isEmpty()) {
            errorMessages.add(new ErrorMessage("Phone Number", "Phone number must be set"));
        }
        if (!wallet.getPhoneNumber().isEmpty() &&
                wallet.getPhoneNumber().length() < 7 || wallet.getPhoneNumber().length() > 13 ) {
            errorMessages.add(new ErrorMessage("Phone Number", "Phone number must be between 7 - 12 digits"));
        }
        if (!wallet.getEmail().isEmpty()) {
            Pattern pattern = Pattern.compile("^(.+)@(.+)$");
            Matcher matcher = pattern.matcher(wallet.getEmail());
            if (!matcher.matches()) {
                errorMessages.add(new ErrorMessage("Email", "Email format must be 123@abc.com"));
            }
        }

        return errorMessages;
    }

    public List<ErrorMessage> topUpBalanceValidation(WalletTransaction wallet) {
        final List<ErrorMessage> errorMessages = new ArrayList<>();

        final Wallet walletByPhoneNumber = walletService.getWalletByPhoneNumber(wallet.getPhoneNumber());
        if (walletByPhoneNumber == null) {
            errorMessages.add(new ErrorMessage("Phone Number", "Wallet is not found, check your phone number"));
        }
        if (wallet.getAmount() <= 10000) {
            errorMessages.add(new ErrorMessage("Amount", "Top up amount must be greater than 10000"));
        }
        return errorMessages;
    }

    public List<ErrorMessage> deductBalanceValidation(WalletTransaction walletTransaction) {
        final List<ErrorMessage> errorMessages = new ArrayList<>();

        final Wallet walletByPhoneNumber = walletService.getWalletByPhoneNumber(walletTransaction.getPhoneNumber());
        if (walletByPhoneNumber == null) {
            errorMessages.add(new ErrorMessage("Phone Number", "Wallet is not found, check your phone number"));
        }
        if (walletByPhoneNumber != null && walletByPhoneNumber.getBalance() - walletTransaction.getAmount() <= 0) {
            errorMessages.add(new ErrorMessage("Balance", "Insufficient balance"));
        }
        return errorMessages;

    }

    public List<ErrorMessage> addWalletValidation(WalletRequest wallet) {
        return Stream.concat(walletValidation(wallet).stream(), verifyPhoneNumberExist(wallet.getPhoneNumber()).stream())
                .collect(Collectors.toList());
    }

    public List<ErrorMessage> updateWalletValidation(Long walletId, WalletRequest wallet) {
        return Stream.concat(Stream.concat(verifyWalletIdExist(walletId).stream(),
                walletValidation(wallet).stream()), verifyPhoneNumberExist(wallet.getPhoneNumber()).stream())
                .collect(Collectors.toList());
    }

    private List<ErrorMessage> verifyWalletIdExist(Long walletId) {
        final List<ErrorMessage> errorMessages = new ArrayList<>();

        final Wallet wallet = walletService.findById(walletId).orElse(null);
        if (wallet == null) {
            errorMessages.add(new ErrorMessage("Wallet Id", "Wallet ID is not exist"));
        }
        return errorMessages;
    }

    private List<ErrorMessage> verifyPhoneNumberExist(String phoneNumber) {
        final List<ErrorMessage> errorMessages = new ArrayList<>();

        final Wallet wallet = walletService.getWalletByPhoneNumber(phoneNumber);
        if (wallet != null) {
            errorMessages.add(new ErrorMessage("Phone Number", "Phone number has been used"));
        }
        return errorMessages;
    }
}
