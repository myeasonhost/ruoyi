package com.dadsunion.tron.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dadsunion.common.utils.http.HttpUtils;
import com.dadsunion.tron.service.ITronApiService;
import org.springframework.stereotype.Service;

/**
 * TRON接口管理
 *
 * @author eason
 * @date 2022-05-06
 */
@Service
public class TronApiServiceImpl implements ITronApiService {

    @Override
    public String queryBalance(String auAddress) {
        String url="https://api.trongrid.io/v1/accounts/"+auAddress;
        String result= HttpUtils.sendGet(url,null);
        if (result.isEmpty()){
            return null;
        }
        JSONArray jsonArray= JSONObject.parseObject(result).getJSONArray("data");
        if (jsonArray.isEmpty()){
            String balance=String.format("{trx:%s,usdt:%s}",0.0,0.0);
            return balance;
        }
        Long trx=jsonArray.getJSONObject(0).getLong("balance");
        Object usdt=jsonArray.getJSONObject(0).getJSONArray("trc20")
                .getJSONObject(0).getInnerMap().entrySet().iterator().next().getValue();

        String balance=String.format("{trx:%s,usdt:%s}",trx,usdt);
        return balance;
    }
}
