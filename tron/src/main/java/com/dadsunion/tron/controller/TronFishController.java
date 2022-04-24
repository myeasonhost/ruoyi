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
import com.dadsunion.tron.domain.TronFish;
import com.dadsunion.tron.service.ITronFishService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 鱼苗管理Controller
 * 
 * @author eason
 * @date 2022-04-20
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/fish" )
public class TronFishController extends BaseController {

    private final ITronFishService iTronFishService;

    /**
     * 查询鱼苗管理列表
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronFish tronFish) {
        startPage();
        List<TronFish> list = iTronFishService.queryList(tronFish);
        return getDataTable(list);
    }

    /**
     * 导出鱼苗管理列表
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:export')" )
    @Log(title = "鱼苗管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronFish tronFish) {
        List<TronFish> list = iTronFishService.queryList(tronFish);
        ExcelUtil<TronFish> util = new ExcelUtil<TronFish>(TronFish.class);
        return util.exportExcel(list, "fish" );
    }

    /**
     * 获取鱼苗管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronFishService.getById(id));
    }

    /**
     * 新增鱼苗管理
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:add')" )
    @Log(title = "鱼苗管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronFish tronFish) {
        return toAjax(iTronFishService.save(tronFish) ? 1 : 0);
    }

    /**
     * 修改鱼苗管理
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:edit')" )
    @Log(title = "鱼苗管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronFish tronFish) {
        return toAjax(iTronFishService.updateById(tronFish) ? 1 : 0);
    }

    /**
     * 删除鱼苗管理
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:remove')" )
    @Log(title = "鱼苗管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronFishService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
