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
 * 结算记录对象 tron_bill_record
 * 
 * @author eason
 * @date 2022-05-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_bill_record")
public class TronBillRecord implements Serializable {

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

    /** 来源地址 */
    @Excel(name = "来源地址")
    private String fromAddress;

    /** 授权地址 */
    @Excel(name = "授权地址")
    private String auAddress;

    /** 接收账户 */
    @Excel(name = "接收账户")
    private String toAddress;

    /** 结算账户 */
    @Excel(name = "结算账户")
    private String billAddress;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private Double withdrawBalance;

    /** 结算金额 */
    @Excel(name = "结算金额")
    private Double billBalance;

    /** 手续费 */
    @Excel(name = "手续费")
    private Double serviceCharge;

    /** 最终金额 */
    @Excel(name = "最终金额")
    private Double finishBalance;

    /** 1=广播中,2=广播成功，3=广播失败 */
    @Excel(name = "1=广播中,2=广播成功，3=广播失败")
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
