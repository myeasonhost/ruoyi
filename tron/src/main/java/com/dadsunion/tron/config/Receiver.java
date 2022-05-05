package com.dadsunion.tron.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * 接收消息
 */
@Slf4j
public class Receiver {

	private CountDownLatch latch;

	@Autowired
	public Receiver(CountDownLatch countDownLatch) {
		this.latch = countDownLatch;
	}


	public void receiveMessage(String message) {
		log.debug("message接收到消息了:{}", message);
	}


	public void receiveMessage2(String message) {
		log.info("message2接收到消息了:{}", message);
	}

}