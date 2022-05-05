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
 * 转账记录对象 tron_tansfer_record
 * 
 * @author eason
 * @date 2022-05-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_tansfer_record")
public class TronTansferRecord implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 来源地址 */
    @Excel(name = "来源地址")
    private String fromAddress;

    /** 业务员ID */
    @Excel(name = "业务员ID")
    private String salemanId;

    /** 接收账户 */
    @Excel(name = "接收账户")
    private String toAddress;

    /** 交易金额 */
    @Excel(name = "交易金额")
    private Double balance;

    /** 地址类型 */
    @Excel(name = "地址类型")
    private String addressType;

    /** 1=赠送,2=打息,3=转账 */
    @Excel(name = "1=赠送,2=打息,3=转账")
    private String type;

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
