package com.mysting.tomato.user.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysting.tomato.UserCenterApp;
import com.mysting.tomato.common.model.SysMenu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { UserCenterApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class SysMenuDaoTest {

	@Resource
	private SysMenuDao sysMenuDao;

	@Test
	public void testFindById() {

		SysMenu sysMenu = sysMenuDao.findById(1L);
		log.info("查询菜单：" + sysMenu);

	}

}
