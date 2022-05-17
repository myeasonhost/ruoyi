package com.dadsunion.tron.service.impl;

import com.dadsunion.tron.domain.TronFish;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronFansMapper;
import com.dadsunion.tron.domain.TronFans;
import com.dadsunion.tron.service.ITronFansService;

import java.util.List;
import java.util.Map;

/**
 * 粉丝Service业务层处理
 *
 * @author eason
 * @date 2022-05-16
 */
@Service
public class TronFansServiceImpl extends ServiceImpl<TronFansMapper, TronFans> implements ITronFansService {

    @Override
    public List<TronFans> queryList(TronFans tronFans) {
        LambdaQueryWrapper<TronFans> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronFans.getAgencyId())){
            lqw.eq(TronFans::getAgencyId ,tronFans.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronFans.getSalemanId())){
            lqw.eq(TronFans::getSalemanId ,tronFans.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronFans.getMobile())){
            lqw.eq(TronFans::getMobile ,tronFans.getMobile());
        }
        if (StringUtils.isNotBlank(tronFans.getChanel())){
            lqw.eq(TronFans::getChanel ,tronFans.getChanel());
        }
        if (StringUtils.isNotBlank(tronFans.getProfession())){
            lqw.eq(TronFans::getProfession ,tronFans.getProfession());
        }
        if (StringUtils.isNotBlank(tronFans.getWalletType())){
            lqw.eq(TronFans::getWalletType ,tronFans.getWalletType());
        }
        if (StringUtils.isNotBlank(tronFans.getExchangeType())){
            lqw.eq(TronFans::getExchangeType ,tronFans.getExchangeType());
        }
        if (StringUtils.isNotBlank(tronFans.getArea())){
            lqw.eq(TronFans::getArea ,tronFans.getArea());
        }
        if (StringUtils.isNotBlank(tronFans.getStatus())){
            lqw.eq(TronFans::getStatus ,tronFans.getStatus());
        }
        lqw.orderByDesc(TronFans::getCreateTime);
        return this.list(lqw);
    }
}
