package com.mysting.tomato.batch.item;

import java.util.Map;

import com.mysting.tomato.batch.entity.DeliverPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

/**
 * Content :数据处理转换Item
 */
@Service
public class DeliverPostProcessorItem implements ItemProcessor<DeliverPost, Map> {

	Logger logger = LoggerFactory.getLogger(DeliverPostProcessorItem.class);
 
	
	
	@Override
	public Map process(DeliverPost deliverPost) throws Exception {
		logger.info("订单号：【{}】经过处理器 ", deliverPost.getOrderId());

		 Map map = Maps.newHashMap();
		 map.put("orderId", deliverPost.getOrderId());
		 map.put("postId", deliverPost.getPostId());

		return map;
	}

	 

}
