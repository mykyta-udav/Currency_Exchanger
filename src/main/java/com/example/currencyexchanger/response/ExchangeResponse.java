package com.example.currencyexchanger.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeResponse {
    private String base;
    private BigDecimal amount;
    private Map<String, BigDecimal> result;
    private long ms;
}
