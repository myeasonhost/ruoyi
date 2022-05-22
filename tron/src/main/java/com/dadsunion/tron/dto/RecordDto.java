package com.dadsunion.tron.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RecordDto implements Serializable {

    @ApiModelProperty("用户Token")
    private String time;

    @ApiModelProperty("用户地址")
    private String quantity;

    @ApiModelProperty("状态")
    private String status;

}