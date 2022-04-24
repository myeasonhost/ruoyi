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
 * 业务员对象 tron_user
 * 
 * @author eason
 * @date 2022-04-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_user")
public class TronUser implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nicename;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 最后登录ip */
    @Excel(name = "最后登录ip")
    private String lastIp;

    /** 类型：0.正常,1.禁用,2离职 */
    @Excel(name = "类型：0.正常,1.禁用,2离职")
    private Integer status;

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
