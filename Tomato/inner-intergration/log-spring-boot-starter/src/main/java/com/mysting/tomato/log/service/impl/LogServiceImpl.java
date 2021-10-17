package com.mysting.tomato.log.service.impl;

import java.util.Date;

import com.mysting.tomato.log.dao.LogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.mysting.tomato.common.model.SysLog;
import com.mysting.tomato.datasource.annotation.DataSource;
import com.mysting.tomato.log.service.LogService;

/**
 * 切换数据源，存储log-center
 */
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	@Async
	@Override
	@DataSource(name="log")
	public void save(SysLog log) {
		if (log.getCreateTime() == null) {
			log.setCreateTime(new Date());
		}
		if (log.getFlag() == null) {
			log.setFlag(Boolean.TRUE);
		}
		logDao.save(log);
	}
}
