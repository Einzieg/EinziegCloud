package com.cloud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.entity.Log;
import com.cloud.service.impl.LogServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(classes = EinziegCloudApplication.class)
@RunWith(SpringRunner.class)
public class LogTest {

	@Resource
	LogServiceImpl logServiceImpl;

	@Test
	public void logTest() {
		Log olog = Log.builder()
				// .outcome("error")
				// .createTime(null)
				// .httpMethod("GET")
				// .classMethod("com.cloud.controller.UserController.login")
				// .requestUrl("/cloud/auth/login")
				.build();
		QueryWrapper<Log> wrapper = new QueryWrapper<>(olog);
		Page<Log> page = new Page<>(1, 10);
		wrapper.orderByDesc("CREATE_TIME");
		IPage<Log> iPage = logServiceImpl.page(page, wrapper);
		System.out.println(iPage);
		log.info("logs: {}", iPage.toString());

	}

	@Test
	public void pt() {
		System.out.println("test");
	}
}
