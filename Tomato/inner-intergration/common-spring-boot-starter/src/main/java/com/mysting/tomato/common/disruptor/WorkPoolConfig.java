package com.mysting.tomato.common.disruptor;

import javax.annotation.PostConstruct;

import com.mysting.tomato.common.disruptor.consumer.Consumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.SleepingWaitStrategy;


@Configuration
@ConditionalOnProperty(name = {"spring.disruptor.enable"}, matchIfMissing = false, havingValue = "true")
public class WorkPoolConfig {

	@PostConstruct
	public void init() {
		Consumer[] consumers = new Consumer[4];
		for (int i = 0; i < consumers.length; i++) {
			consumers[i] = new Consumer(String.format("comsumer%d", i));
		}
		RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI, 1024 * 1024,
				new SleepingWaitStrategy(), consumers);

	}

}
