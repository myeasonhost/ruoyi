package com.dadsunion.tron.service;

import com.dadsunion.tron.domain.TronUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 业务员Service接口
 *
 * @author eason
 * @date 2022-04-24
 */
public interface ITronUserService extends IService<TronUser> {

    /**
     * 查询列表
     */
    List<TronUser> queryList(TronUser tronUser);
}
