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
 * 图片配置01对象 tron_image_config01
 * 
 * @author eason
 * @date 2022-05-17
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tron_image_config01")
public class TronImageConfig01 implements Serializable {

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

    /** 信号 */
    @Excel(name = "信号")
    private String xinhao;

    /** 网络 */
    @Excel(name = "网络")
    private String wang;

    /** 电量 */
    @Excel(name = "电量")
    private String dian;

    /** 当前时间 */
    @Excel(name = "当前时间")
    private String time;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String address;

    /** 转账未读 */
    @Excel(name = "转账未读")
    private String noreadTansfer;

    /** 系统未读 */
    @Excel(name = "系统未读")
    private String noreadSystem;

    /** trx价格 */
    @Excel(name = "trx价格")
    private String trxPrice;

    /** trx数量 */
    @Excel(name = "trx数量")
    private String trxNum;

    /** usdt价格 */
    @Excel(name = "usdt价格")
    private String usdtPrice;

    /** usdt价格 */
    @Excel(name = "usdt价格")
    private String usdtNum;

    /** 总收益 */
    @Excel(name = "总收益")
    private String shouyi;

    /** $column.columnComment */
    private Date createTime;

    /** 待提现 */
    @Excel(name = "待提现")
    private String tixian;


    /** $column.columnComment */
    private Date updateTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
