package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.AuthenticationDTO;
import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface IAuthenticationService extends IService<AuthenticationDTO> {

	Msg<?> register(RegisterRequest registerRequest);

	Msg<?> login(AuthenticationRequest auth);

	Msg<?> logout(HttpServletResponse response);

	Optional<AuthenticationDTO> loadUserByName(String Name);

	Optional<AuthenticationDTO> loadUserByRedis(String Name);



}
