package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;

public interface ILogService extends IService<Log> {

	void saveLog(ProceedingJoinPoint joinPoint, String outcome, Long currentTime, Object returnBody);
}
