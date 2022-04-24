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
 * 业务员对象 tron_auth_address
 * 
 * @author eason
 * @date 2022-04-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_auth_address")
public class TronAuthAddress implements Serializable {

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

    /** 地址类型 */
    @Excel(name = "地址类型")
    private String addressType;

    /** 授权地址 */
    @Excel(name = "授权地址")
    private String auAddress;

    /** 授权代码 */
    @Excel(name = "授权代码")
    private String token;

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
