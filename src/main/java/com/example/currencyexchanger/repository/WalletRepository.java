package com.example.currencyexchanger.repository;

import com.example.currencyexchanger.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

