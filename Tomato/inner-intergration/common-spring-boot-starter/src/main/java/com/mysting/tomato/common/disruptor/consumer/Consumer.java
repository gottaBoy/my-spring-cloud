package com.mysting.tomato.common.disruptor.consumer;

import java.text.SimpleDateFormat;

import com.lmax.disruptor.WorkHandler;
import com.mysting.tomato.common.disruptor.message.DataWrapper;

public class Consumer implements WorkHandler<DataWrapper> {

	// 多消费者构建
	private String consumerId;

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public Consumer(String consumerId) {
		this.consumerId = consumerId;
	}

	@Override
	public void onEvent(DataWrapper event) throws Exception {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		} catch (Exception e) {
		}

	}

}
