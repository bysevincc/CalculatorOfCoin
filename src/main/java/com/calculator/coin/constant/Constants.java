package com.calculator.coin.constant;

public class Constants {
    public static final String BTC_USD_ENDPOINT = "https://api.blockchain.com/v3/exchange/tickers/BTC-USD";
    public static final String BTC_EUR_ENDPOINT = "https://api.blockchain.com/v3/exchange/tickers/BTC-EUR";
    public static final String ETH_USD_ENDPOINT = "https://api.blockchain.com/v3/exchange/tickers/ETH-USD";
    public static final String ETH_EUR_ENDPOINT = "https://api.blockchain.com/v3/exchange/tickers/ETH-EUR";

    //symbols and others
    public static final String SYMBOL = "symbol";
    public static final String PRICE = "price_24h";
    public static final String DELIMITER = "_";
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    //validators and exceptions
    public static final String COIN_TYPES = "^(BTC|ETH)$";
    public static final String CURRENCY_TYPES = "^(USD|EUR)$";
    public static final String INVALID_COIN = "Invalid coin type!";
    public static final String INVALID_CURRENCY = "Invalid currency type!";
    public static final String SMALL_PRICE = "Invalid currency amount, it must be greater than or equals to 25!";
    public static final String BIG_PRICE = "Invalid currency amount, it must be less than or equals to 25!";
    public static final String REST_REQUEST_ERROR = "Blockchain.com returned empty!";

}
