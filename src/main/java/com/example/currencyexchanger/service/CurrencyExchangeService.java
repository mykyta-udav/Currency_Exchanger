package com.example.currencyexchanger.service;

import com.example.currencyexchanger.response.ExchangeResponse;

public interface CurrencyExchangeService {
    ExchangeResponse getCurrencyConvertRate(String from, String to);
}
