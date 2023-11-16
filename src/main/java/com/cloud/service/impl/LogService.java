package com.cloud.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.entity.Log;
import com.cloud.mapper.LogMapper;
import com.cloud.service.ILogService;
import com.cloud.util.IPUtils;
import com.cloud.util.annotation.OLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService extends ServiceImpl<LogMapper, Log> implements ILogService {

	@Override
	public void saveLog(ProceedingJoinPoint joinPoint, String outcome, Long currentTime, Object returnBody) {
		Long takeTime = System.currentTimeMillis() - currentTime;
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		OLog oLog = signature.getMethod().getAnnotation(OLog.class);
		// 方法名
		String methodName = joinPoint.getSignature().getName();

		List<Object> argValues = List.of(joinPoint.getArgs());
		log.info("argValues: {}", argValues);

		String name = switch (methodName) {
			case "login", "register" -> JSONObject.parseObject(JSONObject.toJSONString(argValues.get(1))).getString("email");
			case "mail" -> argValues.get(0).toString();
			default -> SecurityContextHolder.getContext().getAuthentication().getName();
		};
		assert request != null;
		var olog = Log.builder()
				.outcome(outcome)
				.createTime(new Date())
				.takeTime(takeTime)
				.httpMethod(request.getMethod())
				.classMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + methodName)
				.requestUrl(request.getRequestURI())
				.ip(IPUtils.getIpAddr(request))
				.user(name)
				.model(oLog.model())
				.detail(oLog.detail())
				.returnBody(returnBody.toString())
				.build();

		log.info("log: {}", olog);
		this.save(olog);
	}

}
