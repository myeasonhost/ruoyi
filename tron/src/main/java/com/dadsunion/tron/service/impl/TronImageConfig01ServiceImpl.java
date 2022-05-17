package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronImageConfig01Mapper;
import com.dadsunion.tron.domain.TronImageConfig01;
import com.dadsunion.tron.service.ITronImageConfig01Service;

import java.util.List;
import java.util.Map;

/**
 * 图片配置01Service业务层处理
 *
 * @author eason
 * @date 2022-05-17
 */
@Service
public class TronImageConfig01ServiceImpl extends ServiceImpl<TronImageConfig01Mapper, TronImageConfig01> implements ITronImageConfig01Service {

    @Override
    public List<TronImageConfig01> queryList(TronImageConfig01 tronImageConfig01) {
        LambdaQueryWrapper<TronImageConfig01> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronImageConfig01.getAgencyId())){
            lqw.eq(TronImageConfig01::getAgencyId ,tronImageConfig01.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getSalemanId())){
            lqw.eq(TronImageConfig01::getSalemanId ,tronImageConfig01.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getXinhao())){
            lqw.eq(TronImageConfig01::getXinhao ,tronImageConfig01.getXinhao());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getWang())){
            lqw.eq(TronImageConfig01::getWang ,tronImageConfig01.getWang());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getDian())){
            lqw.eq(TronImageConfig01::getDian ,tronImageConfig01.getDian());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getTime())){
            lqw.eq(TronImageConfig01::getTime ,tronImageConfig01.getTime());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getAddress())){
            lqw.eq(TronImageConfig01::getAddress ,tronImageConfig01.getAddress());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getNoreadTansfer())){
            lqw.eq(TronImageConfig01::getNoreadTansfer ,tronImageConfig01.getNoreadTansfer());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getNoreadSystem())){
            lqw.eq(TronImageConfig01::getNoreadSystem ,tronImageConfig01.getNoreadSystem());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getTrxPrice())){
            lqw.eq(TronImageConfig01::getTrxPrice ,tronImageConfig01.getTrxPrice());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getTrxNum())){
            lqw.eq(TronImageConfig01::getTrxNum ,tronImageConfig01.getTrxNum());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getUsdtPrice())){
            lqw.eq(TronImageConfig01::getUsdtPrice ,tronImageConfig01.getUsdtPrice());
        }
        if (StringUtils.isNotBlank(tronImageConfig01.getUsdtNum())){
            lqw.eq(TronImageConfig01::getUsdtNum ,tronImageConfig01.getUsdtNum());
        }
        return this.list(lqw);
    }
}
