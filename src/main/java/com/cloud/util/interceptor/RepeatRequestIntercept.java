package com.cloud.util.interceptor;

import com.cloud.util.IPUtil;
import com.cloud.util.RedisUtil;
import com.cloud.util.msg.Msg;
import com.cloud.util.msg.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * 接口防刷拦截器
 *
 * @author Einzieg
 * @date 2023/11/21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RepeatRequestIntercept implements HandlerInterceptor {

	private static final int MAX_REQUEST_COUNT = 10;
	private static final long EXPIRE_TIME = 10;

	private final RedisUtil redisUtil;

	public boolean preHandle(@NonNull HttpServletRequest request,
	                         @NonNull HttpServletResponse response,
	                         @NonNull Object handler) throws Exception {
		// 判断请求是否为方法的请求
		if (handler instanceof HandlerMethod) {
			String key = IPUtil.getIpAddr() + "-" + request.getMethod() + "-" + request.getRequestURL();
			log.info("==> {}", key);
			Object requestCountObj = redisUtil.get(key);
			if (Objects.isNull(requestCountObj)) {
				// 若为空则为第一次请求
				redisUtil.set(key, 1, EXPIRE_TIME);
			} else {
				// 限定时间内的第n次请求
				int requestCount = Integer.parseInt(requestCountObj.toString());
				// 判断是否超过最大限定请求次数
				if (requestCount < MAX_REQUEST_COUNT) {
					// 未超过则请求次数+1
					redisUtil.increasing(key, 1);
				} else {
					// 否则拒绝请求并返回信息
					Msg.returnMsg(response, ResultCode.TOO_MANY_REQUESTS);
					return false;
				}
			}
		}
		return true;
	}

}
