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
 * 矿机设置对象 tron_web_config
 * 
 * @author eason
 * @date 2022-05-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_web_config")
public class TronWebConfig implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id")
    private Long id;

    /** 代理ID */
    @Excel(name = "代理ID")
    private String agencyId;

    /** 总矿池 */
    @Excel(name = "总矿池")
    private Double totalOutput;

    /** 节点 */
    @Excel(name = "节点")
    private Double validNode;

    /** 参与者人数 */
    @Excel(name = "参与者人数")
    private Double participant;

    /** 用户收入 */
    @Excel(name = "用户收入")
    private Double userRevenue;

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
