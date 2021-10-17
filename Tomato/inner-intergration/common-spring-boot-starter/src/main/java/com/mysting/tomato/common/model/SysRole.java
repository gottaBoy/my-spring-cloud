package com.mysting.tomato.common.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper=true)
public class SysRole extends Model<SysRole> implements Serializable{

	private static final long serialVersionUID = -3591576507384897451L;
	@JsonSerialize(using=ToStringSerializer.class)
	private Long id;
	private String code ;
	private String name;
	@TableField(value="create_time")
	private Date createTime;
	@TableField(value="update_time")
	private Date updateTime;
	
	
	@TableField(exist=false)
	private Long userId;
}
