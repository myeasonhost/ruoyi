package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronAccountAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 站内账号Service接口
 *
 * @author eason
 * @date 2022-05-05
 */
public interface ITronAccountAddressService extends IService<TronAccountAddress> {

    /**
     * 查询列表
     */
    List<TronAccountAddress> queryList(TronAccountAddress tronAccountAddress);
}
