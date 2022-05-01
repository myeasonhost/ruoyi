package com.dadsunion.tron.task;

import com.dadsunion.common.utils.http.HttpUtils;
import com.dadsunion.tron.domain.TronAuthAddress;
import com.dadsunion.tron.service.ITronAuthAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询余额任务
 *
 * @author eason
 */
@Slf4j
@Component("queryBalanceTask")
public class QueryBalanceTask {

	@Autowired
	private ITronAuthAddressService iTronAuthAddressService;
	/**
	 * 查询余额发起任务
	 */
	public void query() {
		log.info("查询余额发起任务开始");
//		List<TronAuthAddress> list=iTronAuthAddressService.queryList();
//		HttpUtils.sendPost()
	}

}
