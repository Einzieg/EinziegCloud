package com.cloud.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.entity.Log;
import com.cloud.mapper.LogMapper;
import com.cloud.service.ILogService;
import com.cloud.util.HttpUtil;
import com.cloud.util.IPUtil;
import com.cloud.util.JwtUtil;
import com.cloud.util.annotation.OLog;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

	@Override
	public void saveLog(ProceedingJoinPoint joinPoint, String outcome, Long currentTime, Object returnBody) {
		Long takeTime = System.currentTimeMillis() - currentTime;
		HttpServletRequest request = HttpUtil.getHttpServletRequest();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		OLog oLog = signature.getMethod().getAnnotation(OLog.class);
		// 方法名
		String methodName = joinPoint.getSignature().getName();
		assert request != null;
		String name = switch (methodName) {
			case "login", "register" -> JSONObject.parseObject(JSONObject.toJSONString(List.of(joinPoint.getArgs()).get(0))).getString("username");
			case "logout" -> JwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
			case "mail" -> Arrays.asList(joinPoint.getArgs()).get(0).toString();
			default -> SecurityContextHolder.getContext().getAuthentication().getName();
		};
		Log olog = Log.builder()
				.outcome(outcome)
				.createTime(new Date())
				.takeTime(takeTime)
				.httpMethod(request.getMethod())
				.classMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + methodName)
				.requestUrl(request.getRequestURI())
				.ip(IPUtil.getIpAddr())
				.user(name)
				.model(oLog.model())
				.detail(oLog.detail())
				.returnBody(returnBody.toString())
				.build();

		log.debug("log: {}", olog);
		this.save(olog);
	}

	public Msg<?> getLogs(Log olog, Integer current, Integer size) {
		return Msg.success(
				this.page(
						new Page<>(current, size),
						new QueryWrapper<>(olog).orderByDesc("CREATE_TIME")
				)
		);
	}

}
