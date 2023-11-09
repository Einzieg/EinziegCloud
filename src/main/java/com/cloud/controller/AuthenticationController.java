package com.cloud.controller;

import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.service.IAuthenticationService;
import com.cloud.util.annotation.OLog;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

	private final IAuthenticationService authenticationService;

	@PostMapping("/register")
	@OLog(model = "用户模块", type = "注册", detail = "用户注册")
	public Msg<?> register(HttpServletRequest request, @Validated RegisterRequest registerRequest) {
		return authenticationService.register(request, registerRequest);
	}

	@PostMapping("/login")
	@OLog(model = "用户模块", type = "登录", detail = "用户登录")
	public Msg<?> login(HttpServletRequest request, AuthenticationRequest auth) {
		return authenticationService.login(request, auth);
	}


}
