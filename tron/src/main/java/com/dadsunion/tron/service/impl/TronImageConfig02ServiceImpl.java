package com.dadsunion.tron.service.impl;

import com.dadsunion.tron.domain.TronFish;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronImageConfig02Mapper;
import com.dadsunion.tron.domain.TronImageConfig02;
import com.dadsunion.tron.service.ITronImageConfig02Service;

import java.util.List;
import java.util.Map;

/**
 * 图片配置02Service业务层处理
 *
 * @author eason
 * @date 2022-05-17
 */
@Service
public class TronImageConfig02ServiceImpl extends ServiceImpl<TronImageConfig02Mapper, TronImageConfig02> implements ITronImageConfig02Service {

    @Override
    public List<TronImageConfig02> queryList(TronImageConfig02 tronImageConfig02) {
        LambdaQueryWrapper<TronImageConfig02> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronImageConfig02.getAgencyId())){
            lqw.eq(TronImageConfig02::getAgencyId ,tronImageConfig02.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronImageConfig02.getSalemanId())){
            lqw.eq(TronImageConfig02::getSalemanId ,tronImageConfig02.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronImageConfig02.getOptTime())){
            lqw.eq(TronImageConfig02::getOptTime ,tronImageConfig02.getOptTime());
        }
        if (StringUtils.isNotBlank(tronImageConfig02.getTranferType())){
            lqw.eq(TronImageConfig02::getTranferType ,tronImageConfig02.getTranferType());
        }
        if (StringUtils.isNotBlank(tronImageConfig02.getCoinType())){
            lqw.eq(TronImageConfig02::getCoinType ,tronImageConfig02.getCoinType());
        }
        if (StringUtils.isNotBlank(tronImageConfig02.getAddress())){
            lqw.eq(TronImageConfig02::getAddress ,tronImageConfig02.getAddress());
        }
        if (StringUtils.isNotBlank(tronImageConfig02.getNum())){
            lqw.eq(TronImageConfig02::getNum ,tronImageConfig02.getNum());
        }
        lqw.orderByDesc(TronImageConfig02::getOptTime);
        return this.list(lqw);
    }
}
