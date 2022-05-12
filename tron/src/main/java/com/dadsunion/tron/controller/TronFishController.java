package com.dadsunion.tron.controller;

import com.alibaba.fastjson.JSONObject;
import com.dadsunion.common.annotation.Log;
import com.dadsunion.common.core.controller.BaseController;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.core.page.TableDataInfo;
import com.dadsunion.common.enums.BusinessType;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.tron.domain.TronFish;
import com.dadsunion.tron.service.ITronApiService;
import com.dadsunion.tron.service.ITronFishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private final ITronApiService iTronApiService;
    /**
     * 查询鱼苗管理列表
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronFish tronFish) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronFish> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronFishService.queryList(tronFish);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronFish.setAgencyId(sysUser.getUserName());
            list = iTronFishService.queryList(tronFish);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronFish.setSalemanId(sysUser.getUserName());
            list = iTronFishService.queryList(tronFish);
        }
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
    @GetMapping(value = "/{id}/{method}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id,@PathVariable("method" ) String method) {
        TronFish tronFish=iTronFishService.getById(id);
        if ("detail".equals(method)) {
            return AjaxResult.success(tronFish);
        }

        if ("detailWithBalance".equals(method)) {
            String balance1=iTronApiService.queryBalance(tronFish.getAddress());
            tronFish.setFromAddressbalance(balance1);
            String balance2=iTronApiService.queryBalance(tronFish.getAuAddress());
            tronFish.setAuAddressbalance(balance2);
            return AjaxResult.success(tronFish);
        }

        if ("queryBalance".equals(method)){
            String balance=iTronApiService.queryBalance(tronFish.getAddress());
            if (balance == null){
                return toAjax(0);
            }
            JSONObject jsonObject1 = JSONObject.parseObject(tronFish.getBalance());
            JSONObject jsonObject2 =JSONObject.parseObject(balance);
            jsonObject1.put("trx",jsonObject2.get("trx"));
            jsonObject1.put("usdt",jsonObject2.get("usdt"));
            tronFish.setBalance(jsonObject1.toJSONString());
            tronFish.setUpdateTime(new Date(System.currentTimeMillis()));
            iTronFishService.updateById(tronFish);
            return AjaxResult.success(balance);
        }
        return AjaxResult.error("查询失败");
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
        tronFish.setUpdateTime(new Date(System.currentTimeMillis()));
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

    /**
     * 鱼苗统计
     */
    @PreAuthorize("@ss.hasPermi('tron:fish:query')")
    @PostMapping("/count/{method}")
    public AjaxResult count(TronFish tronFish,@PathVariable("method" ) String method) {
        if ("fish".equals(method)){
            return AjaxResult.success(iTronFishService.queryCount(tronFish));
        }
        if ("usdt".equals(method)){
            return AjaxResult.success(iTronFishService.queryTotalUsdt(tronFish));
        }
        return toAjax(0);
    }
}
