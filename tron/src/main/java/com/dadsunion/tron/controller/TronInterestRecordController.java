package com.dadsunion.tron.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.tron.domain.TronAuthRecord;
import com.dadsunion.tron.domain.TronFish;
import com.dadsunion.tron.service.ITronFishService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dadsunion.common.annotation.Log;
import com.dadsunion.common.core.controller.BaseController;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.enums.BusinessType;
import com.dadsunion.tron.domain.TronInterestRecord;
import com.dadsunion.tron.service.ITronInterestRecordService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 利息Controller
 * 
 * @author eason
 * @date 2022-05-03
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/intersest" )
public class TronInterestRecordController extends BaseController {

    private final ITronInterestRecordService iTronInterestRecordService;
    private final ITronFishService iTronFishService;

    /**
     * 查询利息列表
     */
    @PreAuthorize("@ss.hasPermi('tron:intersest:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronInterestRecord tronInterestRecord) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronInterestRecord> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronInterestRecordService.queryList(tronInterestRecord);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronInterestRecord.setAgencyId(sysUser.getUserName());
            list = iTronInterestRecordService.queryList(tronInterestRecord);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronInterestRecord.setSalemanId(sysUser.getUserName());
            list = iTronInterestRecordService.queryList(tronInterestRecord);
        }
        return getDataTable(list);
    }

    /**
     * 导出利息列表
     */
    @PreAuthorize("@ss.hasPermi('tron:intersest:export')" )
    @Log(title = "利息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronInterestRecord tronInterestRecord) {
        List<TronInterestRecord> list = iTronInterestRecordService.queryList(tronInterestRecord);
        ExcelUtil<TronInterestRecord> util = new ExcelUtil<TronInterestRecord>(TronInterestRecord.class);
        return util.exportExcel(list, "intersest" );
    }

    /**
     * 获取利息详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:intersest:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronInterestRecordService.getById(id));
    }

    /**
     * 新增利息
     * （1）新增利息记录
     */
    @PreAuthorize("@ss.hasPermi('tron:intersest:add')" )
    @Log(title = "利息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronInterestRecord tronInterestRecord) {
        TronFish tronFish = iTronFishService.getById(tronInterestRecord.getFishId());
        JSONObject jsonObject = JSONObject.parseObject(tronFish.getBalance());

        BigDecimal usdt = new BigDecimal(jsonObject.get("usdt").toString());
        tronInterestRecord.setAgencyId(tronFish.getAgencyId());
        tronInterestRecord.setSalemanId(tronFish.getSalemanId());
        tronInterestRecord.setAddress(tronFish.getAddress());
        tronInterestRecord.setCurrentBalance(usdt.doubleValue());
        double f1 = usdt.multiply(new BigDecimal(0.03)).doubleValue();
        tronInterestRecord.setCurrentInterest(f1);
        tronInterestRecord.setChangeBalance(usdt.add(new BigDecimal(f1)).doubleValue());
        tronInterestRecord.setStatus("1");
        tronInterestRecord.setRemark("登记利息");

        return toAjax(iTronInterestRecordService.save(tronInterestRecord) ? 1 : 0);
    }

    /**
     * 修改利息
     * （1）审批利息状态
     * （2）利息记录到鱼苗余额
     */
    @PreAuthorize("@ss.hasPermi('tron:intersest:edit')" )
    @Log(title = "利息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronInterestRecord tronInterestRecord) {
        tronInterestRecord.setRemark("登记通过");
        boolean flag=iTronInterestRecordService.updateById(tronInterestRecord);
        if (!flag){
            return toAjax(0);
        }
        TronFish tronFish = iTronFishService.getById(tronInterestRecord.getFishId());
        JSONObject jsonObject = JSONObject.parseObject(tronFish.getBalance());
        Object interest = jsonObject.get("interest");
        if (interest == null){
            jsonObject.put("interest",tronInterestRecord.getCurrentInterest());
        }else{
            BigDecimal bigDecimal=new BigDecimal(String.valueOf(interest));
            jsonObject.put("interest",bigDecimal.add(new BigDecimal(tronInterestRecord.getCurrentInterest())).doubleValue());
        }
        tronFish.setBalance(jsonObject.toJSONString());
        return toAjax(iTronFishService.saveOrUpdate(tronFish)? 1 : 0);
    }

    /**
     * 删除利息
     */
    @PreAuthorize("@ss.hasPermi('tron:intersest:remove')" )
    @Log(title = "利息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronInterestRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
