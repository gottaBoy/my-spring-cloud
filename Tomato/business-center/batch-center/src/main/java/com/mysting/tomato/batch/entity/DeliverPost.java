package com.mysting.tomato.batch.entity;

import lombok.Data;

@Data
public class DeliverPost {

	private String orderId ;
	private String postId;
	private boolean isArrived ;
	
	
}
