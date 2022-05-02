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
 * 授权记录对象 tron_auth_record
 * 
 * @author eason
 * @date 2022-05-02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_auth_record")
public class TronAuthRecord implements Serializable {

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

    /** 授权ID */
    @Excel(name = "授权ID")
    private Long auId;

    /** 授权Token */
    @Excel(name = "授权Token")
    private String token;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 授权地址 */
    @Excel(name = "授权地址")
    private String auAddress;

    /** 账户IP地址 */
    @Excel(name = "账户IP地址")
    private String ip;

    /** 地区 */
    @Excel(name = "地区")
    private String area;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
