package com.cloud.controller;

import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.service.IAuthenticationService;
import com.cloud.util.annotation.OLog;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

	private final IAuthenticationService authenticationService;


	@PostMapping("/register")
	@OLog(model = "用户模块", detail = "用户注册")
	public Msg<?> register(@RequestBody @Validated RegisterRequest registerRequest) {
		return authenticationService.register(registerRequest);
	}

	@PostMapping("/login")
	@OLog(model = "用户模块", detail = "用户登录")
	public Msg<?> login(@RequestBody @Validated AuthenticationRequest auth) {
		return authenticationService.login(auth);
	}

	@GetMapping("/logout")
	@OLog(model = "用户模块", detail = "用户登出")
	public Msg<?> logout(HttpServletResponse response, HttpServletRequest request) {
		return authenticationService.logout(response, request);
	}


}
