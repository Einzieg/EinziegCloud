package com.cloud.service.impl;

import com.cloud.service.IEmailService;
import com.cloud.service.IUserService;
import com.cloud.util.RedisUtil;
import com.cloud.util.mail.MailTemplate;
import com.cloud.util.mail.MailUtil;
import com.cloud.util.msg.Msg;
import com.cloud.util.msg.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

	private final MailUtil mailUtil;
	private final RedisUtil redisUtil;
	private final IUserService userService;


	/**
	 * 注册邮件
	 *
	 * @param email 邮箱
	 * @return {@code Msg<?>}
	 */
	public Msg<?> sendMailToRegistration(String email) {
		if (userService.findUserByEmail(email).isPresent()) {
			return Msg.fail(ResultCode.EMAIL_ALREADY_REGISTERED);
		}
		Map<Object, Object> redisMap = redisUtil.getHash(email);
		if (redisMap.get("time") != null) {
			// 计算当前时间与redis中的时间差，如果小于60秒则不允许再次发送邮件
			if (System.currentTimeMillis() - Long.parseLong(redisMap.get("time").toString()) < 60000) {
				return Msg.fail(ResultCode.TOO_MANY_REQUESTS);
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
