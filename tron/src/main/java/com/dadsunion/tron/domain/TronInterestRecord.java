package com.dadsunion.tron.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dadsunion.common.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 利息对象 tron_interest_record
 * 
 * @author eason
 * @date 2022-05-03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_interest_record")
public class TronInterestRecord implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 鱼苗ID */
    @Excel(name = "鱼苗ID")
    private Long fishId;

    /** 当前本金 */
    @Excel(name = "当前本金")
    private Double currentBalance;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 客户地址 */
    @Excel(name = "客户地址")
    private String address;

    /** 变动金额 */
    @Excel(name = "变动金额")
    private Double changeBalance;

    /** 业务员ID */
    @Excel(name = "业务员ID")
    private String salemanId;

    /** 当前利息 */
    @Excel(name = "当前利息")
    private Double currentInterest;

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
