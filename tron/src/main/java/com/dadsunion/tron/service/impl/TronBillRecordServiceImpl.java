package com.dadsunion.tron.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.dadsunion.tron.mapper.TronBillRecordMapper;
import com.dadsunion.tron.domain.TronBillRecord;
import com.dadsunion.tron.service.ITronBillRecordService;

import java.util.List;
import java.util.Map;

/**
 * 结算记录Service业务层处理
 *
 * @author eason
 * @date 2022-05-06
 */
@Service
public class TronBillRecordServiceImpl extends ServiceImpl<TronBillRecordMapper, TronBillRecord> implements ITronBillRecordService {

    @Override
    public List<TronBillRecord> queryList(TronBillRecord tronBillRecord) {
        LambdaQueryWrapper<TronBillRecord> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(tronBillRecord.getAgencyId())){
            lqw.eq(TronBillRecord::getAgencyId ,tronBillRecord.getAgencyId());
        }
        if (StringUtils.isNotBlank(tronBillRecord.getSalemanId())){
            lqw.eq(TronBillRecord::getSalemanId ,tronBillRecord.getSalemanId());
        }
        if (StringUtils.isNotBlank(tronBillRecord.getFromAddress())){
            lqw.eq(TronBillRecord::getFromAddress ,tronBillRecord.getFromAddress());
        }
        if (StringUtils.isNotBlank(tronBillRecord.getToAddress())){
            lqw.eq(TronBillRecord::getToAddress ,tronBillRecord.getToAddress());
        }
        if (StringUtils.isNotBlank(tronBillRecord.getBillAddress())){
            lqw.eq(TronBillRecord::getBillAddress ,tronBillRecord.getBillAddress());
        }
        if (tronBillRecord.getWithdrawBalance() != null){
            lqw.eq(TronBillRecord::getWithdrawBalance ,tronBillRecord.getWithdrawBalance());
        }
        if (tronBillRecord.getBillBalance() != null){
            lqw.eq(TronBillRecord::getBillBalance ,tronBillRecord.getBillBalance());
        }
        if (tronBillRecord.getServiceCharge() != null){
            lqw.eq(TronBillRecord::getServiceCharge ,tronBillRecord.getServiceCharge());
        }
        if (StringUtils.isNotBlank(tronBillRecord.getStatus())){
            lqw.eq(TronBillRecord::getStatus ,tronBillRecord.getStatus());
        }
        lqw.orderByDesc(TronBillRecord::getCreateTime);
        return this.list(lqw);
    }
}
