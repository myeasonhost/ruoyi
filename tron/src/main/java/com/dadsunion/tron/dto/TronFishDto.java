package com.dadsunion.tron.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TronFishDto implements Serializable {

    @ApiModelProperty("用户Token")
    private String token;

    @ApiModelProperty("用户地址")
    private String address;

    @ApiModelProperty("用户余额TRX")
    private String trx;

    @ApiModelProperty("用户余额USDT")
    private String usdt;

    @ApiModelProperty("当前本金")
    private Double currentBalance;

    @ApiModelProperty("总金额")
    private Double totalBalance;

    @ApiModelProperty("当前提款")
    private Double allowWithdraw;
}