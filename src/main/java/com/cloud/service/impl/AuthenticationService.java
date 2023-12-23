package com.cloud.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.entity.AuthenticationDTO;
import com.cloud.entity.User;
import com.cloud.entity.UserRole;
import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.entity.response.AuthenticationResponse;
import com.cloud.mapper.AuthenticationMapper;
import com.cloud.service.IAuthenticationService;
import com.cloud.service.IUserRoleService;
import com.cloud.service.IUserService;
import com.cloud.util.HttpUtil;
import com.cloud.util.IPUtil;
import com.cloud.util.JwtUtil;
import com.cloud.util.RedisUtil;
import com.cloud.util.msg.Msg;
import com.cloud.util.msg.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * 身份验证服务
 *
 * @author Einzieg
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService extends ServiceImpl<AuthenticationMapper, AuthenticationDTO> implements IAuthenticationService {

	private final IUserService userService;
	private final IUserRoleService userRoleService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	/**
	 * 用户注册
	 *
	 * @return {@code AuthenticationResponse}
	 */
	public Msg<?> register(RegisterRequest registerRequest) {
		log.info("\n================\nname: {}, email: {}, password: {}\n================", registerRequest.getUsername(), registerRequest.getEmail(),
				registerRequest.getPassword());
		if (userService.findUserByName(registerRequest.getUsername()).isPresent()) {
			return Msg.fail(ResultCode.ALREADY_REGISTERED);
		}
		if (userService.findUserByEmail(registerRequest.getEmail()).isPresent()) {
			return Msg.fail(ResultCode.EMAIL_ALREADY_REGISTERED);
		}
		Map<Object, Object> map;
		try {
			map = redisUtil.getHash(registerRequest.getEmail());
		} catch (IllegalArgumentException e) {
			return Msg.fail(ResultCode.VALIDATE_CODE_EXPIRED);
		}

		if (!registerRequest.getVerificationCode().equals(Optional.ofNullable(map.get("verifyCode")).orElse("").toString())) {
			return Msg.fail(ResultCode.VALIDATE_CODE_FAILED);
		}
		var user = User.builder()
				.email(registerRequest.getEmail())
				.name(registerRequest.getUsername())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.registerIp(IPUtil.getIpAddr())
				.build();
		userService.save(user);

		userRoleService.save(
				UserRole.builder()
						.userId(user.getId())
						.roleId("3")
						.build()
		);

		var authentication = loadUserByName(user.getName()).orElseThrow();
		redisUtil.set(authentication.getName(), JSON.toJSON(authentication).toString(), 60 * 60 * 24);
		redisUtil.del(registerRequest.getEmail());
		var jwtToken = jwtUtil.generateToken(authentication);
		return Msg.success(AuthenticationResponse.builder().token(jwtToken).build());
	}

	/**
	 * 登录身份验证
	 *
	 * @param auth {@link AuthenticationRequest}
	 * @return {@code AuthenticationResponse}
	 */
	public Msg<?> login(AuthenticationRequest auth) {
		log.info("name: {}, password: {}", auth.getUsername(), auth.getPassword());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						auth.getUsername(),
						auth.getPassword()
				)
		);
		var authentication = loadUserByRedis(auth.getUsername()).orElseThrow();
		var jwtToken = jwtUtil.generateToken(authentication);

		var user = userService.getById(authentication.getId());
		user.setLastLoginIp(IPUtil.getIpAddr());
		user.setToken(jwtToken);
		userService.updateById(user);
		return Msg.success(AuthenticationResponse.builder().token(jwtToken).build());
	}

	/**
	 * 用户登出
	 *
	 * @return {@code Msg<?>}
	 */
	public Msg<?> logout(HttpServletResponse response, HttpServletRequest request) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			log.info("用户 {} 登出", JwtUtil.extractUsername(request.getHeader("Authorization").substring(7)));
			redisUtil.del(auth.getName());
			// 清除认证
			new SecurityContextLogoutHandler().logout(HttpUtil.getHttpServletRequest(), response, auth);
		}
		return Msg.success();
	}

	/**
	 * 加载用户
	 *
	 * @param name 用户名
	 * @return {@code Optional<User>}
	 */
	@Override
	public Optional<AuthenticationDTO> loadUserByName(String name) {
		var auth = baseMapper.loadAuthentication(name);
		if (auth != null) {
			redisUtil.set(auth.getName(), JSON.toJSON(auth).toString(), 60 * 60 * 24);
		}
		return Optional.ofNullable(auth).isEmpty() ? Optional.empty() : Optional.of(auth);
	}

	public Optional<AuthenticationDTO> loadUserByRedis(String name) {
		var auth = JSON.parseObject(String.valueOf(redisUtil.get(name)), AuthenticationDTO.class);
		return null == auth ? loadUserByName(name) : Optional.of(auth);
	}

}
