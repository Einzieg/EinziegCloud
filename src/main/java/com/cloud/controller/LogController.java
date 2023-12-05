package com.cloud.controller;

import com.cloud.entity.Log;
import com.cloud.service.ILogService;
import com.cloud.util.annotation.OLog;
import com.cloud.util.msg.Msg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {

	private final ILogService logService;

	@GetMapping("/get")
	@OLog(model = "日志模块", detail = "获取日志")
	public Msg<?> getLog(@RequestParam(value = "current", defaultValue = "1") Integer current,
	                     @RequestParam(value = "size", defaultValue = "10") Integer size,
	                     Log olog) {
		log.info("olog:{}，{}， {}", olog, current, size);
		return logService.getLogs(olog, current, size);
	}

}
