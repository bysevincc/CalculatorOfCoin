package com.calculator.coin.service;

import com.calculator.coin.constant.Constants;
import com.calculator.coin.constant.RequestType;
import com.calculator.coin.dto.ExchangetoCurrency.ExchangeCoinToCurrencyRequest;
import com.calculator.coin.dto.ExchangetoCurrency.ExchangeCoinToCurrencyResponse;
import com.calculator.coin.entity.CalculatorCoinEntity;
import com.calculator.coin.repository.CalculatorCoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class ExchangeCoinToCurrencyServiceImpl implements CalculatorCoinService<ExchangeCoinToCurrencyRequest,ExchangeCoinToCurrencyResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeCoinToCurrencyServiceImpl.class);
    private CoinService coinDataService;
    private CalculatorCoinRepository coinRepository;


    @Override
    public ExchangeCoinToCurrencyResponse calculate(ExchangeCoinToCurrencyRequest request) {
        RequestType requestType = RequestType.valueOf(request.getCoinType() + Constants.DELIMITER + request.getCurrencyType());

        Map<String, String> coinDataMap = coinDataService.getCoinData();
        double result = request.getCoinAmount() * Double.parseDouble(coinDataMap.get(requestType.getName()));
        ExchangeCoinToCurrencyResponse exchangeCoinToCurrencyResponse = new ExchangeCoinToCurrencyResponse();
        exchangeCoinToCurrencyResponse.setCurrencyAmount(result);


        CalculatorCoinEntity coinEntity = createEntity(result, request);
        coinRepository.save(coinEntity);
        LOGGER.info(coinEntity.toString());

        return exchangeCoinToCurrencyResponse;
    }

    private CalculatorCoinEntity createEntity(double result, ExchangeCoinToCurrencyRequest request) {
        CalculatorCoinEntity coinEntity = new CalculatorCoinEntity();
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();

        coinEntity.setSourceType(request.getCoinType());
        coinEntity.setSourceAmount(request.getCoinAmount());
        coinEntity.setTargetType(request.getCurrencyType());
        coinEntity.setTargetAmount(result);
        coinEntity.setTransactionDate(date);
        return coinEntity;
    }

}

