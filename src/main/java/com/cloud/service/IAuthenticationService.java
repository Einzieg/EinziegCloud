package com.cloud.service;

import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

	Msg<?> register(HttpServletRequest request, RegisterRequest registerRequest);

	Msg<?> login(HttpServletRequest request, AuthenticationRequest auth);

	Msg<?> sendVerificationCode(String email);

}
