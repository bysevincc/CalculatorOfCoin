package com.calculator.coin.dto.ExchangetoCoin;

import com.calculator.coin.constant.Constants;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;


@Api
@Getter
@Setter
public class ExchangeCurrencyToCoinRequest {

    @Pattern(regexp= Constants.CURRENCY_TYPES, message = Constants.INVALID_CURRENCY)
    @NotNull
    @ApiModelProperty(value="Currency Type")
    private String currencyType;

    @Min(value = 25, message = Constants.SMALL_PRICE)
    @Max(value = 5000, message = Constants.BIG_PRICE)
    @NotNull
    @ApiModelProperty
    private double currencyAmount;

    @Pattern(regexp= Constants.COIN_TYPES, message = Constants.INVALID_COIN)
    @NotNull
    @ApiModelProperty(value="Coin Type")
    private String coinType;

    public ExchangeCurrencyToCoinRequest(){}

    public ExchangeCurrencyToCoinRequest(String currencyType, String coinType, double currencyAmount) {
        this.currencyType = currencyType;
        this.coinType = coinType;
        this.currencyAmount = currencyAmount;
    }

}
