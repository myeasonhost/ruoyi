package com.dadsunion.tron.service.impl;

import com.dadsunion.tron.domain.TronAccountAddress;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronEasonAddressMapper;
import com.dadsunion.tron.domain.TronEasonAddress;
import com.dadsunion.tron.service.ITronEasonAddressService;

import java.util.List;

/**
 * 总站账户Service业务层处理
 *
 * @author eason
 * @date 2022-05-06
 */
@Service
public class TronEasonAddressServiceImpl extends ServiceImpl<TronEasonAddressMapper, TronEasonAddress> implements ITronEasonAddressService {

    @Override
    public List<TronEasonAddress> queryList(TronEasonAddress tronEasonAddress) {
        LambdaQueryWrapper<TronEasonAddress> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronEasonAddress.getAgencyId())){
            lqw.eq(TronEasonAddress::getAgencyId ,tronEasonAddress.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronEasonAddress.getAddressType())){
            lqw.eq(TronEasonAddress::getAddressType ,tronEasonAddress.getAddressType());
        }
        if (StringUtils.isNotBlank(tronEasonAddress.getAddress())){
            lqw.eq(TronEasonAddress::getAddress ,tronEasonAddress.getAddress());
        }
        if (StringUtils.isNotBlank(tronEasonAddress.getHexAddress())){
            lqw.eq(TronEasonAddress::getHexAddress ,tronEasonAddress.getHexAddress());
        }
        if (StringUtils.isNotBlank(tronEasonAddress.getPrivatekey())){
            lqw.eq(TronEasonAddress::getPrivatekey ,tronEasonAddress.getPrivatekey());
        }
        if (StringUtils.isNotBlank(tronEasonAddress.getBalance())){
            lqw.eq(TronEasonAddress::getBalance ,tronEasonAddress.getBalance());
        }
        lqw.select(TronEasonAddress.class, item -> !item.getColumn().equals("privateKey"));//私钥不对外开放
        lqw.orderByDesc(TronEasonAddress::getCreateTime);

        return this.list(lqw);
    }
}
