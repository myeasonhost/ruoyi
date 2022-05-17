package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronFans;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 粉丝Service接口
 *
 * @author eason
 * @date 2022-05-16
 */
public interface ITronFansService extends IService<TronFans> {

    /**
     * 查询列表
     */
    List<TronFans> queryList(TronFans tronFans);
}
