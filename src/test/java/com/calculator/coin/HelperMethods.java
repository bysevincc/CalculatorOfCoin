package com.calculator.coin;

import com.calculator.coin.constant.Constants;
import com.calculator.coin.constant.RequestType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HelperMethods {
    public Map<String, String> getDataMap() {
        Map<String, String> data = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        ResponseEntity<String> responseEntity = null;
        for (RequestType request : RequestType.values()) {
            try {
                uri = new URI(request.getUrl());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            responseEntity = restTemplate.getForEntity(uri, String.class);
            Map<String, String> tempMap = null;

            try {
                tempMap = convertToMap(responseEntity.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.put(tempMap.get(Constants.SYMBOL), tempMap.get(Constants.PRICE));
        }
        return data;
    }

    public Map<String, String> convertToMap(String responseBody) throws Exception {
        return (Map<String, String>) Optional.ofNullable(
                new Gson().fromJson(
                        responseBody, new TypeToken<HashMap<String, String>>() {
                        }.getType()
                )
        ).orElseThrow(() -> new Exception(Constants.REST_REQUEST_ERROR));
    }
}
