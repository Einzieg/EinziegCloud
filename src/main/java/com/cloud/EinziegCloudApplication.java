package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   // 开启定时任务功能
@EnableAsync        // 开启异步支持
@EnableCaching      // 开启Redis缓存支持
@MapperScan({"com.cloud.mapper"})
public class EinziegCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EinziegCloudApplication.class, args);
	}

}
