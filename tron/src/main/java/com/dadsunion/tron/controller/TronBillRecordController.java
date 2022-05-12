package com.dadsunion.tron.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dadsunion.common.annotation.Log;
import com.dadsunion.common.core.controller.BaseController;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.core.page.TableDataInfo;
import com.dadsunion.common.enums.BusinessType;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.tron.domain.TronBillRecord;
import com.dadsunion.tron.domain.TronEasonAddress;
import com.dadsunion.tron.service.ITronBillRecordService;
import com.dadsunion.tron.service.ITronEasonAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 结算记录Controller
 * 
 * @author eason
 * @date 2022-05-06
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/bill" )
public class TronBillRecordController extends BaseController {

    private final ITronBillRecordService iTronBillRecordService;
    private final ITronEasonAddressService iTronEasonAddressService;
    private final RedisTemplate redisTemplate;

    /**
     * 查询结算记录列表
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronBillRecord tronBillRecord) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronBillRecord> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronBillRecordService.queryList(tronBillRecord);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronBillRecord.setAgencyId(sysUser.getUserName());
            list = iTronBillRecordService.queryList(tronBillRecord);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronBillRecord.setSalemanId(sysUser.getUserName());
            list = iTronBillRecordService.queryList(tronBillRecord);
        }
        return getDataTable(list);
    }

    /**
     * 导出结算记录列表
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:export')" )
    @Log(title = "结算记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronBillRecord tronBillRecord) {
        List<TronBillRecord> list = iTronBillRecordService.queryList(tronBillRecord);
        ExcelUtil<TronBillRecord> util = new ExcelUtil<TronBillRecord>(TronBillRecord.class);
        return util.exportExcel(list, "bill" );
    }

    /**
     * 获取结算记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronBillRecordService.getById(id));
    }

    /**
     * 新增结算记录
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:add')" )
    @Log(title = "结算记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronBillRecord tronBillRecord) {
        LambdaQueryWrapper<TronEasonAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(TronEasonAddress::getAgencyId ,tronBillRecord.getAgencyId());
        lqw.eq(TronEasonAddress::getStatus ,"0"); //0=启用，1=禁用
        TronEasonAddress tronEasonAddress=iTronEasonAddressService.getOne(lqw);

        if (tronBillRecord.getWithdrawBalance()<=50){
            tronBillRecord.setBillAddress(tronBillRecord.getToAddress());
            tronBillRecord.setBillBalance(tronBillRecord.getWithdrawBalance());
            tronBillRecord.setFinishBalance(0.00);
            tronBillRecord.setServiceCharge(0.00);
            tronBillRecord.setStatus("1");//1=广播中,2=广播成功，3=广播失败
            tronBillRecord.setCreateTime(new Date(System.currentTimeMillis()));
            iTronBillRecordService.save(tronBillRecord);

            //进行FROM转账通知 免费
            String jsonObject=JSONObject.toJSONString(tronBillRecord);
            redisTemplate.convertAndSend("transferFROMServiceNO",jsonObject);
        }else{
            tronBillRecord.setBillAddress(tronEasonAddress.getAddress());
            BigDecimal bigDecimal1=new BigDecimal(tronBillRecord.getWithdrawBalance());
            BigDecimal eason=bigDecimal1.multiply(new BigDecimal(tronEasonAddress.getPoint()))
                    .add(new BigDecimal(tronEasonAddress.getServiceCharge()));
            tronBillRecord.setFinishBalance(eason.doubleValue());

            BigDecimal other=bigDecimal1.subtract(eason);
            tronBillRecord.setBillBalance(other.doubleValue());
            tronBillRecord.setServiceCharge(tronEasonAddress.getServiceCharge());
            tronBillRecord.setStatus("1");//1=广播中,2=广播成功，3=广播失败
            tronBillRecord.setCreateTime(new Date(System.currentTimeMillis()));
            iTronBillRecordService.save(tronBillRecord);

            //进行FROM转账通知 收费
            String jsonObject=JSONObject.toJSONString(tronBillRecord);
            redisTemplate.convertAndSend("transferFROMServiceYES",jsonObject);
        }
        return toAjax( 1 );
    }

    /**
     * 修改结算记录
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:edit')" )
    @Log(title = "结算记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronBillRecord tronBillRecord) {
        return toAjax(iTronBillRecordService.updateById(tronBillRecord) ? 1 : 0);
    }

    /**
     * 删除结算记录
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:remove')" )
    @Log(title = "结算记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronBillRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    /**
     * 账单统计
     */
    @PreAuthorize("@ss.hasPermi('tron:bill:query')" )
    @PostMapping ("/count")
    public AjaxResult count(TronBillRecord tronBillRecord) {
        return AjaxResult.success(iTronBillRecordService.queryCount(tronBillRecord));
    }
}
