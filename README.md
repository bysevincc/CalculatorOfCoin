# CalculatorOfCoin

Calculate USD to BTC - //Post Endpoint
/calculatorcoin/calculate/coin
{
  "coinType": "BTC",
  "currencyAmount": 2000,
  "currencyType": "USD"
}

Calculate BTC to USD - //Post Endpoint 
/calculatorcoin/calculate/currency
{
  "coinAmount": 20,
  "coinType": "BTC",
  "currencyType": "USD"
}

all record  ///Get Endpoint:
/calculatorcoin/coins
