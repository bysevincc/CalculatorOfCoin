package com.calculator.coin.controller;


import com.calculator.coin.converter.CalculatorCoinCalculatorConverter;
import com.calculator.coin.dto.ExchangetoCoin.ExchangeCurrencyToCoinRequest;
import com.calculator.coin.dto.ExchangetoCoin.ExchangeCurrencyToCoinResponse;
import com.calculator.coin.dto.ExchangetoCurrency.ExchangeCoinToCurrencyRequest;
import com.calculator.coin.dto.ExchangetoCurrency.ExchangeCoinToCurrencyResponse;
import com.calculator.coin.entity.CalculatorCoinEntity;
import com.calculator.coin.repository.CalculatorCoinRepository;
import com.calculator.coin.service.CalculatorCoinService;
import com.calculator.coin.service.ExchangeCoinToCurrencyServiceImpl;
import com.calculator.coin.service.ExchangeCurrencyToCoinServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/calculatorcoin")
@Api
public class CalculatorCoinController {

    private CalculatorCoinRepository coinRepository;
    private CalculatorCoinCalculatorConverter coinCalculatorConverter;

    public CalculatorCoinController(CalculatorCoinRepository coinRepository, CalculatorCoinCalculatorConverter coinCalculatorConverter) {
        this.coinRepository = coinRepository;
        this.coinCalculatorConverter = coinCalculatorConverter;
    }

    @GetMapping("/coins")
    public List<CalculatorCoinEntity> getAllCoins() {
        return coinRepository.findAll();
    }
    @PostMapping("/calculate/currency")
    public ExchangeCoinToCurrencyResponse coinToCurrency(@Valid @RequestBody ExchangeCoinToCurrencyRequest request) {

        CalculatorCoinService calculatorCoinService = coinCalculatorConverter
                .getCalculatorService(ExchangeCoinToCurrencyServiceImpl.class)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));

        ExchangeCoinToCurrencyResponse coinToCurrencyResponseDto = null;
        try {
            coinToCurrencyResponseDto = (ExchangeCoinToCurrencyResponse) calculatorCoinService.calculate(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinToCurrencyResponseDto;
    }


    @PostMapping("/calculate/coin")
    public ExchangeCurrencyToCoinResponse currencyToCoinResponse(@Valid @RequestBody ExchangeCurrencyToCoinRequest currencyToCoinRequestDto) {

        CalculatorCoinService coinService = coinCalculatorConverter
                .getCalculatorService(ExchangeCurrencyToCoinServiceImpl.class)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));

        ExchangeCurrencyToCoinResponse currencyToCoinResponseDto = null;
        try {
            currencyToCoinResponseDto = (ExchangeCurrencyToCoinResponse) coinService.calculate(currencyToCoinRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currencyToCoinResponseDto;
    }


















}
