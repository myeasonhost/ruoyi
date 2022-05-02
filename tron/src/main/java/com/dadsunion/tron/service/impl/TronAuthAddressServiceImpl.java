package com.dadsunion.tron.service.impl;

import com.dadsunion.tron.domain.TronAuthRecord;
import com.dadsunion.tron.service.ITronAuthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

/**
 * 授权Service业务层处理
 *
 * @author eason
 * @date 2022-04-20
 */
@Service
public class TronAuthAddressServiceImpl extends ServiceImpl<TronAuthAddressMapper, TronAuthAddress> implements ITronAuthAddressService {

    @Autowired
    private ITronAuthRecordService iTronAuthRecordService;

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

        List<TronAuthAddress> list= this.list(lqw).stream().map(tronAuthAddress1 -> {
            LambdaQueryWrapper<TronAuthRecord> lqw2 = Wrappers.lambdaQuery();
            lqw2.eq(TronAuthRecord::getAuAddress,tronAuthAddress1.getAuAddress());
            tronAuthAddress1.setAuNum(iTronAuthRecordService.count(lqw2));
            return tronAuthAddress1;
        }).collect(Collectors.toList());
        return list;
    }
}
