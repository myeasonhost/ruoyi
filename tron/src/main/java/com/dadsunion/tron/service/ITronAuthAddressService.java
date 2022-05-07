package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronAuthAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 授权Service接口
 *
 * @author eason
 * @date 2022-04-20
 */
public interface ITronAuthAddressService extends IService<TronAuthAddress> {

    /**
     * 查询列表
     */
    List<TronAuthAddress> queryList(TronAuthAddress tronAuthAddress);

    /**
     * 查询代理名ID
     */
    String queryAgent(long deptId);
}
