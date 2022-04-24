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

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

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
