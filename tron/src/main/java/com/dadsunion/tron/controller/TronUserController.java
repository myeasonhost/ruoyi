package com.dadsunion.tron.controller;

import java.util.List;
import java.util.Arrays;

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
import com.dadsunion.tron.domain.TronUser;
import com.dadsunion.tron.service.ITronUserService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 业务员Controller
 * 
 * @author eason
 * @date 2022-04-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/user" )
public class TronUserController extends BaseController {

    private final ITronUserService iTronUserService;

    /**
     * 查询业务员列表
     */
    @PreAuthorize("@ss.hasPermi('tron:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronUser tronUser) {
        startPage();
        List<TronUser> list = iTronUserService.queryList(tronUser);
        return getDataTable(list);
    }

    /**
     * 导出业务员列表
     */
    @PreAuthorize("@ss.hasPermi('tron:user:export')" )
    @Log(title = "业务员" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronUser tronUser) {
        List<TronUser> list = iTronUserService.queryList(tronUser);
        ExcelUtil<TronUser> util = new ExcelUtil<TronUser>(TronUser.class);
        return util.exportExcel(list, "user" );
    }

    /**
     * 获取业务员详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:user:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronUserService.getById(id));
    }

    /**
     * 新增业务员
     */
    @PreAuthorize("@ss.hasPermi('tron:user:add')" )
    @Log(title = "业务员" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronUser tronUser) {
        return toAjax(iTronUserService.save(tronUser) ? 1 : 0);
    }

    /**
     * 修改业务员
     */
    @PreAuthorize("@ss.hasPermi('tron:user:edit')" )
    @Log(title = "业务员" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronUser tronUser) {
        return toAjax(iTronUserService.updateById(tronUser) ? 1 : 0);
    }

    /**
     * 删除业务员
     */
    @PreAuthorize("@ss.hasPermi('tron:user:remove')" )
    @Log(title = "业务员" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronUserService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
