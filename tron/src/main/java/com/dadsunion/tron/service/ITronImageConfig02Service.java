package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronImageConfig02;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 图片配置02Service接口
 *
 * @author eason
 * @date 2022-05-17
 */
public interface ITronImageConfig02Service extends IService<TronImageConfig02> {

    /**
     * 查询列表
     */
    List<TronImageConfig02> queryList(TronImageConfig02 tronImageConfig02);
}
