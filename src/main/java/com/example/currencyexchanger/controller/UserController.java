package com.example.currencyexchanger.controller;

import com.example.currencyexchanger.entity.User;
import com.example.currencyexchanger.entity.Wallet;
import com.example.currencyexchanger.response.ExchangeResponse;
import com.example.currencyexchanger.service.CurrencyExchangeService;
import com.example.currencyexchanger.service.CurrencyExchangeServiceImpl;
import com.example.currencyexchanger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyExchangeService currencyExchangeService;


    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String name) {
        User user = userService.createUser(name);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/wallet")
    public ResponseEntity<Wallet> getUserWallet(@PathVariable Long userId) {
        Wallet wallet = userService.getUserWallet(userId);
        return (wallet != null) ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}/add-currency")
    public ResponseEntity<String> addCurrencyToUserWallet(
            @PathVariable Long userId,
            @RequestParam String currency,
            @RequestParam BigDecimal amount
    ) {
        userService.addCurrencyToUserWallet(userId, currency, amount);
        return ResponseEntity.ok("Currency added successfully");
    }

    @PutMapping("/{userId}/exchange-currency")
    public String exchangeCurrency(
            @PathVariable Long userId,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount
    ) {
        if (userService.getUserById(userId).getWallet().getCurrencies().get(from) == null){
            return "You don't have enough money of %s in your wallet".formatted(from);
        }
        ExchangeResponse currencyConvertRate = currencyExchangeService.getCurrencyConvertRate(from, to);
        BigDecimal rate = currencyConvertRate.getResult().get(to);
        User user = userService.getUserById(userId);
        if (user.getWithoutFee() > 0) {
            BigDecimal fromAmount = user.getWallet().getCurrencies().get(from).subtract(amount);
            BigDecimal toAmount = user.getWallet().getCurrencies().merge(to, amount.multiply(rate), BigDecimal::add);
            userService.updateUserWallet(userId, from, fromAmount);
            userService.updateUserWallet(userId, to, toAmount);
            userService.updateWithoutFee(userId, user.getWithoutFee() - 1);
            return ("You have converted %f %s to %f %S and now the balance is\n" +
                    "%f %S and %f %S.").formatted(amount, from, amount.multiply(rate), to, fromAmount, from, toAmount, to);
        } else {
            BigDecimal fromAmount = user.getWallet().getCurrencies().get(from).subtract(amount.add(amount.multiply(BigDecimal.valueOf(0.07))));
            BigDecimal toAmount = user.getWallet().getCurrencies().merge(to, amount.multiply(rate), BigDecimal::add);
            userService.updateUserWallet(userId, from, fromAmount);
            userService.updateUserWallet(userId, to, toAmount);
            return ("You have converted %f %s to %f %S and now the balance is\n" +
                    "%f %S and %f %S. Commission fee - %f %s").formatted(amount, from, amount.multiply(rate), to, fromAmount, from, toAmount, to, amount.multiply(BigDecimal.valueOf(0.07)), from);
        }
    }
}