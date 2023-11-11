package com.cloud;

import com.cloud.util.RedisUtil;
import com.cloud.util.mail.VerificationCodeUtil;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = EinziegCloudApplication.class)
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class RedisTest {

	@Resource
	RedisUtil redisUtil;

	@Test
	public void redisTest() {
		Map<String,Object> map = new HashMap<>();
		map.put("verifyCode", VerificationCodeUtil.generateVerificationCode());
		map.put("time",String.valueOf(System.currentTimeMillis()));
		System.out.println(redisUtil.setHash("1936343575wang@gmail.com",map,180));
		System.out.println(redisUtil.getHash("1936343575wang@gmail.com"));

		Map<Object,Object> map2 = redisUtil.getHash("1936343575wang@gmail.com");
		System.out.println(map2.get("verifyCode"));
		System.out.println(map2.get("time"));

		// 计算当前时间与redis中的时间差，如果小于60秒则不允许再次发送邮件
		// System.out.println(System.currentTimeMillis() - Long.parseLong(map2.get("time").toString()));   // 先判空，再转换类型
		// System.out.println(System.currentTimeMillis() - Long.parseLong(map2.get("time").toString()) < 60000);
		// System.out.println(redisUtil.set("Captcha-1936343575wang@gmail.com", "L23ES1", 60));
		// System.out.println(redisUtil.get("Captcha-1936343575wang@gmail.com"));
	}

}
