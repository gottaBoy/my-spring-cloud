package com.mysting.tomato.eureka;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.mysting.tomato.eureka.config.TestEurekaConfiguration;



@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestEurekaConfiguration.class)
public class EurekaServerAppTest {

	@Autowired
	private ApplicationContext context;
	
	@Test
	public void testNull(){
		Assert.assertNotNull(context.getBean(Runnable.class));
	}
	


}
