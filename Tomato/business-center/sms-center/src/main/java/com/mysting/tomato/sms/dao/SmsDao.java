package com.mysting.tomato.sms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.mysting.tomato.sms.model.Sms;

/**
 * * 程序名 : SmsDao
 * 建立日期: 2018-07-09
 * 作者 : someday
 * 模块 : 短信中心
 * 描述 : 短信crud
 * 备注 : version20180709001
 * <p>
 * 修改历史
 * 序号 	       日期 		        修改人 		         修改原因
 */
@Mapper
public interface SmsDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_sms(phone, sign_name, template_code, params, day, create_time, update_time) "
			+ "values(#{phone}, #{signName}, #{templateCode}, #{params}, #{day}, #{createTime}, #{updateTime})")
	int save(Sms sms);

	@Select("select * from sys_sms t where t.id = #{id}")
	Sms findById(Long id);

	int update(Sms sms);

	int count(Map<String, Object> params);

	List<Sms> findList(Map<String, Object> params);
}
