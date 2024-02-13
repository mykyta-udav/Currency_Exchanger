package com.example.currencyexchanger.repository;

import com.example.currencyexchanger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

