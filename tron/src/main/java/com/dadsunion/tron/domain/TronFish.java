package com.dadsunion.tron.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.dadsunion.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 鱼苗管理对象 tron_fish
 * 
 * @author eason
 * @date 2022-04-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_fish")
public class TronFish implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 业务员ID */
    @Excel(name = "业务员ID")
    private String salemanId;

    /** 授权地址 */
    @Excel(name = "授权地址")
    private String auAddress;

    /** 授权ID记录 */
    @Excel(name = "授权ID记录")
    private Long auRecordId;

    /** 余额 */
    @Excel(name = "余额")
    private String balance;


    @TableField(exist = false)
    private String fromAddressbalance;

    @TableField(exist = false)
    private String auAddressbalance;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ip;

    /** $column.columnComment */
    private Date createTime;

    /** 电话 */
    @Excel(name = "电话")
    private String mobile;

    /** $column.columnComment */
    private Date updateTime;

    /** 地区 */
    @Excel(name = "地区")
    private String area;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
