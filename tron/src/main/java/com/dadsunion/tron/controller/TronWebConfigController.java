package com.dadsunion.tron.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.utils.SecurityUtils;
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
import com.dadsunion.tron.domain.TronWebConfig;
import com.dadsunion.tron.service.ITronWebConfigService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 矿机设置Controller
 * 
 * @author eason
 * @date 2022-05-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/config" )
public class TronWebConfigController extends BaseController {

    private final ITronWebConfigService iTronWebConfigService;

    /**
     * 查询矿机设置列表
     */
    @PreAuthorize("@ss.hasPermi('tron:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronWebConfig tronWebConfig) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronWebConfig> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronWebConfigService.queryList(tronWebConfig);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronWebConfig.setAgencyId(sysUser.getUserName());
            list = iTronWebConfigService.queryList(tronWebConfig);
        }
        return getDataTable(list);
    }

    /**
     * 导出矿机设置列表
     */
    @PreAuthorize("@ss.hasPermi('tron:config:export')" )
    @Log(title = "矿机设置" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronWebConfig tronWebConfig) {
        List<TronWebConfig> list = iTronWebConfigService.queryList(tronWebConfig);
        ExcelUtil<TronWebConfig> util = new ExcelUtil<TronWebConfig>(TronWebConfig.class);
        return util.exportExcel(list, "config" );
    }

    /**
     * 获取矿机设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:config:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronWebConfigService.getById(id));
    }

    /**
     * 新增矿机设置
     */
    @PreAuthorize("@ss.hasPermi('tron:config:add')" )
    @Log(title = "矿机设置" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronWebConfig tronWebConfig) {
        return toAjax(iTronWebConfigService.save(tronWebConfig) ? 1 : 0);
    }

    /**
     * 修改矿机设置
     */
    @PreAuthorize("@ss.hasPermi('tron:config:edit')" )
    @Log(title = "矿机设置" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronWebConfig tronWebConfig) {
        return toAjax(iTronWebConfigService.updateById(tronWebConfig) ? 1 : 0);
    }

    /**
     * 删除矿机设置
     */
    @PreAuthorize("@ss.hasPermi('tron:config:remove')" )
    @Log(title = "矿机设置" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronWebConfigService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
