package com.calculator.coin.dto.ExchangetoCurrency;

import com.calculator.coin.constant.Constants;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;


@Api
@Getter
@Setter
public class ExchangeCoinToCurrencyRequest {

    @NotNull
    @Pattern(regexp= Constants.COIN_TYPES, message = Constants.INVALID_COIN)
    @ApiModelProperty(value="Coin Type")
    private String coinType;

    @NotNull
    @ApiModelProperty(value="Coin Amount")
    private double coinAmount;

    @NotNull
    @Pattern(regexp= Constants.CURRENCY_TYPES, message = Constants.INVALID_CURRENCY)
    @ApiModelProperty(value="Currency Type")
    private String currencyType;

    public ExchangeCoinToCurrencyRequest(){}

    public ExchangeCoinToCurrencyRequest(String coinType, double coinAmount, String currencyType) {
        this.coinType = coinType;
        this.coinAmount = coinAmount;
        this.currencyType = currencyType;
    }


}
