package com.cloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cloud.entity.User;
import com.cloud.entity.request.AuthenticationRequest;
import com.cloud.entity.request.RegisterRequest;
import com.cloud.entity.response.AuthenticationResponse;
import com.cloud.service.IAuthenticationService;
import com.cloud.service.IUserService;
import com.cloud.util.IPUtils;
import com.cloud.util.JwtUtil;
import com.cloud.util.RedisUtil;
import com.cloud.util.Role;
import com.cloud.util.mail.MailTemplate;
import com.cloud.util.mail.MailUtil;
import com.cloud.util.msg.Msg;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证服务
 *
 * @author Einzieg
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

	private final IUserService userService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final MailUtil mailUtil;
	private final RedisUtil redisUtil;

	/**
	 * 用户注册
	 *
	 * @param request {@link RegisterRequest}
	 * @return {@code AuthenticationResponse}
	 */
	public Msg<?> register(HttpServletRequest request, RegisterRequest registerRequest) {
		if (userService.loadUserByEmail(registerRequest.getEmail()).isPresent()) {
			return Msg.fail("该用户已存在");
		}
		Map<Object,Object> map = redisUtil.getHash(registerRequest.getEmail());
		String verifyCode = map.get("verifyCode").toString();
		if (!registerRequest.getVerificationCode().equals(verifyCode)) {
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
	public Msg<?> login(HttpServletRequest request, AuthenticationRequest auth) {
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

	/**
	 * 发送验证码
	 *
	 * @param email 邮箱
	 * @return {@code Msg<?>}
	 */
	public Msg<?> sendVerificationCode(String email) {
		Map<Object, Object> redisMap = redisUtil.getHash(email);
		if (redisMap.get("time") != null) {
			// 计算当前时间与redis中的时间差，如果小于60秒则不允许再次发送邮件
			if (System.currentTimeMillis() - Long.parseLong(redisMap.get("time").toString()) < 60000) {
				return Msg.fail("请勿频繁发送邮件");
			}
		}
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("verifyCode", mailUtil.sendMail(email, MailTemplate.REGISTER));
			map.put("time", String.valueOf(System.currentTimeMillis()));
			redisUtil.setHash(email, map, 60 * 15);
			return Msg.success("邮件发送成功");
		} catch (Exception e) {
			log.error("发送邮件失败", e);
			return Msg.fail("邮件发送失败" + e.getMessage());
		}
	}

}
