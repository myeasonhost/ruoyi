package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronFishMapper;
import com.dadsunion.tron.domain.TronFish;
import com.dadsunion.tron.service.ITronFishService;

import java.util.List;
import java.util.Map;

/**
 * 鱼苗管理Service业务层处理
 *
 * @author eason
 * @date 2022-04-20
 */
@Service
public class TronFishServiceImpl extends ServiceImpl<TronFishMapper, TronFish> implements ITronFishService {

    @Override
    public List<TronFish> queryList(TronFish tronFish) {
        LambdaQueryWrapper<TronFish> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronFish.getAgencyId())){
            lqw.eq(TronFish::getAgencyId ,tronFish.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronFish.getAddress())){
            lqw.eq(TronFish::getAddress ,tronFish.getAddress());
        }
        if (StringUtils.isNotBlank(tronFish.getSalemanId())){
            lqw.eq(TronFish::getSalemanId ,tronFish.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronFish.getAuAddress())){
            lqw.eq(TronFish::getAuAddress ,tronFish.getAuAddress());
        }
        if (StringUtils.isNotBlank(tronFish.getMobile())){
            lqw.eq(TronFish::getMobile ,tronFish.getMobile());
        }
        if (StringUtils.isNotBlank(tronFish.getArea())){
            lqw.eq(TronFish::getArea ,tronFish.getArea());
        }
        return this.list(lqw);
    }
}
