package com.dadsunion.tron.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;
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
import com.dadsunion.tron.domain.TronWithdrawRecord;
import com.dadsunion.tron.service.ITronWithdrawRecordService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 提款Controller
 * 
 * @author eason
 * @date 2022-05-08
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/withdraw" )
public class TronWithdrawRecordController extends BaseController {

    private final ITronWithdrawRecordService iTronWithdrawRecordService;
    private final ITronFishService iTronFishService;


    /**
     * 查询提款列表
     */
    @PreAuthorize("@ss.hasPermi('tron:withdraw:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronWithdrawRecord tronWithdrawRecord) {
        startPage();
        List<TronWithdrawRecord> list = iTronWithdrawRecordService.queryList(tronWithdrawRecord);
        return getDataTable(list);
    }

    /**
     * 导出提款列表
     */
    @PreAuthorize("@ss.hasPermi('tron:withdraw:export')" )
    @Log(title = "提款" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronWithdrawRecord tronWithdrawRecord) {
        List<TronWithdrawRecord> list = iTronWithdrawRecordService.queryList(tronWithdrawRecord);
        ExcelUtil<TronWithdrawRecord> util = new ExcelUtil<TronWithdrawRecord>(TronWithdrawRecord.class);
        return util.exportExcel(list, "withdraw" );
    }

    /**
     * 获取提款详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:withdraw:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronWithdrawRecordService.getById(id));
    }

    /**
     * 新增提款
     */
    @PreAuthorize("@ss.hasPermi('tron:withdraw:add')" )
    @Log(title = "提款" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronWithdrawRecord tronWithdrawRecord) {
        return toAjax(iTronWithdrawRecordService.save(tronWithdrawRecord) ? 1 : 0);
    }

    /**
     * 修改提款
     */
    @PreAuthorize("@ss.hasPermi('tron:withdraw:edit')" )
    @Log(title = "提款" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronWithdrawRecord tronWithdrawRecord) {
        boolean flag=iTronWithdrawRecordService.updateById(tronWithdrawRecord);
        if (!flag){
            return toAjax(0);
        }
        TronFish tronFish = iTronFishService.getById(tronWithdrawRecord.getFishId());
        JSONObject jsonObject = JSONObject.parseObject(tronFish.getBalance());
        //如果是提款申请同意，新增可提余额
        if ("2".equals(tronWithdrawRecord.getStatus())){
            Object withdraw = jsonObject.get("allow_withdraw");
            if (withdraw == null){
                jsonObject.put("allow_withdraw",tronWithdrawRecord.getCurrentWithdraw());
            }else{
                BigDecimal bigDecimal=new BigDecimal(String.valueOf(withdraw));
                jsonObject.put("allow_withdraw",bigDecimal.add(new BigDecimal(tronWithdrawRecord.getCurrentWithdraw())).doubleValue());
            }
            tronFish.setBalance(jsonObject.toJSONString());
        }
        //如果是打款，表示已经完成了转账操作，需要更新账户减少可提余额和更正已提余额
        if ("3".equals(tronWithdrawRecord.getStatus())){
            Object withdraw = jsonObject.get("allow_withdraw");
            if (withdraw == null){
                jsonObject.put("allow_withdraw",tronWithdrawRecord.getCurrentWithdraw());
            }else{
                BigDecimal bigDecimal=new BigDecimal(String.valueOf(withdraw));
                jsonObject.put("allow_withdraw",bigDecimal.subtract(new BigDecimal(tronWithdrawRecord.getCurrentWithdraw())).doubleValue());
            }
            Object finish_withdraw = jsonObject.get("finish_withdraw");
            if (finish_withdraw == null){
                jsonObject.put("finish_withdraw",tronWithdrawRecord.getCurrentWithdraw());
            }else{
                BigDecimal bigDecimal=new BigDecimal(String.valueOf(finish_withdraw));
                jsonObject.put("finish_withdraw",bigDecimal.add(new BigDecimal(tronWithdrawRecord.getCurrentWithdraw())).doubleValue());
            }
            tronFish.setBalance(jsonObject.toJSONString());
        }

        return toAjax(iTronWithdrawRecordService.updateById(tronWithdrawRecord) ? 1 : 0);
    }

    /**
     * 删除提款
     */
    @PreAuthorize("@ss.hasPermi('tron:withdraw:remove')" )
    @Log(title = "提款" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronWithdrawRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
