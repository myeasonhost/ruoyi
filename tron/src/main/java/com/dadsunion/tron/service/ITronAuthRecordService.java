package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronAuthRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 授权记录Service接口
 *
 * @author eason
 * @date 2022-05-02
 */
public interface ITronAuthRecordService extends IService<TronAuthRecord> {

    /**
     * 查询列表
     */
    List<TronAuthRecord> queryList(TronAuthRecord tronAuthRecord);
}
