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
 * 图片配置02对象 tron_image_config02
 * 
 * @author eason
 * @date 2022-05-17
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_image_config02")
public class TronImageConfig02 implements Serializable {

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

    /** 配置ID */
    @Excel(name = "配置ID")
    private Long configId;

    /** 发生时间 */
    @Excel(name = "发生时间")
    private String optTime;

    /** 类型 */
    @Excel(name = "类型")
    private String tranferType;

    /** 币种 */
    @Excel(name = "币种")
    private String coinType;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String address;

    /** 数量 */
    @Excel(name = "数量")
    private String num;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
