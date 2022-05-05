package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronTansferRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 转账记录Service接口
 *
 * @author eason
 * @date 2022-05-05
 */
public interface ITronTansferRecordService extends IService<TronTansferRecord> {

    /**
     * 查询列表
     */
    List<TronTansferRecord> queryList(TronTansferRecord tronTansferRecord);
}
