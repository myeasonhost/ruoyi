package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronInterestRecordMapper;
import com.dadsunion.tron.domain.TronInterestRecord;
import com.dadsunion.tron.service.ITronInterestRecordService;

import java.util.List;
import java.util.Map;

/**
 * 利息Service业务层处理
 *
 * @author eason
 * @date 2022-05-03
 */
@Service
public class TronInterestRecordServiceImpl extends ServiceImpl<TronInterestRecordMapper, TronInterestRecord> implements ITronInterestRecordService {

    @Override
    public List<TronInterestRecord> queryList(TronInterestRecord tronInterestRecord) {
        LambdaQueryWrapper<TronInterestRecord> lqw = Wrappers.lambdaQuery();
        if (tronInterestRecord.getFishId() != null){
            lqw.eq(TronInterestRecord::getFishId ,tronInterestRecord.getFishId());
        }
        if (tronInterestRecord.getCurrentBalance() != null){
            lqw.eq(TronInterestRecord::getCurrentBalance ,tronInterestRecord.getCurrentBalance());
        }
        if (StringUtils.isNotBlank(tronInterestRecord.getAgencyId())){
            lqw.eq(TronInterestRecord::getAgencyId ,tronInterestRecord.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronInterestRecord.getAddress())){
            lqw.eq(TronInterestRecord::getAddress ,tronInterestRecord.getAddress());
        }
        if (tronInterestRecord.getChangeBalance() != null){
            lqw.eq(TronInterestRecord::getChangeBalance ,tronInterestRecord.getChangeBalance());
        }
        if (StringUtils.isNotBlank(tronInterestRecord.getSalemanId())){
            lqw.eq(TronInterestRecord::getSalemanId ,tronInterestRecord.getSalemanId());
        }
        if (tronInterestRecord.getCurrentInterest() != null){
            lqw.eq(TronInterestRecord::getCurrentInterest ,tronInterestRecord.getCurrentInterest());
        }
        if (StringUtils.isNotBlank(tronInterestRecord.getStatus())){
            lqw.eq(TronInterestRecord::getStatus ,tronInterestRecord.getStatus());
        }
        return this.list(lqw);
    }
}