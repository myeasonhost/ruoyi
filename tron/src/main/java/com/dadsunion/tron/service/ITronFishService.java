package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronFish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 鱼苗管理Service接口
 *
 * @author eason
 * @date 2022-04-20
 */
public interface ITronFishService extends IService<TronFish> {

    /**
     * 查询列表
     */
    List<TronFish> queryList(TronFish tronFish);

    /**
     * 查询统计
     */
    Integer queryCount(TronFish tronFish);

    /**
     * 查询USDT
     */
    Long queryTotalUsdt(TronFish tronFish);
}
