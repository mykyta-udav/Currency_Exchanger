package com.example.currencyexchanger.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "wallet_currencies", joinColumns = @JoinColumn(name = "wallet_id"))
    @MapKeyColumn(name = "currency")
    @Column(name = "amount")
    private Map<String, BigDecimal> currencies;

    public Wallet() {
        this.currencies = new HashMap<>();
        this.currencies.put("EUR", new BigDecimal("1000"));
    }

    public void addCurrency(String currency, BigDecimal amount) {
        currencies.put(currency, amount);
    }

    public BigDecimal getCurrencyAmount(String currency) {
        return currencies.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void updateCurrency(String currency, BigDecimal amount) {
        currencies.put(currency, amount);
    }
}

