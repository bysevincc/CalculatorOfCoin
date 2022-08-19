package com.calculator.coin;

import com.calculator.coin.constant.Constants;
import com.calculator.coin.constant.RequestType;
import com.calculator.coin.dto.ExchangetoCoin.ExchangeCurrencyToCoinRequest;
import com.calculator.coin.dto.ExchangetoCoin.ExchangeCurrencyToCoinResponse;
import com.calculator.coin.dto.ExchangetoCurrency.ExchangeCoinToCurrencyRequest;
import com.calculator.coin.dto.ExchangetoCurrency.ExchangeCoinToCurrencyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@SpringBootTest
class IntegrationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

   // private HelperMethods helperMethods;

  //  @BeforeEach
  //  void setUp() {
      //  helperMethods = new HelperMethods();
    //}

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
    @Test
    @DisplayName("USD to BTC calculator test")
    public void USD_to_BTC_calculator_test() throws Exception {
        ExchangeCurrencyToCoinRequest requestDto = new ExchangeCurrencyToCoinRequest();
        requestDto.setCurrencyAmount(2000);
        requestDto.setCoinType("BTC");
        requestDto.setCurrencyType("USD");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/coin");
        ExchangeCurrencyToCoinResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeCurrencyToCoinResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = requestDto.getCurrencyAmount() / Double.parseDouble(dataMap.get("BTC-USD"));

        assertEquals(actualResponseBody.getCoinAmount(), expected);
    }

    @Test
    @DisplayName("EUR to BTC calculator test")
    public void EUR_to_BTC_calculator_test() throws Exception {
        ExchangeCurrencyToCoinRequest requestDto = new ExchangeCurrencyToCoinRequest();
        requestDto.setCurrencyAmount(1500);
        requestDto.setCoinType("BTC");
        requestDto.setCurrencyType("EUR");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/coin");
        ExchangeCurrencyToCoinResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeCurrencyToCoinResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = requestDto.getCurrencyAmount() / Double.parseDouble(dataMap.get("BTC-EUR"));

        assertEquals(actualResponseBody.getCoinAmount(), expected);
    }

    @Test
    @DisplayName("USD to ETH calculator test")
    public void USD_to_ETH_calculator_test() throws Exception {
        ExchangeCurrencyToCoinRequest requestDto = new ExchangeCurrencyToCoinRequest();
        requestDto.setCurrencyAmount(400);
        requestDto.setCoinType("ETH");
        requestDto.setCurrencyType("USD");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/coin");
        ExchangeCurrencyToCoinResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeCurrencyToCoinResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = requestDto.getCurrencyAmount() / Double.parseDouble(dataMap.get("ETH-USD"));

        assertEquals(actualResponseBody.getCoinAmount(), expected);
    }

    @Test
    @DisplayName("EUR to ETH calculator test")
    public void EUR_to_ETH_calculator_test() throws Exception {
        ExchangeCurrencyToCoinRequest requestDto = new ExchangeCurrencyToCoinRequest();
        requestDto.setCurrencyAmount(888);
        requestDto.setCoinType("ETH");
        requestDto.setCurrencyType("EUR");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/coin");
        ExchangeCurrencyToCoinResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeCurrencyToCoinResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = requestDto.getCurrencyAmount() / Double.parseDouble(dataMap.get("ETH-EUR"));

        assertEquals(actualResponseBody.getCoinAmount(), expected);
    }


    @Test
    @DisplayName("BTC to USD calculator test")
    public void BTC_to_USD_calculator_test() throws Exception {
        ExchangeCoinToCurrencyRequest requestDto = new ExchangeCoinToCurrencyRequest();
        requestDto.setCoinAmount(20);
        requestDto.setCoinType("BTC");
        requestDto.setCurrencyType("USD");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/currency");
        ExchangeCoinToCurrencyResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeCoinToCurrencyResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = Double.parseDouble(dataMap.get("BTC-USD")) * requestDto.getCoinAmount();

        assertEquals(actualResponseBody.getCurrencyAmount(), expected);
    }

    @Test
    @DisplayName("BTC to EUR calculator test")
    public void BTC_to_EUR_calculator_test() throws Exception {
        ExchangeCoinToCurrencyRequest requestDto = new ExchangeCoinToCurrencyRequest();
        requestDto.setCoinAmount(200);
        requestDto.setCoinType("BTC");
        requestDto.setCurrencyType("EUR");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/currency");
        ExchangeCoinToCurrencyResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeCoinToCurrencyResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = Double.parseDouble(dataMap.get("BTC-EUR")) * requestDto.getCoinAmount();

        assertEquals(actualResponseBody.getCurrencyAmount(), expected);
    }

    @Test
    @DisplayName("ETH to USD calculator test")
    public void ETH_to_USD_calculator_test() throws Exception {
        ExchangeCoinToCurrencyRequest requestDto = new ExchangeCoinToCurrencyRequest();
        requestDto.setCoinAmount(100);
        requestDto.setCoinType("ETH");
        requestDto.setCurrencyType("USD");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/currency");
        ExchangeCoinToCurrencyResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(),  ExchangeCoinToCurrencyResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = Double.parseDouble(dataMap.get("ETH-USD")) * requestDto.getCoinAmount();

        assertEquals(actualResponseBody.getCurrencyAmount(), expected);
    }

    @Test
    @DisplayName("ETH to EUR calculator test")
    public void ETH_to_EUR_calculator_test() throws Exception {
        ExchangeCoinToCurrencyRequest requestDto = new  ExchangeCoinToCurrencyRequest();
        requestDto.setCoinAmount(200);
        requestDto.setCoinType("ETH");
        requestDto.setCurrencyType("EUR");

        MvcResult result = mvcPerformer(requestDto, "/calculatorcoin/calculate/currency");
        ExchangeCoinToCurrencyResponse actualResponseBody = objectMapper.readValue(result.getResponse().getContentAsString(),  ExchangeCoinToCurrencyResponse.class);

        Map<String, String> dataMap = getDataMap();
        double expected = Double.parseDouble(dataMap.get("ETH-EUR")) * requestDto.getCoinAmount();

        assertEquals(actualResponseBody.getCurrencyAmount(), expected);
    }

    public MvcResult mvcPerformer(Object object, String endpoint) throws Exception {
        MvcResult result = mvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(status().isOk()).
                andReturn();
        return result;
    }
}