package com.cloud.controller;

import com.cloud.util.annotation.OLog;
import com.cloud.util.msg.Msg;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	@GetMapping("/hello")
	@OLog(model = "用户模块", detail = "测试")
	public Msg<String> hello() {
		return Msg.success("Hello World");
	}

}
