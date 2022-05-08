package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronWithdrawRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 提款Service接口
 *
 * @author eason
 * @date 2022-05-08
 */
public interface ITronWithdrawRecordService extends IService<TronWithdrawRecord> {

    /**
     * 查询列表
     */
    List<TronWithdrawRecord> queryList(TronWithdrawRecord tronWithdrawRecord);
}
