package com.cloud.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

	private static int num = 0;

	@Override
	public Health health() {
		int errorCode = check(); // 进行一些特定的健康检查
		if (errorCode != 0) {
			return Health.down().withDetail("Error Code", errorCode).build();
		}
		return Health.up().build();
	}

	// 这里模拟检查，设置为一次正常一次异常
	private int check() {
		num++;
		return num % 2;
	}

}

