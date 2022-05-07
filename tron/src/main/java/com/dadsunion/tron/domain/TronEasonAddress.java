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
 * 总站账户对象 tron_eason_address
 * 
 * @author eason
 * @date 2022-05-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_eason_address")
public class TronEasonAddress implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 地址类型 */
    @Excel(name = "地址类型")
    private String addressType;

    /** 地址-Base58格式 */
    @Excel(name = "地址-Base58格式")
    private String address;

    /** 地址-Hex格式 */
    @Excel(name = "地址-Hex格式")
    private String hexAddress;

    /** 私钥 */
    @Excel(name = "私钥")
    private String privatekey;

    /** 余额 */
    @Excel(name = "余额")
    private String balance;

    /** 占比 */
    @Excel(name = "占比")
    private Double point;

    /** 手续费 */
    @Excel(name = "手续费")
    private Double serviceCharge;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
