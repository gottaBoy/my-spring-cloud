package com.mysting.tomato.message.service;

import java.util.Map;

import com.mysting.tomato.message.config.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ProducerService {
	private final Source source;

	public ProducerService(Source source) {
		this.source = source;
	}

	public void sendMsg(final Map msg) {

		MessageChannel messageChannel = source.output();
		messageChannel.send(MessageBuilder.withPayload(msg)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}
}