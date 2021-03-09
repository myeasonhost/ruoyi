package com.dadsunion.tron.task;

import com.dadsunion.common.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 扫描链上转账信息
 * 
 * @author ruoyi
 */
@Component("scanTask")
public class ScanTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}