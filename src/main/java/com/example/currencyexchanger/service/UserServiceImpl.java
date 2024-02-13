package com.example.currencyexchanger.service;

import com.example.currencyexchanger.entity.User;
import com.example.currencyexchanger.entity.Wallet;
import com.example.currencyexchanger.repository.UserRepository;
import com.example.currencyexchanger.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;


    @Override
    @Transactional
    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Wallet getUserWallet(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return (user != null) ? user.getWallet() : null;
    }

    @Override
    @Transactional
    public void addCurrencyToUserWallet(Long userId, String currency, BigDecimal amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Wallet wallet = user.getWallet();
            wallet.addCurrency(currency, amount);
            walletRepository.save(wallet);
        }
    }

    @Override
    public void updateUserWallet(Long userId, String currency, BigDecimal amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Wallet wallet = user.getWallet();
            wallet.updateCurrency(currency, amount);
            walletRepository.save(wallet);
        }
    }

    @Override
    public void updateWithoutFee(Long userId, int withoutFee) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setWithoutFee(withoutFee);
            userRepository.save(user);
        }
    }
}

