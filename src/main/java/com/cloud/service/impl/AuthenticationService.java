package com.cloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.response.AuthenticationResponse;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.entity.User;
import com.cloud.service.IAuthenticationService;
import com.cloud.service.IUserService;
import com.cloud.util.IPUtils;
import com.cloud.util.JwtUtil;
import com.cloud.util.Role;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 身份验证服务
 *
 * @author Einzieg
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

	private final IUserService userService;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;


	/**
	 * 用户注册
	 *
	 * @param request {@link RegisterRequest}
	 * @return {@code AuthenticationResponse}
	 */
	public Msg<?> register(HttpServletRequest request,RegisterRequest registerRequest) {
		if (userService.loadUserByEmail(registerRequest.getEmail()).isPresent()) {
			return Msg.fail("该用户已存在");
		}
		if (StringUtils.isNotEmpty(registerRequest.getVerificationCode())){
			return Msg.fail("验证码错误");
		}
		var user = User.builder()
				.email(registerRequest.getEmail())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(Role.USER)
				.build();
		user.setRegisterIp(IPUtils.getIpAddr(request));
		userService.save(user);
		var jwtToken = jwtUtil.generateToken(user);
		return Msg.success(AuthenticationResponse.builder().token(jwtToken).build());
	}

	/**
	 * 进行身份验证
	 *
	 * @param auth {@link AuthenticationRequest}
	 * @return {@code AuthenticationResponse}
	 */
	public Msg<?> login(HttpServletRequest request,AuthenticationRequest auth) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						auth.getEmail(),
						auth.getPassword()
				)
		);
		var user = userService.loadUserByEmail(auth.getEmail()).orElseThrow();
		var jwtToken = jwtUtil.generateToken(user);
		user.setToken(jwtToken);
		user.setLastLoginIp(IPUtils.getIpAddr(request));
		userService.updateById(user);
		return Msg.success(AuthenticationResponse.builder().token(jwtToken).build());
	}
}
