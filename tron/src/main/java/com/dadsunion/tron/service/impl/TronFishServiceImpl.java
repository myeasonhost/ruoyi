package com.dadsunion.tron.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dadsunion.tron.domain.TronFish;
import com.dadsunion.tron.mapper.TronFishMapper;
import com.dadsunion.tron.service.ITronFishService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        lqw.orderByDesc(TronFish::getIsTop);
        lqw.orderByDesc(TronFish::getCreateTime);

        return this.list(lqw);
    }

    @Override
    public Integer queryCount(TronFish tronFish) throws ParseException {
        LambdaQueryWrapper<TronFish> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronFish.getAgencyId())){
            lqw.eq(TronFish::getAgencyId ,tronFish.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronFish.getSalemanId())){
            lqw.eq(TronFish::getSalemanId ,tronFish.getSalemanId());
        }
        if (tronFish.getCreateTime()!=null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            tronFish.setCreateTime(sdf.parse(sdf.format(tronFish.getCreateTime())));
            lqw.ge(TronFish::getCreateTime ,tronFish.getCreateTime()); //ne	不等于<>   gt大于> ge大于等于>= lt小于< le小于等于<=
        }
        lqw.orderByDesc(TronFish::getCreateTime);

        return this.count(lqw);
    }

    @Override
    public Map<String,Object> queryTotalUsdt(TronFish tronFish) {
        QueryWrapper<TronFish> queryWrapper = Wrappers.query();
        queryWrapper.select("IFNULL(SUM(balance->'$.usdt'),0) as usdt,IFNULL(SUM(balance->'$.billusdt'),0) as billusdt");
        if (StringUtils.isNotBlank(tronFish.getAgencyId())){
            queryWrapper.eq("agency_id" ,tronFish.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronFish.getSalemanId())){
            queryWrapper.eq("saleman_id" ,tronFish.getSalemanId());
        }
        return this.getMap(queryWrapper);
    }
}
