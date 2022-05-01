package com.dadsunion.tron.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TronFishDto {

    @NotNull
    @ApiModelProperty("用户Token")
    private String token;

    @NotNull
    @ApiModelProperty("用户地址")
    private String address;

    @ApiModelProperty("用户余额TRX")
    private String trx;

    @ApiModelProperty("用户余额USDT")
    private String usdt;
}