package com.calculator.coin.converter;


import com.calculator.coin.service.CalculatorCoinService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CalculatorCoinCalculatorConverter {

    private List<CalculatorCoinService> coinServices;

    public CalculatorCoinCalculatorConverter(List<CalculatorCoinService> coinServices) {
        this.coinServices = coinServices;
    }

    public Optional<CalculatorCoinService> getCalculatorService(Class clazz){
        return coinServices.stream().filter(s -> s.getClass().equals(clazz)).findFirst();
    }
}
