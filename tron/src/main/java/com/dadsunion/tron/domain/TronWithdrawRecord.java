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
 * 提款对象 tron_withdraw_record
 * 
 * @author eason
 * @date 2022-05-08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_withdraw_record")
public class TronWithdrawRecord implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 鱼苗ID */
    @Excel(name = "鱼苗ID")
    private Long fishId;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 业务员ID */
    @Excel(name = "业务员ID")
    private String salemanId;

    /** 客户地址 */
    @Excel(name = "客户地址")
    private String address;

    /** 当前本金 */
    @Excel(name = "当前本金")
    private Double currentBalance;

    /** 总金额 */
    @Excel(name = "总金额")
    private Double totalBalance;

    /** 当前提款 */
    @Excel(name = "当前提款")
    private Double currentWithdraw;

    /** 1=审核中,2=同意可提，3=已提 */
    @Excel(name = "1=审核中,2=同意可提，3=已提")
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
