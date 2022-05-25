package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronWebConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 矿机设置Service接口
 *
 * @author eason
 * @date 2022-05-24
 */
public interface ITronWebConfigService extends IService<TronWebConfig> {

    /**
     * 查询列表
     */
    List<TronWebConfig> queryList(TronWebConfig tronWebConfig);
}
