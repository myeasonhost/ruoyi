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
 * 粉丝对象 tron_fans
 * 
 * @author eason
 * @date 2022-05-16
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_fans")
public class TronFans implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 业务员ID */
    @Excel(name = "业务员ID")
    private String salemanId;

    /** 电话 */
    @Excel(name = "电话")
    private String mobile;

    /** 渠道 */
    @Excel(name = "渠道")
    private String chanel;

    /** 职业 */
    @Excel(name = "职业")
    private String profession;

    /** 钱包类型 */
    @Excel(name = "钱包类型")
    private String walletType;

    /** 交易所类型 */
    @Excel(name = "交易所类型")
    private String exchangeType;

    /** 地区 */
    @Excel(name = "地区")
    private String area;

    /** 是否有效 */
    @Excel(name = "是否有效")
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
