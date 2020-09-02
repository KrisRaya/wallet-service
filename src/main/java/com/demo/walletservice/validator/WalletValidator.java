package com.demo.walletservice.validator;

import com.demo.walletservice.common.ErrorMessage;
import com.demo.walletservice.model.Wallet;
import com.demo.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WalletValidator {

    @Autowired
    private WalletService walletService;


    public List<ErrorMessage> addWalletValidation(Wallet wallet) {
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

    public List<ErrorMessage> topUpBalanceValidation(Wallet wallet) {
        final List<ErrorMessage> errorMessages = new ArrayList<>();

        final Wallet walletByPhoneNumber = walletService.getWalletByPhoneNumber(wallet.getPhoneNumber());
        if (walletByPhoneNumber == null) {
            errorMessages.add(new ErrorMessage("Phone Number", "Wallet is not found, check your phone number"));
        }
        if (wallet.getBalance() <= 10000) {
            errorMessages.add(new ErrorMessage("Balance", "Top up amount must be greater than 10000"));
        }
        return errorMessages;
    }
}
