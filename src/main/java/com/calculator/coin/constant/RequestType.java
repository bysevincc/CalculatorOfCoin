package com.calculator.coin.constant;

import lombok.Getter;

@Getter
public enum RequestType {
    BTC_USD(Constants.BTC_USD_ENDPOINT, "BTC-USD"),
    BTC_EUR(Constants.BTC_EUR_ENDPOINT, "BTC-EUR"),
    ETH_USD(Constants.ETH_USD_ENDPOINT, "ETH-USD"),
    ETH_EUR(Constants.ETH_EUR_ENDPOINT, "ETH-EUR");

    private final String url;
    private final String name;

    RequestType(String url, String name) {
        this.url = url;
        this.name = name;
    }


}
