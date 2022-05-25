package com.dadsunion.tron.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dadsunion.common.annotation.Log;
import com.dadsunion.common.constant.HttpStatus;
import com.dadsunion.common.core.controller.BaseController;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.common.core.domain.entity.SysUser;
import com.dadsunion.common.core.domain.model.LoginUser;
import com.dadsunion.common.core.page.TableDataInfo;
import com.dadsunion.common.enums.BusinessType;
import com.dadsunion.common.utils.GenCodeUtil;
import com.dadsunion.common.utils.SecurityUtils;
import com.dadsunion.common.utils.StringUtils;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.service.ITronApiService;
import com.dadsunion.tron.service.ITronAuthAddressService;
import com.github.pagehelper.PageInfo;
import com.sunlight.tronsdk.address.Address;
import com.sunlight.tronsdk.address.AddressHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private final ITronApiService iTronApiService;

    /**
     * 查询授权列表
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronAuthAddress tronAuthAddress) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<TronAuthAddress> list = new ArrayList<>();
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId())){
            list = iTronAuthAddressService.queryList(tronAuthAddress);
        }
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            tronAuthAddress.setAgencyId(sysUser.getUserName());
            list = iTronAuthAddressService.queryList(tronAuthAddress);
        } else if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronAuthAddress.setSalemanId(sysUser.getUserName());
            list = iTronAuthAddressService.queryList(tronAuthAddress);
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);

        LambdaQueryWrapper<TronAuthAddress> lqw = Wrappers.lambdaQuery();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tronAuthAddress.getAgencyId())){
            lqw.eq(TronAuthAddress::getAgencyId ,tronAuthAddress.getAgencyId());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tronAuthAddress.getSalemanId())){
            lqw.eq(TronAuthAddress::getSalemanId ,tronAuthAddress.getSalemanId());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tronAuthAddress.getAddressType())){
            lqw.eq(TronAuthAddress::getAddressType ,tronAuthAddress.getAddressType());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tronAuthAddress.getAuAddress())){
            lqw.eq(TronAuthAddress::getAuAddress ,tronAuthAddress.getAuAddress());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tronAuthAddress.getToken())){
            lqw.eq(TronAuthAddress::getToken ,tronAuthAddress.getToken());
        }
        lqw.orderByDesc(TronAuthAddress::getCreateTime);

        rspData.setTotal(iTronAuthAddressService.count(lqw));
        return rspData;
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
    @Log(title = "查询TRX余额" , businessType = BusinessType.INSERT)
    @GetMapping(value = "/{id}/{method}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id,@PathVariable("method" ) String method) {
        TronAuthAddress tronAuthAddress=iTronAuthAddressService.getById(id);
        if ("detail".equals(method)) {
            tronAuthAddress.setPrivatekey(null); //私钥不对外开放
            return AjaxResult.success(tronAuthAddress);
        }

        if ("queryBalance".equals(method)){
            String balance=iTronApiService.queryBalance(tronAuthAddress.getAuAddress());
            if (balance == null){
                return toAjax(0);
            }
            tronAuthAddress.setBalance(balance);
            iTronAuthAddressService.updateById(tronAuthAddress);
            return AjaxResult.success(balance);
        }
        return AjaxResult.error("查询失败");
    }

    /**
     * 新增授权
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:add')" )
    @Log(title = "授权" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronAuthAddress tronAuthAddress) throws Exception {
        SysUser sysUser=SecurityUtils.getLoginUser().getUser();
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("admin")) { //只能有一个角色
            if (StringUtils.isEmpty(tronAuthAddress.getAgencyId())){
                return AjaxResult.error("代理agencyId不能为空");
            }
            if (StringUtils.isEmpty(tronAuthAddress.getSalemanId())){
                return AjaxResult.error("业务员salemanId不能为空");
            }
        }
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("agent")) { //只能有一个角色
            if (StringUtils.isEmpty(tronAuthAddress.getSalemanId())){
                return AjaxResult.error("业务员salemanId不能为空");
            }
            tronAuthAddress.setAgencyId(sysUser.getUserName());
        }
        if (sysUser.getRoles().get(0).getRoleKey().startsWith("common")) {
            tronAuthAddress.setSalemanId(sysUser.getUserName());
            String agencyId=iTronAuthAddressService.queryAgent(sysUser.getDeptId());
            tronAuthAddress.setAgencyId(agencyId);
        }

        Address address = AddressHelper.newAddress();
        // 保存到本地数据库
        tronAuthAddress.setAuAddress(address.getAddress());
        tronAuthAddress.setPrivatekey(address.getPrivateKey());
        tronAuthAddress.setAuHexAddress(AddressHelper.toHexString(address.getAddress()));
        tronAuthAddress.setToken("000000");
        tronAuthAddress.setBalance("{trx:0.0,usdt:0.0}");
        iTronAuthAddressService.save(tronAuthAddress);
        tronAuthAddress.setToken(GenCodeUtil.toSerialCode(tronAuthAddress.getId()));
        return toAjax(iTronAuthAddressService.updateById(tronAuthAddress) ? 1 : 0);
    }
    /**
     * 修改授权
     */
    @PreAuthorize("@ss.hasPermi('tron:auth:edit')" )
    @Log(title = "授权" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronAuthAddress tronAuthAddress) {
        String uri=iTronAuthAddressService.getAuthAddressUri("auth-address-uri");
        tronAuthAddress.setUrlAddress(uri+"/?from=bitkeep&lang=en&token="+tronAuthAddress.getToken());
        iTronAuthAddressService.updateById(tronAuthAddress);
        return AjaxResult.success(tronAuthAddress);
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
