package com.example.currencyexchanger.service;

import com.example.currencyexchanger.entity.User;
import com.example.currencyexchanger.entity.Wallet;

import java.math.BigDecimal;

public interface UserService {
    User createUser(String name);
    User getUserById(Long userId);
    Wallet getUserWallet(Long userId);
    void addCurrencyToUserWallet(Long userId, String currency, BigDecimal amount);
    void updateUserWallet(Long userId, String currency, BigDecimal amount);
    void updateWithoutFee(Long userId, int withoutFee);
}

