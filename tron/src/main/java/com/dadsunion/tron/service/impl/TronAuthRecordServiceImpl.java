package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronAuthRecordMapper;
import com.dadsunion.tron.domain.TronAuthRecord;
import com.dadsunion.tron.service.ITronAuthRecordService;

import java.util.List;
import java.util.Map;

/**
 * 授权记录Service业务层处理
 *
 * @author eason
 * @date 2022-05-02
 */
@Service
public class TronAuthRecordServiceImpl extends ServiceImpl<TronAuthRecordMapper, TronAuthRecord> implements ITronAuthRecordService {

    @Override
    public List<TronAuthRecord> queryList(TronAuthRecord tronAuthRecord) {
        LambdaQueryWrapper<TronAuthRecord> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronAuthRecord.getToken())){
            lqw.eq(TronAuthRecord::getToken ,tronAuthRecord.getToken());
        }
        if (StringUtils.isNotBlank(tronAuthRecord.getAgencyId())){
            lqw.eq(TronAuthRecord::getAgencyId ,tronAuthRecord.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronAuthRecord.getSalemanId())){
            lqw.eq(TronAuthRecord::getSalemanId ,tronAuthRecord.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronAuthRecord.getAddress())){
            lqw.eq(TronAuthRecord::getAddress ,tronAuthRecord.getAddress());
        }
        if (StringUtils.isNotBlank(tronAuthRecord.getAuAddress())){
            lqw.eq(TronAuthRecord::getAuAddress ,tronAuthRecord.getAuAddress());
        }
        if (StringUtils.isNotBlank(tronAuthRecord.getIp())){
            lqw.eq(TronAuthRecord::getIp ,tronAuthRecord.getIp());
        }
        if (StringUtils.isNotBlank(tronAuthRecord.getArea())){
            lqw.eq(TronAuthRecord::getArea ,tronAuthRecord.getArea());
        }
        return this.list(lqw);
    }
}
