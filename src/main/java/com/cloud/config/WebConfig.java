package com.cloud.config;

import com.cloud.util.interceptor.RepeatRequestIntercept;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final RepeatRequestIntercept repeatRequestIntercept;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(repeatRequestIntercept).addPathPatterns("/**");
	}
}
