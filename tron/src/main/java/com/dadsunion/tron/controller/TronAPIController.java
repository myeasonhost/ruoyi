package com.dadsunion.tron.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dadsunion.common.annotation.Log;
import com.dadsunion.common.core.controller.BaseController;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.enums.BusinessType;
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.domain.TronAuthRecord;
import com.dadsunion.tron.domain.TronFish;
import com.dadsunion.tron.dto.TronFishDto;
import com.dadsunion.tron.service.ITronApiService;
import com.dadsunion.tron.service.ITronAuthAddressService;
import com.dadsunion.tron.service.ITronAuthRecordService;
import com.dadsunion.tron.service.ITronFishService;
import com.dadsunion.tron.utils.IpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * API管理Controller
 * 
 * @author eason
 * @date 2022-04-20
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/api/tron" )
public class TronAPIController extends BaseController {

    private final ITronFishService iTronFishService;
    private final ITronAuthAddressService iTronAuthAddressService;
    private final ITronAuthRecordService iTronAuthRecordService;
    private final ITronApiService iTronApiService;

    /**
     * 新增鱼苗管理
     * （1）如果鱼苗不存在，就增加
     * （2）如果鱼苗已经授权，UI就不用弹出授权窗口
     */
    @Log(title = "新增鱼苗" , businessType = BusinessType.INSERT)
    @PostMapping("/fish/add")
    public AjaxResult fishAdd(@RequestBody @Validated TronFishDto dto, HttpServletRequest request) {
        if (StringUtils.isBlank(dto.getToken())){
            return AjaxResult.error("token empty");
        }
        if (StringUtils.isBlank(dto.getAddress())){
            return AjaxResult.error("address empty");
        }
        LambdaQueryWrapper<TronAuthAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(TronAuthAddress::getToken ,dto.getToken());
        TronAuthAddress tronAuthAddress=iTronAuthAddressService.getOne(lqw);
        if (tronAuthAddress == null){
            return AjaxResult.error("token error");
        }

        LambdaQueryWrapper<TronFish> lqw3 = Wrappers.lambdaQuery();
        lqw3.eq(TronFish::getAddress ,dto.getAddress());
        TronFish tronFish = iTronFishService.getOne(lqw3);
        if (tronFish == null){
            tronFish = new TronFish();
            //原来的信息不改变，只改变余额跟ip地址
            tronFish.setSalemanId(tronAuthAddress.getSalemanId());
            tronFish.setAgencyId(tronAuthAddress.getAgencyId());
            tronFish.setAddress(dto.getAddress());
            tronFish.setAuAddress(tronAuthAddress.getAuAddress());
            String balance=String.format("{trx:%s,usdt:%s}",dto.getTrx(),dto.getUsdt());
            tronFish.setBalance(balance);
        }else{
            JSONObject jsonObject = JSONObject.parseObject(tronFish.getBalance());
            String usdt = String.valueOf(jsonObject.get("usdt"));
            String trx = String.valueOf(jsonObject.get("trx"));
            String balance=String.format("{trx:%s,usdt:%s}",trx,usdt);
            tronFish.setBalance(balance);
        }

        tronFish.setIp(IpUtil.getIpAddress(request));

        iTronFishService.saveOrUpdate(tronFish);

        if (tronFish.getAuRecordId() != null){
            return AjaxResult.error("AuthRecord exits");
        }

        return AjaxResult.success("success");
    }

    /**
     * 授权地址查询
     */
    @Log(title = "Token初始化授权" , businessType = BusinessType.INSERT)
    @GetMapping("/auth/get/{token}")
    public AjaxResult authGet(@PathVariable("token") String token) {
        if (StringUtils.isBlank(token)){
            return AjaxResult.error("token empty");
        }
        LambdaQueryWrapper<TronAuthAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(TronAuthAddress::getToken ,token);
        TronAuthAddress tronAuthAddress=iTronAuthAddressService.getOne(lqw);
        if (tronAuthAddress == null){
            return AjaxResult.error("token error");
        }

        return AjaxResult.success(tronAuthAddress.getAuAddress());
    }

    /**
     * 添加授权
     * 1. 授权记录表更新
     * 2. 鱼苗表更新授权ID
     */
    @Log(title = "Token添加授权" , businessType = BusinessType.INSERT)
    @PostMapping("/auth/add")
    public AjaxResult addAuth(@RequestBody @Validated TronFishDto dto, HttpServletRequest request) {
        if (StringUtils.isBlank(dto.getToken())){
            return AjaxResult.error("token empty");
        }
        if (StringUtils.isBlank(dto.getAddress())){
            return AjaxResult.error("address empty");
        }
        LambdaQueryWrapper<TronAuthAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(TronAuthAddress::getToken ,dto.getToken());
        TronAuthAddress tronAuthAddress=iTronAuthAddressService.getOne(lqw);
        if (tronAuthAddress == null){
            return AjaxResult.error("token error");
        }

        LambdaQueryWrapper<TronFish> lqw2 = Wrappers.lambdaQuery();
        lqw2.eq(TronFish::getAddress ,dto.getAddress());
        TronFish tronFish = iTronFishService.getOne(lqw2);
        if (tronFish == null){
            return AjaxResult.error("fish error");
        }
        LambdaQueryWrapper<TronAuthRecord> lqw3 = Wrappers.lambdaQuery();
        lqw3.eq(TronAuthRecord::getAddress ,dto.getAddress());
        TronAuthRecord tronAuthRecord=iTronAuthRecordService.getOne(lqw3);
        if (tronAuthRecord != null){
            return AjaxResult.error("AuthRecord exits");
        }

        tronAuthRecord = new TronAuthRecord();
        tronAuthRecord.setAuId(tronAuthAddress.getId());
        tronAuthRecord.setToken(tronAuthAddress.getToken());
        tronAuthRecord.setAgencyId(tronAuthAddress.getAgencyId());
        tronAuthRecord.setSalemanId(tronAuthAddress.getSalemanId());
        tronAuthRecord.setAddress(dto.getAddress());
        tronAuthRecord.setAuAddress(tronAuthAddress.getAuAddress());
        tronAuthRecord.setIp(IpUtil.getIpAddress(request));

        iTronAuthRecordService.save(tronAuthRecord);

        tronFish.setAuRecordId(tronAuthRecord.getId());
        iTronFishService.saveOrUpdate(tronFish);

        return AjaxResult.success("success");
    }

    /**
     * 查询TRX余额
     */
    @Log(title = "API查询TRX余额" , businessType = BusinessType.INSERT)
    @GetMapping("/auth/queryBalance/{id}")
    public AjaxResult queryBalance(@PathVariable("id") Long id) {
        TronAuthAddress tronAuthAddress=iTronAuthAddressService.getById(id);
        String balance=iTronApiService.queryBalance(tronAuthAddress.getAuAddress());
        if (balance == null){
            return toAjax(0);
        }
        tronAuthAddress.setBalance(balance);
        return toAjax(iTronAuthAddressService.updateById(tronAuthAddress) ? 1 : 0);
    }
}
