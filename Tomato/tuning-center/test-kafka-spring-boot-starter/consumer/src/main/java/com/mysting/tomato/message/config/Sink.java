package com.mysting.tomato.message.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Sink {
	/**
	 * Input channel name.
	 */
	String INPUT = "input";

	/**
	 * @return input channel.
	 */
	@Input(Sink.INPUT)
	SubscribableChannel input();

}
