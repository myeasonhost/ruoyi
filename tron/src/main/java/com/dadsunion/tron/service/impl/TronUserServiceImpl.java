package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronUserMapper;
import com.dadsunion.tron.domain.TronUser;
import com.dadsunion.tron.service.ITronUserService;

import java.util.List;
import java.util.Map;

/**
 * 业务员Service业务层处理
 *
 * @author eason
 * @date 2022-04-24
 */
@Service
public class TronUserServiceImpl extends ServiceImpl<TronUserMapper, TronUser> implements ITronUserService {

    @Override
    public List<TronUser> queryList(TronUser tronUser) {
        LambdaQueryWrapper<TronUser> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronUser.getAgencyId())){
            lqw.eq(TronUser::getAgencyId ,tronUser.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronUser.getUsername())){
            lqw.like(TronUser::getUsername ,tronUser.getUsername());
        }
        if (StringUtils.isNotBlank(tronUser.getNicename())){
            lqw.like(TronUser::getNicename ,tronUser.getNicename());
        }
        if (StringUtils.isNotBlank(tronUser.getPassword())){
            lqw.eq(TronUser::getPassword ,tronUser.getPassword());
        }
        if (StringUtils.isNotBlank(tronUser.getLastIp())){
            lqw.eq(TronUser::getLastIp ,tronUser.getLastIp());
        }
        if (tronUser.getStatus() != null){
            lqw.eq(TronUser::getStatus ,tronUser.getStatus());
        }
        return this.list(lqw);
    }
}
