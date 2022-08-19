package com.calculator.coin.service;

import com.calculator.coin.constant.Constants;
import com.calculator.coin.constant.RequestType;
import com.calculator.coin.dto.ExchangetoCoin.ExchangeCurrencyToCoinRequest;
import com.calculator.coin.dto.ExchangetoCoin.ExchangeCurrencyToCoinResponse;
import com.calculator.coin.entity.CalculatorCoinEntity;
import com.calculator.coin.repository.CalculatorCoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Component
public class ExchangeCurrencyToCoinServiceImpl implements CalculatorCoinService<ExchangeCurrencyToCoinRequest,ExchangeCurrencyToCoinResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeCurrencyToCoinServiceImpl.class);
    private CoinService coinService;
    private CalculatorCoinRepository coinRepository;
    public ExchangeCurrencyToCoinServiceImpl(CoinService coinService, CalculatorCoinRepository coinRepository) {
        this.coinService = coinService;
        this.coinRepository = coinRepository;
    }

    @Override
    public ExchangeCurrencyToCoinResponse calculate(ExchangeCurrencyToCoinRequest request) throws Exception {
        RequestType requestType = RequestType.valueOf(request.getCoinType() + Constants.DELIMITER + request.getCurrencyType());

        Map<String, String> coinMap = coinService.getCoinData();
        double result = request.getCurrencyAmount() / Double.parseDouble(coinMap.get(requestType.getName()));
        ExchangeCurrencyToCoinResponse exchangeCurrencyToCoinResponse = new ExchangeCurrencyToCoinResponse();
        exchangeCurrencyToCoinResponse.setCoinAmount(result);

        //create coin entity and save to db
        CalculatorCoinEntity coinEntity = createEntity(result, request);
        coinRepository.save(coinEntity);
        LOGGER.info(coinEntity.toString());

        return exchangeCurrencyToCoinResponse;
    }

    private CalculatorCoinEntity createEntity(double result, ExchangeCurrencyToCoinRequest requestDto){
        CalculatorCoinEntity coinEntity = new CalculatorCoinEntity();
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.CANADA);
        Date date = new Date();

        coinEntity.setSourceType(requestDto.getCurrencyType());
        coinEntity.setSourceAmount(requestDto.getCurrencyAmount());
        coinEntity.setTargetType(requestDto.getCoinType());
        coinEntity.setTargetAmount(result);
        coinEntity.setTransactionDate(date);
        return coinEntity;
    }
}
