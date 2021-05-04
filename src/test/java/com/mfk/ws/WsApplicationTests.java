package com.mfk.ws;

import com.mfk.ws.config.Myconfig;
import com.mfk.ws.controller.WsController;
import com.mfk.ws.utils.ApplicationContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsApplicationTests {

/*	@Autowired
	DataSource dataSource;*/



	@Test
	public void contextLoads() {
//		System.out.println("========================="+dataSource.getClass());

		System.out.println("SpringBootVersion============="+SpringBootVersion.getVersion());
		System.out.println("SpringVersion============="+ SpringVersion.getVersion());
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WsApplication.class);
//		WsController ws = context.getBean(WsController.class);
////		ws.testBean("1");
	}
	@Test
	public void testIocBeans () {
		ApplicationContext ioc= ApplicationContextUtil.getApplicationContext();
		String[] cache = ioc.getBeanDefinitionNames();
		for (int i = 0; i < cache.length; i++) {
			System.out.println(cache[i]);
		}
		//System.out.println(cache.toString());
	}
	@Test
	public void testCacheImp () {
		ApplicationContext ioc= ApplicationContextUtil.getApplicationContext();
		CacheManager manager = ioc.getBean(CacheManager.class);
		System.out.println(manager);
	}


}
