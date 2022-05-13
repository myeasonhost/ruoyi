package com.dadsunion.tron.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatFishDto implements Serializable {

    @ApiModelProperty("今日鱼苗")
    private Integer dayFish;

    @ApiModelProperty("鱼苗总数")
    private Integer totalFish;

    @ApiModelProperty("交易总额")
    private Double billUsdt;

    @ApiModelProperty("鱼苗总价值")
    private Double totalUsdt;

}