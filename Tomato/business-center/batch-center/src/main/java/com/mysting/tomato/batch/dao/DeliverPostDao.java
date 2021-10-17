package com.mysting.tomato.batch.dao;

import java.util.List;

import com.mysting.tomato.batch.entity.DeliverPost;

public interface DeliverPostDao {

	public void batchInsert(List<? extends DeliverPost> list)  ;

}
