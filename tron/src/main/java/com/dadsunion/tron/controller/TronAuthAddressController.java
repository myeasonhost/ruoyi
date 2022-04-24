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
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.service.ITronAuthAddressService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 授权Controller
 *
 * @author eason
 * @date 2022-04-20
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/auth" )
public class TronAuthAddressController extends BaseController {

    private final ITronAuthAddressService iTronAuthAddressService;

    /**
     * 查询授权列表
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronAuthAddress tronAuthAddress) {
        startPage();
        List<TronAuthAddress> list = iTronAuthAddressService.queryList(tronAuthAddress);
        return getDataTable(list);
    }

    /**
     * 导出授权列表
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:export')" )
    @Log(title = "授权" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronAuthAddress tronAuthAddress) {
        List<TronAuthAddress> list = iTronAuthAddressService.queryList(tronAuthAddress);
        ExcelUtil<TronAuthAddress> util = new ExcelUtil<TronAuthAddress>(TronAuthAddress.class);
        return util.exportExcel(list, "auth" );
    }

    /**
     * 获取授权详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronAuthAddressService.getById(id));
    }

    /**
     * 新增授权
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:add')" )
    @Log(title = "授权" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronAuthAddress tronAuthAddress) {
        return toAjax(iTronAuthAddressService.save(tronAuthAddress) ? 1 : 0);
    }

    /**
     * 修改授权
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:edit')" )
    @Log(title = "授权" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronAuthAddress tronAuthAddress) {
        return toAjax(iTronAuthAddressService.updateById(tronAuthAddress) ? 1 : 0);
    }

    /**
     * 删除授权
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:remove')" )
    @Log(title = "授权" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronAuthAddressService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
