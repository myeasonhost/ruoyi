package com.dadsunion.tron.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.common.utils.StringUtils;
import com.dadsunion.tron.service.ITronAuthAddressService;
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
import com.dadsunion.tron.domain.TronFans;
import com.dadsunion.tron.service.ITronFansService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 粉丝Controller
 * 
 * @author eason
 * @date 2022-05-16
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/fans" )
public class TronFansController extends BaseController {

    private final ITronFansService iTronFansService;
    private final ITronAuthAddressService iTronAuthAddressService;

    /**
     * 查询粉丝列表
     */
    @PreAuthorize("@ss.hasPermi('tron:fans:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronFans tronFans) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronFans> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronFansService.queryList(tronFans);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronFans.setAgencyId(sysUser.getUserName());
            list = iTronFansService.queryList(tronFans);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronFans.setSalemanId(sysUser.getUserName());
            list = iTronFansService.queryList(tronFans);
        }
        return getDataTable(list);
    }

    /**
     * 导出粉丝列表
     */
    @PreAuthorize("@ss.hasPermi('tron:fans:export')" )
    @Log(title = "粉丝" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronFans tronFans) {
        List<TronFans> list = iTronFansService.queryList(tronFans);
        ExcelUtil<TronFans> util = new ExcelUtil<TronFans>(TronFans.class);
        return util.exportExcel(list, "fans" );
    }

    /**
     * 获取粉丝详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:fans:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iTronFansService.getById(id));
    }

    /**
     * 新增粉丝
     */
    @PreAuthorize("@ss.hasPermi('tron:fans:add')" )
    @Log(title = "粉丝" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronFans tronFans) {
        if (StringUtils.isEmpty(tronFans.getMobile())){
            return AjaxResult.error("电话不能为空");
        }
        if (StringUtils.isNotEmpty(tronFans.getMobile())){
            LambdaQueryWrapper<TronFans> lqw = Wrappers.lambdaQuery();
            lqw.eq(TronFans::getMobile ,tronFans.getMobile());
            if (iTronFansService.getOne(lqw)!=null){
                return AjaxResult.error("该客户已经被业务员绑定");
            }
        }

        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("admin")) { //只能有一个角色
            if (StringUtils.isEmpty(tronFans.getAgencyId())){
                return AjaxResult.error("代理agencyId不能为空");
            }
            if (StringUtils.isEmpty(tronFans.getSalemanId())){
                return AjaxResult.error("业务员salemanId不能为空");
            }
        }
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            if (StringUtils.isEmpty(tronFans.getSalemanId())){
                return AjaxResult.error("业务员salemanId不能为空");
            }
            tronFans.setAgencyId(sysUser.getUserName());
        }
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronFans.setSalemanId(sysUser.getUserName());
            String agencyId=iTronAuthAddressService.queryAgent(sysUser.getDeptId());
            tronFans.setAgencyId(agencyId);
        }

        return toAjax(iTronFansService.save(tronFans) ? 1 : 0);
    }

    /**
     * 修改粉丝
     */
    @PreAuthorize("@ss.hasPermi('tron:fans:edit')" )
    @Log(title = "粉丝" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronFans tronFans) {
        return toAjax(iTronFansService.updateById(tronFans) ? 1 : 0);
    }

    /**
     * 删除粉丝
     */
    @PreAuthorize("@ss.hasPermi('tron:fans:remove')" )
    @Log(title = "粉丝" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronFansService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
