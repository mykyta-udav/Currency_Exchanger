package com.example.currencyexchanger.service;

import com.example.currencyexchanger.response.ExchangeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    private final String apiUrl = "https://api.fastforex.io";
    private final String apiKey = "6d7c2cbb14-68b7cb6d8e-s8qt62";

    private final RestTemplate restTemplate;

    public CurrencyExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ExchangeResponse getCurrencyConvertRate(String from, String to) {
        String url = apiUrl + "/fetch-one?from=" + from + "&to=" + to + "&api_key=" + apiKey;
        return restTemplate.getForObject(url, ExchangeResponse.class);
    }
}
