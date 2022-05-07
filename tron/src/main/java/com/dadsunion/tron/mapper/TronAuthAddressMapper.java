package com.dadsunion.tron.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dadsunion.tron.domain.TronAuthAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 授权Mapper接口
 *
 * @author eason
 * @date 2022-04-20
 */
public interface TronAuthAddressMapper extends BaseMapper<TronAuthAddress> {

    @Select({"${sql}"})
    String executeQuery(@Param("sql") String sql);
}
