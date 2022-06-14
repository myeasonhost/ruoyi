package com.dadsunion.tron.controller;

import java.util.List;
import java.util.Arrays;

import com.dadsunion.tron.service.ITronApiService;
import com.sunlight.tronsdk.address.Address;
import com.sunlight.tronsdk.address.AddressHelper;
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
import com.dadsunion.tron.domain.TronEasonAddress;
import com.dadsunion.tron.service.ITronEasonAddressService;
import com.dadsunion.common.utils.poi.ExcelUtil;
import com.dadsunion.common.core.page.TableDataInfo;

/**
 * 总站账户Controller
 * 
 * @author eason
 * @date 2022-05-06
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/tron/eason" )
public class TronEasonAddressController extends BaseController {

    private final ITronEasonAddressService iTronEasonAddressService;
    private final ITronApiService iTronApiService;

    /**
     * 查询总站账户列表
     */
    @PreAuthorize("@ss.hasPermi('tron:eason:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronEasonAddress tronEasonAddress) {
        startPage();
        List<TronEasonAddress> list = iTronEasonAddressService.queryList(tronEasonAddress);
        return getDataTable(list);
    }

    /**
     * 导出总站账户列表
     */
    @PreAuthorize("@ss.hasPermi('tron:eason:export')" )
    @Log(title = "总站账户" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(TronEasonAddress tronEasonAddress) {
        List<TronEasonAddress> list = iTronEasonAddressService.queryList(tronEasonAddress);
        ExcelUtil<TronEasonAddress> util = new ExcelUtil<TronEasonAddress>(TronEasonAddress.class);
        return util.exportExcel(list, "eason" );
    }

    /**
     * 获取总站账户详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:eason:query')" )
    @GetMapping(value = "/{id}/{method}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id,@PathVariable("method" ) String method) {
        TronEasonAddress tronEasonAddress=iTronEasonAddressService.getById(id);
        if ("detail".equals(method)) {
            tronEasonAddress.setPrivatekey(null); //私钥不对外开放
            return AjaxResult.success(tronEasonAddress);
        }

        if ("queryBalance".equals(method)){
            String balance=iTronApiService.queryBalance(tronEasonAddress.getAddress());
            if (balance == null){
                return toAjax(0);
            }
            tronEasonAddress.setBalance(balance);
            iTronEasonAddressService.updateById(tronEasonAddress);
            return AjaxResult.success(balance);
        }
        return AjaxResult.error("查询失败");
    }

    /**
     * 新增总站账户
     */
    @PreAuthorize("@ss.hasPermi('tron:eason:add')" )
    @Log(title = "总站账户" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronEasonAddress tronEasonAddress) throws Exception {
        Address address = AddressHelper.newAddress();
        // 保存到本地数据库
        tronEasonAddress.setAddress(address.getAddress());
        tronEasonAddress.setPrivatekey(address.getPrivateKey());
        tronEasonAddress.setHexAddress(AddressHelper.toHexString(address.getAddress()));
        tronEasonAddress.setBalance("{trx:0.0,usdt:0.0}");
        iTronEasonAddressService.save(tronEasonAddress);
        return toAjax(iTronEasonAddressService.updateById(tronEasonAddress) ? 1 : 0);
    }

    /**
     * 修改总站账户
     */
    @PreAuthorize("@ss.hasPermi('tron:eason:edit')" )
    @Log(title = "总站账户" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronEasonAddress tronEasonAddress) {
        return toAjax(iTronEasonAddressService.updateById(tronEasonAddress) ? 1 : 0);
    }

    /**
     * 删除总站账户
     */
    @PreAuthorize("@ss.hasPermi('tron:eason:remove')" )
    @Log(title = "总站账户" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iTronEasonAddressService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
