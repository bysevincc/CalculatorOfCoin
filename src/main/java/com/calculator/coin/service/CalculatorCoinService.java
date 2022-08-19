package com.calculator.coin.service;


import org.springframework.stereotype.Service;

@Service
public interface CalculatorCoinService<T,S> {
    S calculate(T request) throws Exception;
}
