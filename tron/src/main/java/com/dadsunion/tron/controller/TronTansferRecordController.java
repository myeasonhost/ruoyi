package com.dadsunion.tron.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.service.ITronApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.dadsunion.tron.domain.TronTansferRecord;
import com.dadsunion.tron.service.ITronTansferRecordService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 转账记录Controller
 * 
 * @author eason
 * @date 2022-05-05
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/transfer" )
public class TronTansferRecordController extends BaseController {

    private final ITronTansferRecordService iTronTansferRecordService;
    private final ITronApiService iTronApiService;
    private final RedisTemplate redisTemplate;

    /**
     * 查询转账记录列表
     */
    @PreAuthorize("@ss.hasPermi('tron:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronTansferRecord tronTansferRecord) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronTansferRecord> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronTansferRecordService.queryList(tronTansferRecord);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronTansferRecord.setAgencyId(sysUser.getUserName());
            list = iTronTansferRecordService.queryList(tronTansferRecord);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronTansferRecord.setSalemanId(sysUser.getUserName());
            list = iTronTansferRecordService.queryList(tronTansferRecord);
        }
        return getDataTable(list);
    }

    /**
     * 导出转账记录列表
     */
    @PreAuthorize("@ss.hasPermi('tron:transfer:export')" )
    @Log(title = "转账记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronTansferRecord tronTansferRecord) {
        List<TronTansferRecord> list = iTronTansferRecordService.queryList(tronTansferRecord);
        ExcelUtil<TronTansferRecord> util = new ExcelUtil<TronTansferRecord>(TronTansferRecord.class);
        return util.exportExcel(list, "transfer" );
    }

    /**
     * 获取转账记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:transfer:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronTansferRecordService.getById(id));
    }

    /**
     * 新增转账记录
     */
    @PreAuthorize("@ss.hasPermi('tron:transfer:add')" )
    @Log(title = "转账记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronTansferRecord tronTansferRecord) throws Exception {
        tronTansferRecord.setStatus("1");//1=广播中,2=广播成功，3=广播失败
        tronTansferRecord.setCreateTime(new Date(System.currentTimeMillis()));
        iTronTansferRecordService.save(tronTansferRecord);
        AjaxResult result=null;
        if ("TRX".equals(tronTansferRecord.getAddressType())) {
            //进行TRX转账通知
            String jsonObject= JSONObject.toJSONString(tronTansferRecord);
            redisTemplate.convertAndSend("transferTRX",jsonObject);
        }
        if ("USDT".equals(tronTansferRecord.getAddressType())) {
            //进行USDT转账通知
            String jsonObject= JSONObject.toJSONString(tronTansferRecord);
            redisTemplate.convertAndSend("transferUSDT",jsonObject);
        }
        return toAjax(1 );
    }

    /**
     * 修改转账记录
     */
    @PreAuthorize("@ss.hasPermi('tron:transfer:edit')" )
    @Log(title = "转账记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronTansferRecord tronTansferRecord) {
        return toAjax(iTronTansferRecordService.updateById(tronTansferRecord) ? 1 : 0);
    }

    /**
     * 删除转账记录
     */
    @PreAuthorize("@ss.hasPermi('tron:transfer:remove')" )
    @Log(title = "转账记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronTansferRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
