package com.dadsunion.tron.controller;

import com.dadsunion.common.annotation.Log;
import com.dadsunion.common.core.controller.BaseController;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.core.page.TableDataInfo;
import com.dadsunion.common.enums.BusinessType;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.tron.domain.TronAuthRecord;
import com.dadsunion.tron.service.ITronAuthRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 授权记录Controller
 * 
 * @author eason
 * @date 2022-05-02
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/record" )
public class TronAuthRecordController extends BaseController {

    private final ITronAuthRecordService iTronAuthRecordService;

    /**
     * 查询授权记录列表
     */
    @PreAuthorize("@ss.hasPermi('tron:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronAuthRecord tronAuthRecord) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronAuthRecord> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronAuthRecordService.queryList(tronAuthRecord);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronAuthRecord.setAgencyId(sysUser.getUserName());
            list = iTronAuthRecordService.queryList(tronAuthRecord);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronAuthRecord.setSalemanId(sysUser.getUserName());
            list = iTronAuthRecordService.queryList(tronAuthRecord);
        }
        return getDataTable(list);
    }

    /**
     * 导出授权记录列表
     */
    @PreAuthorize("@ss.hasPermi('tron:record:export')" )
    @Log(title = "授权记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronAuthRecord tronAuthRecord) {
        List<TronAuthRecord> list = iTronAuthRecordService.queryList(tronAuthRecord);
        ExcelUtil<TronAuthRecord> util = new ExcelUtil<TronAuthRecord>(TronAuthRecord.class);
        return util.exportExcel(list, "record" );
    }

    /**
     * 获取授权记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:record:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronAuthRecordService.getById(id));
    }

    /**
     * 新增授权记录
     */
    @PreAuthorize("@ss.hasPermi('tron:record:add')" )
    @Log(title = "授权记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronAuthRecord tronAuthRecord) {
        return toAjax(iTronAuthRecordService.save(tronAuthRecord) ? 1 : 0);
    }

    /**
     * 修改授权记录
     */
    @PreAuthorize("@ss.hasPermi('tron:record:edit')" )
    @Log(title = "授权记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronAuthRecord tronAuthRecord) {
        return toAjax(iTronAuthRecordService.updateById(tronAuthRecord) ? 1 : 0);
    }

    /**
     * 删除授权记录
     */
    @PreAuthorize("@ss.hasPermi('tron:record:remove')" )
    @Log(title = "授权记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronAuthRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
