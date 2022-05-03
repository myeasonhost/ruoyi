package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronInterestRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 利息Service接口
 *
 * @author eason
 * @date 2022-05-03
 */
public interface ITronInterestRecordService extends IService<TronInterestRecord> {

    /**
     * 查询列表
     */
    List<TronInterestRecord> queryList(TronInterestRecord tronInterestRecord);
}
