package com.dadsunion.tron.service.impl;

import com.dadsunion.tron.domain.TronAuthAddress;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronAccountAddressMapper;
import com.dadsunion.tron.domain.TronAccountAddress;
import com.dadsunion.tron.service.ITronAccountAddressService;

import java.util.List;
import java.util.Map;

/**
 * 站内账号Service业务层处理
 *
 * @author eason
 * @date 2022-05-05
 */
@Service
public class TronAccountAddressServiceImpl extends ServiceImpl<TronAccountAddressMapper, TronAccountAddress> implements ITronAccountAddressService {

    @Override
    public List<TronAccountAddress> queryList(TronAccountAddress tronAccountAddress) {
        LambdaQueryWrapper<TronAccountAddress> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronAccountAddress.getAgencyId())){
            lqw.eq(TronAccountAddress::getAgencyId ,tronAccountAddress.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronAccountAddress.getAddressType())){
            lqw.eq(TronAccountAddress::getAddressType ,tronAccountAddress.getAddressType());
        }
        if (StringUtils.isNotBlank(tronAccountAddress.getAddress())){
            lqw.eq(TronAccountAddress::getAddress ,tronAccountAddress.getAddress());
        }
        if (StringUtils.isNotBlank(tronAccountAddress.getHexAddress())){
            lqw.eq(TronAccountAddress::getHexAddress ,tronAccountAddress.getHexAddress());
        }
        if (StringUtils.isNotBlank(tronAccountAddress.getPrivateKey())){
            lqw.eq(TronAccountAddress::getPrivateKey ,tronAccountAddress.getPrivateKey());
        }
        if (StringUtils.isNotBlank(tronAccountAddress.getBalance())){
            lqw.eq(TronAccountAddress::getBalance ,tronAccountAddress.getBalance());
        }
        lqw.select(TronAccountAddress.class, item -> !item.getColumn().equals("privatekey"));//私钥不对外开放

        return this.list(lqw);
    }
}
