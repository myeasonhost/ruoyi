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

    @ApiModelProperty("总矿池")
    private Double totalOutput;

    @ApiModelProperty("节点")
    private Double validNode;

    @ApiModelProperty("参与者人数")
    private Double participant;

    @ApiModelProperty("用户收入")
    private Double userRevenue;


}