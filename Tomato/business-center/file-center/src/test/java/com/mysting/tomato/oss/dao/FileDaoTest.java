package com.mysting.tomato.oss.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysting.tomato.FileCenterApp;
import com.mysting.tomato.oss.model.FileInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { FileCenterApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class FileDaoTest {

	@Resource
	private FileDao fileDao;

	@Test
	public void testFindById() {

		FileInfo fileInfo = fileDao.findById("96b29f59bcff2dbe5a7db4fff8735bfd");
		log.info("查询文件：" + fileInfo);

	}

}
