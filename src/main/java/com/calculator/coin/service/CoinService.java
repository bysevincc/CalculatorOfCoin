package com.calculator.coin.service;

import com.calculator.coin.constant.Constants;
import com.calculator.coin.constant.RequestType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@EnableScheduling
@Service
@Getter
@Setter
public class CoinService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoinService.class);

    private Map<String, String> coinData;
    private RestTemplate restTemplate;


    private Map<String, String> convertResponseToHashMap(String responseBody) throws Exception {
        return (Map<String, String>) Optional.ofNullable(
                new Gson().fromJson(
                        responseBody, new TypeToken<HashMap<String, String>>() {
                        }.getType()
                )
        ).orElseThrow(() -> new Exception(Constants.REST_REQUEST_ERROR));
    }


    @Scheduled(fixedRate = 10000)
    public void request() throws Exception {
        URI uri = null;
        ResponseEntity<String> responseEntity = null;
        for (RequestType request : RequestType.values()) {
            uri = new URI(request.getUrl());
            responseEntity = restTemplate.getForEntity(uri, String.class);
            Map<String, String> tempMap = null;
            try {
                tempMap = convertResponseToHashMap(responseEntity.getBody());
            } catch (Exception e) {
                LOGGER.error(Constants.REST_REQUEST_ERROR);
                e.printStackTrace();
            }
            coinData.put(tempMap.get(Constants.SYMBOL), tempMap.get(Constants.PRICE));
        }

        LOGGER.info(String.valueOf(coinData));
    }

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.coinData = new HashMap<>();
        try {
            request();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
