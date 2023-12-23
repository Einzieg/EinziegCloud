package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling   // 开启定时任务功能
@EnableAsync        // 开启异步支持
@EnableCaching      // 开启Redis缓存支持
@MapperScan({"com.cloud.mapper"})
public class EinziegCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EinziegCloudApplication.class, args);
	}

	/**
	 * 配置Tomcat使用虚拟线程
	 *
	 * @return {@code TomcatProtocolHandlerCustomizer<?>}
	 */
	@Bean
	public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
		return protocolHandler -> {
			System.out.printf("""
					============================================================================================================
					|| ==> Configuring %s to use VirtualThreadPerTaskExecutor
					============================================================================================================
					""", protocolHandler);
			protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
		};
	}

}
