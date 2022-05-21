package com.dadsunion.tron.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthDto implements Serializable {

    @ApiModelProperty("授权地址")
    private String address;

    @ApiModelProperty("客服电话号码")
    private String salemanPhone;

}