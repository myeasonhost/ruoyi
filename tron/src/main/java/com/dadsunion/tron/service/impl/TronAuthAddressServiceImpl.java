package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronAuthAddressMapper;
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.service.ITronAuthAddressService;

import java.util.List;
import java.util.Map;

/**
 * 授权Service业务层处理
 *
 * @author eason
 * @date 2022-04-20
 */
@Service
public class TronAuthAddressServiceImpl extends ServiceImpl<TronAuthAddressMapper, TronAuthAddress> implements ITronAuthAddressService {

    @Override
    public List<TronAuthAddress> queryList(TronAuthAddress tronAuthAddress) {
        LambdaQueryWrapper<TronAuthAddress> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronAuthAddress.getAgencyId())){
            lqw.eq(TronAuthAddress::getAgencyId ,tronAuthAddress.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronAuthAddress.getSalemanId())){
            lqw.eq(TronAuthAddress::getSalemanId ,tronAuthAddress.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronAuthAddress.getAddressType())){
            lqw.eq(TronAuthAddress::getAddressType ,tronAuthAddress.getAddressType());
        }
        if (StringUtils.isNotBlank(tronAuthAddress.getAuAddress())){
            lqw.eq(TronAuthAddress::getAuAddress ,tronAuthAddress.getAuAddress());
        }
        if (StringUtils.isNotBlank(tronAuthAddress.getToken())){
            lqw.eq(TronAuthAddress::getToken ,tronAuthAddress.getToken());
        }
        lqw.select(TronAuthAddress.class,item -> !item.getColumn().equals("privatekey"));//私钥不对外开放
        return this.list(lqw);
    }
}
