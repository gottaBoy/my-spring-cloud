package com.mysting.tomato.common.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_role_user")
@EqualsAndHashCode(callSuper=true)
public class SysUserRole  extends Model<SysUserRole> implements Serializable{

	private static final long serialVersionUID = 2096687235759960875L;
	
	@JsonSerialize(using=ToStringSerializer.class)
	private Long id;
	@TableField(value="user_id")
	@JsonSerialize(using=ToStringSerializer.class)
	private Long userId;
	@TableField(value="role_id")
	@JsonSerialize(using=ToStringSerializer.class)
    private Long roleId;

}
