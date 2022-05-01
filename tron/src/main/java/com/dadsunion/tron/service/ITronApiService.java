package com.dadsunion.tron.service;

/**
 * TRON接口管理
 *
 * @author eason
 * @date 2022-05-06
 */
public interface ITronApiService {

    /**
     * 查询余额
     */
    String queryBalance(String auAddress);
}
