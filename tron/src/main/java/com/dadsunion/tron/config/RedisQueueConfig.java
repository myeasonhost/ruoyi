package com.dadsunion.tron.config;

import com.dadsunion.tron.constants.SysConstant;
import com.dadsunion.tron.queue.Receiver;
import com.fasterxml.jackson.databind.JavaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

@Configuration
public class RedisQueueConfig {


	/**
	 * 初始化监听器
	 *
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @param listenerAdapter2
	 * @return
	 */
	@Bean
	public RedisMessageListenerContainer container(JedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter, MessageListenerAdapter listenerAdapter2) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic(SysConstant.CHANNEL_CHAIN));
		//		container.addMessageListener(listenerAdapter2, new PatternTopic(SysConstant.PROJECT_NAME));
		return container;
	}

	/**
	 * 配置监听器1
	 *
	 * @return
	 */
	@Bean
	public MessageListenerAdapter listenerAdapter(Receiver receiver) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
		//		messageListenerAdapter.setSerializer(new JdkSerializationRedisSerializer());
//		messageListenerAdapter.setStringSerializer(new Jackson2JsonRedisSerializer<String>(String.class));
		return messageListenerAdapter;
	}

	//	/**
	//	 * 配置监听器2
	//	 *
	//	 * @return
	//	 */
	//	@Bean
	//	public MessageListenerAdapter listenerAdapter2(ReceiveMessage receiveMessage) {
	//		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiveMessage, "receiveMessage2");
	//		messageListenerAdapter.setSerializer(new JdkSerializationRedisSerializer());
	//		return messageListenerAdapter;
	//	}

	/**
	 * 消息监听者1
	 *
	 * @return
	 */
	@Bean
	public Receiver receiver(CountDownLatch countDownLatch) {
		return new Receiver(countDownLatch);
	}

	//	/**
	//	 * 消息监听者2
	//	 *
	//	 * @return
	//	 */
	//	@Bean
	//	public ReceiveMessage receiveMessage2(CountDownLatch countDownLatch) {
	//		return new ReceiveMessage(countDownLatch);
	//	}

	@Bean
	public CountDownLatch countDownLatch() {
		return new CountDownLatch(1);
	}
}
