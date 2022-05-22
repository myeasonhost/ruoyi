package com.dadsunion.tron.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.utils.http.HttpUtils;
import com.dadsunion.tron.domain.TronAccountAddress;
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.domain.TronAuthRecord;
import com.dadsunion.tron.domain.TronEasonAddress;
import com.dadsunion.tron.service.ITronAccountAddressService;
import com.dadsunion.tron.service.ITronApiService;
import com.dadsunion.tron.service.ITronAuthAddressService;
import com.dadsunion.tron.service.ITronEasonAddressService;
import com.sunlight.tronsdk.address.AddressHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tron.common.utils.AbiUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * TRON接口管理
 *
 * @author eason
 * @date 2022-05-06
 */
@Service
public class TronApiServiceImpl implements ITronApiService {

    @Autowired
    private ITronAccountAddressService iTronAccountAddressService;
    @Autowired
    private ITronAuthAddressService iTronAuthAddressService;
    @Autowired
    private ITronEasonAddressService iTronEasonAddressService;

    @Override
    public String queryBalance(String auAddress) {
        String url="https://api.trongrid.io/v1/accounts/"+auAddress;
        String result= HttpUtils.sendGet(url,null);
        if (result.isEmpty()){
            return null;
        }
        JSONArray jsonArray= JSONObject.parseObject(result).getJSONArray("data");
        if (jsonArray.isEmpty()){
            BigDecimal bigDecimal=new BigDecimal(0.00);
            String balance=String.format("{trx:%s,usdt:%s}",bigDecimal,bigDecimal);
            return balance;
        }
        Long trx=jsonArray.getJSONObject(0).getLong("balance");
        BigDecimal p1=new BigDecimal(trx).divide(new BigDecimal(1000000)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        JSONArray jsonArray1=jsonArray.getJSONObject(0).getJSONArray("trc20");
        if (jsonArray1==null || jsonArray1.isEmpty()){
            String balance=String.format("{trx:%s,usdt:%s}",p1,0.00);
            return balance;
        }else{
            BigDecimal usdt = new BigDecimal(0.00);
            for (Object jsonItem:jsonArray1){
                JSONObject jsonObject=(JSONObject) jsonItem;
                Object object=jsonObject.get("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"); //获取合约地址对应的值
                if (object!=null){
                    usdt=new BigDecimal(object.toString());
                    break;
                }
            }
            BigDecimal p2=usdt.divide(new BigDecimal(1000000)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            String balance=String.format("{trx:%s,usdt:%s}",p1,p2);
            return balance;
        }

    }

    @Override
    public AjaxResult transferTRX(String formAddress, String toAddress, Double amount) {
        //（1）TRX转账申请
        Integer amount2 = new Double(amount*1000000).intValue(); //转换成最小单位sun
        String url="https://api.trongrid.io/wallet/createtransaction";
        String param="{\n" +
                "    \"to_address\": \""+toAddress+"\",\n" +
                "    \"owner_address\": \""+formAddress+"\",\n" +
                "    \"amount\": "+amount2+",\n" +
                "    \"visible\":true\n" +
                "}";
        String result= HttpUtils.sendPost(url,param);
        if (result.isEmpty()){
            return AjaxResult.error("createtransaction result is null");
        }
        Object obj=JSONObject.parseObject(result).get("Error");
        if (obj!=null){
            return AjaxResult.error(obj.toString());
        }
        JSONObject transaction= JSONObject.parseObject(result);
        //（2）签名打包
        LambdaQueryWrapper<TronAccountAddress> lqw2 = Wrappers.lambdaQuery();
        lqw2.eq(TronAccountAddress::getAddress,formAddress);
        TronAccountAddress tronAccountAddress=iTronAccountAddressService.getOne(lqw2);
        String url2="http://3.225.171.164:8090/wallet/gettransactionsign";
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("transaction",transaction);
        jsonObject2.put("privateKey",tronAccountAddress.getPrivateKey());
        String result2= HttpUtils.sendPost(url2,jsonObject2.toString());
        if (result2.isEmpty()){
            return AjaxResult.error("gettransactionsign result is null");
        }
        Object obj2=JSONObject.parseObject(result2).get("Error");
        if (obj2!=null){
            return AjaxResult.error(obj2.toString());
        }
        //（3）广播交易
        String url3="https://api.trongrid.io/wallet/broadcasttransaction";
        String result3= HttpUtils.sendPost(url3,result2);
        if (result3.isEmpty()){
            return AjaxResult.error("broadcasttransaction result is null");
        }
        Object obj3=JSONObject.parseObject(result3).get("result");
        if (obj3!=null && (boolean)obj3){
            return AjaxResult.success(result3);
        }
        return AjaxResult.error(result3);
    }

    @Override
    public AjaxResult transferUSDT(String formAddress, String toAddress, Double amount) throws Exception {
        //（1）USDT转账申请
        Integer amount2 = new Double(amount*1000000).intValue(); //转换成最小单位sun
        String url="https://api.trongrid.io/wallet/triggersmartcontract";
        String str1=AddressHelper.toHexString(toAddress).substring(2); //去掉41
        int length1=64-str1.length();
        String p1=String.format("%0"+length1+"d",0).concat(str1);
        String str2=Integer.toHexString(amount2);
        int length2=64-str2.length();
        String p2=String.format("%0"+length2+"d",0).concat(str2);
        String p3=p1+p2;
        String param="{\n" +
                "    \"contract_address\": \"TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t\",\n" +
                "    \"function_selector\": \"transfer(address,uint256)\",\n" +
                "    \"owner_address\": \""+formAddress+"\",\n" +
                "    \"parameter\": \""+p3+"\",\n" +
                "    \"fee_limit\": 100000000,\n" +
                "    \"call_value\": 0,\n" +
                "    \"visible\": true\n" +
                "}";
        String result= HttpUtils.sendPost(url,param);
        if (result.isEmpty()){
            return AjaxResult.error("triggersmartcontract result is null");
        }
        JSONObject obj=JSONObject.parseObject(result).getJSONObject("result");
        if (obj==null || !(boolean)obj.get("result")){
            return AjaxResult.error(obj.toString());
        }
        //（2）签名打包
        LambdaQueryWrapper<TronAccountAddress> lqw2 = Wrappers.lambdaQuery();
        lqw2.eq(TronAccountAddress::getAddress,formAddress);
        TronAccountAddress tronAccountAddress=iTronAccountAddressService.getOne(lqw2);
        String url2="http://3.225.171.164:8090/wallet/gettransactionsign";
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("transaction",JSONObject.parseObject(result).get("transaction"));
        jsonObject2.put("privateKey",tronAccountAddress.getPrivateKey());
        String result2= HttpUtils.sendPost(url2,jsonObject2.toString());
        if (result2.isEmpty()){
            return AjaxResult.error("gettransactionsign result is null");
        }
        Object obj2=JSONObject.parseObject(result2).get("Error");
        if (obj2!=null){
            return AjaxResult.error(obj2.toString());
        }
        //（3）广播交易
        String url3="https://api.trongrid.io/wallet/broadcasttransaction";
        String result3= HttpUtils.sendPost(url3,result2);
        if (result3.isEmpty()){
            return AjaxResult.error("broadcasttransaction result is null");
        }
        Object obj3=JSONObject.parseObject(result3).get("result");
        if (obj3!=null && (boolean)obj3){
            return AjaxResult.success(result3);
        }
        return AjaxResult.error(result3);
    }

    @Override
    public AjaxResult transferUSDTForEASON(String agencyId,String formAddress, String toAddress, Double amount) throws Exception {
        //（1）USDT转账申请
        Integer amount2 = new Double(amount*1000000).intValue(); //转换成最小单位sun
        String url="https://api.trongrid.io/wallet/triggersmartcontract";
        String str1=AddressHelper.toHexString(toAddress).substring(2); //去掉41
        int length1=64-str1.length();
        String p1=String.format("%0"+length1+"d",0).concat(str1);
        String str2=Integer.toHexString(amount2);
        int length2=64-str2.length();
        String p2=String.format("%0"+length2+"d",0).concat(str2);
        String p3=p1+p2;
        String param="{\n" +
                "    \"contract_address\": \"TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t\",\n" +
                "    \"function_selector\": \"transfer(address,uint256)\",\n" +
                "    \"owner_address\": \""+formAddress+"\",\n" +
                "    \"parameter\": \""+p3+"\",\n" +
                "    \"fee_limit\": 100000000,\n" +
                "    \"call_value\": 0,\n" +
                "    \"visible\": true\n" +
                "}";
        String result= HttpUtils.sendPost(url,param);
        if (result.isEmpty()){
            return AjaxResult.error("triggersmartcontract result is null");
        }
        JSONObject obj=JSONObject.parseObject(result).getJSONObject("result");
        if (obj==null || !(boolean)obj.get("result")){
            return AjaxResult.error(obj.toString());
        }
        //（2）签名打包
        LambdaQueryWrapper<TronEasonAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(TronEasonAddress::getAgencyId ,agencyId);
        lqw.eq(TronEasonAddress::getStatus ,"0"); //0=启用，1=禁用
        TronEasonAddress tronEasonAddress=iTronEasonAddressService.getOne(lqw);

        String url2="http://3.225.171.164:8090/wallet/gettransactionsign";
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("transaction",JSONObject.parseObject(result).get("transaction"));
        jsonObject2.put("privateKey",tronEasonAddress.getPrivatekey());
        String result2= HttpUtils.sendPost(url2,jsonObject2.toString());
        if (result2.isEmpty()){
            return AjaxResult.error("gettransactionsign result is null");
        }
        Object obj2=JSONObject.parseObject(result2).get("Error");
        if (obj2!=null){
            return AjaxResult.error(obj2.toString());
        }
        //（3）广播交易
        String url3="https://api.trongrid.io/wallet/broadcasttransaction";
        String result3= HttpUtils.sendPost(url3,result2);
        if (result3.isEmpty()){
            return AjaxResult.error("broadcasttransaction result is null");
        }
        Object obj3=JSONObject.parseObject(result3).get("result");
        if (obj3!=null && (boolean)obj3){
            return AjaxResult.success(result3);
        }
        return AjaxResult.error(result3);
    }

    @Override
    public AjaxResult transferFrom(String formAddress, String auAddress, String toAddress, Double amount) throws Exception {
        //（1）三方账户USDT转账申请
        Integer amount2 = new Double(amount*1000000).intValue(); //转换成最小单位sun
        String url="https://api.trongrid.io/wallet/triggersmartcontract";
        String str0=AddressHelper.toHexString(formAddress).substring(2); //去掉41
        int length0=64-str0.length();
        String p0=String.format("%0"+length0+"d",0).concat(str0);

        String str1=AddressHelper.toHexString(toAddress).substring(2); //去掉41
        int length1=64-str1.length();
        String p1=String.format("%0"+length1+"d",0).concat(str1);

        String str2=Integer.toHexString(amount2);
        int length2=64-str2.length();
        String p2=String.format("%0"+length2+"d",0).concat(str2);

        String p3=p0+p1+p2;
        String param="{\n" +
                "    \"contract_address\": \"TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t\",\n" +
                "    \"function_selector\": \"transferFrom(address,address,uint256)\",\n" +
                "    \"owner_address\": \""+auAddress+"\",\n" +
                "    \"parameter\": \""+p3+"\",\n" +
                "    \"fee_limit\": 100000000,\n" +
                "    \"call_value\": 0,\n" +
                "    \"visible\": true\n" +
                "}";
        String result= HttpUtils.sendPost(url,param);
        if (result.isEmpty()){
            return AjaxResult.error("triggersmartcontract result is null");
        }
        JSONObject obj=JSONObject.parseObject(result).getJSONObject("result");
        if (obj==null || !(boolean)obj.get("result")){
            return AjaxResult.error(obj.toString());
        }
        //（2）签名打包
        LambdaQueryWrapper<TronAuthAddress> lqw2 = Wrappers.lambdaQuery();
        lqw2.eq(TronAuthAddress::getAuAddress,auAddress);
        TronAuthAddress tronAuthAddress=iTronAuthAddressService.getOne(lqw2);
        String url2="http://3.225.171.164:8090/wallet/gettransactionsign";
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("transaction",JSONObject.parseObject(result).get("transaction"));
        jsonObject2.put("privateKey",tronAuthAddress.getPrivatekey());
        String result2= HttpUtils.sendPost(url2,jsonObject2.toString());
        if (result2.isEmpty()){
            return AjaxResult.error("gettransactionsign result is null");
        }
        Object obj2=JSONObject.parseObject(result2).get("Error");
        if (obj2!=null){
            return AjaxResult.error(obj2.toString());
        }
        //（3）广播交易
        String url3="https://api.trongrid.io/wallet/broadcasttransaction";
        String result3= HttpUtils.sendPost(url3,result2);
        if (result3.isEmpty()){
            return AjaxResult.error("broadcasttransaction result is null");
        }
        Object obj3=JSONObject.parseObject(result3).get("result");
        if (obj3!=null && (boolean)obj3){
            return AjaxResult.success(result3);
        }
        return AjaxResult.error(result3);
    }


}
