package com.mysting.tomato.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysting.tomato.batch.entity.DeliverPost;
import org.springframework.jdbc.core.RowMapper;

public class DeliverPostRowMapper implements RowMapper<DeliverPost> {
	public DeliverPost mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeliverPost deliverPost = new DeliverPost();
		deliverPost.setOrderId(rs.getString("order_id"));
		deliverPost.setPostId(rs.getString("post_id"));
		deliverPost.setArrived(rs.getBoolean("isArrived"));
		return deliverPost;
	}
}
