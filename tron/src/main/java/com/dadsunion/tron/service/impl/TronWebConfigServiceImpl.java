package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronWebConfigMapper;
import com.dadsunion.tron.domain.TronWebConfig;
import com.dadsunion.tron.service.ITronWebConfigService;

import java.util.List;

/**
 * 矿机设置Service业务层处理
 *
 * @author eason
 * @date 2022-05-24
 */
@Service
public class TronWebConfigServiceImpl extends ServiceImpl<TronWebConfigMapper, TronWebConfig> implements ITronWebConfigService {

    @Override
    public List<TronWebConfig> queryList(TronWebConfig tronWebConfig) {
        LambdaQueryWrapper<TronWebConfig> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronWebConfig.getAgencyId())){
            lqw.eq(TronWebConfig::getAgencyId ,tronWebConfig.getAgencyId());
        }
        return this.list(lqw);
    }
}
