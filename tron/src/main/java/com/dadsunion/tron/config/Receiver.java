package com.dadsunion.tron.config;

import com.alibaba.fastjson.JSONObject;
import com.dadsunion.common.core.domain.AjaxResult;
import com.dadsunion.tron.domain.TronBillRecord;
import com.dadsunion.tron.domain.TronTansferRecord;
import com.dadsunion.tron.service.ITronApiService;
import com.dadsunion.tron.service.ITronBillRecordService;
import com.dadsunion.tron.service.ITronTansferRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 接收消息
 */
@Slf4j
@Service
public class Receiver {

	@Autowired
	private ITronApiService iTronApiService;
	@Autowired
	private ITronBillRecordService iTronBillRecordService;
	@Autowired
	private ITronTansferRecordService iTronTansferRecordService;


	public void transferTRX(String message) throws Exception {
		log.debug("transferTRX接收到消息了:{}", message);
		TronTansferRecord tronTansferRecord= JSONObject.parseObject(message,TronTansferRecord.class);
		log.info("tronTansferRecord-TRX", tronTansferRecord);
		AjaxResult result=iTronApiService.transferTRX(tronTansferRecord.getFromAddress(),tronTansferRecord.getToAddress(),
				tronTansferRecord.getBalance());
		if (result.get(AjaxResult.CODE_TAG).equals(500)){
			tronTansferRecord.setStatus("3");
			tronTansferRecord.setRemark(result.get(AjaxResult.MSG_TAG).toString());
		}
		if (result.get(AjaxResult.CODE_TAG).equals(200)){
			tronTansferRecord.setStatus("2");
			tronTansferRecord.setRemark(result.get(AjaxResult.MSG_TAG).toString());
		}
		tronTansferRecord.setUpdateTime(new Date(System.currentTimeMillis()));
		iTronTansferRecordService.saveOrUpdate(tronTansferRecord);
	}


	public void transferUSDT(String message) throws Exception {
		log.info("transferUSDT接收到消息了:{}", message);
		TronTansferRecord tronTansferRecord= JSONObject.parseObject(message,TronTansferRecord.class);
		log.info("tronTansferRecord-USDT", tronTansferRecord);
		AjaxResult result=iTronApiService.transferUSDT(tronTansferRecord.getFromAddress(),tronTansferRecord.getToAddress(),
				tronTansferRecord.getBalance());
		if (result.get(AjaxResult.CODE_TAG).equals(500)){
			tronTansferRecord.setStatus("3");
			tronTansferRecord.setRemark(result.get(AjaxResult.MSG_TAG).toString());
		}
		if (result.get(AjaxResult.CODE_TAG).equals(200)){
			tronTansferRecord.setStatus("2");
			tronTansferRecord.setRemark(result.get(AjaxResult.MSG_TAG).toString());
		}
		tronTansferRecord.setUpdateTime(new Date(System.currentTimeMillis()));
		iTronTansferRecordService.saveOrUpdate(tronTansferRecord);
	}

	public void transferFROM(String message) throws Exception {
		log.info("transferFROM接收到消息了:{}", message);
		TronBillRecord tronBillRecord= JSONObject.parseObject(message,TronBillRecord.class);
		log.info("tronBillRecord", tronBillRecord);
		//（1）客户地址->结算地址转账，withdraw_balance转化USDT
		AjaxResult result=iTronApiService.transferFrom(tronBillRecord.getFromAddress(), tronBillRecord.getAuAddress(),
				tronBillRecord.getBillAddress(), tronBillRecord.getWithdrawBalance());
		log.info("transferFROM进行了FROM转账:{}", result);
        if (result.get(AjaxResult.CODE_TAG).equals(500)){
			tronBillRecord.setStatus("3"); //1=广播中,2=广播成功，3=广播失败
			tronBillRecord.setRemark(result.get(AjaxResult.MSG_TAG).toString());
        }
        if (result.get(AjaxResult.CODE_TAG).equals(200)){
			tronBillRecord.setRemark("step01:"+result.get(AjaxResult.MSG_TAG).toString());
			//（2）结算地址->客户转账，bill_address转化USDT
			AjaxResult result2=iTronApiService.transferUSDT(tronBillRecord.getBillAddress(),tronBillRecord.getToAddress(),
					tronBillRecord.getBillBalance());
			if (result2.get(AjaxResult.CODE_TAG).equals(200)){
				tronBillRecord.setStatus("2");
				tronBillRecord.setRemark(tronBillRecord.getRemark()+"step02:"+result2.get(AjaxResult.MSG_TAG).toString());
			}else{
				tronBillRecord.setStatus("3");
				tronBillRecord.setRemark(tronBillRecord.getRemark()+"step02:"+result.get(AjaxResult.MSG_TAG).toString());
			}
		}
		tronBillRecord.setUpdateTime(new Date(System.currentTimeMillis()));
		iTronBillRecordService.saveOrUpdate(tronBillRecord);

	}


}