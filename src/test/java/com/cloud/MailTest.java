package com.cloud;

import com.cloud.util.mail.MailTemplate;
import com.cloud.util.mail.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = EinziegCloudApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Service
public class MailTest {

	@Value("${spring.email.username}")
	private String USERNAME;
	@Value("${spring.email.password}")
	private String PASSWORD;
	@Value("${spring.email.host}")
	private String HOST;
	@Value("${spring.email.port}")
	private String PORT;

	@Autowired
	MailUtil mailUtil;

	@Test
	public void main() {
		String mail1 = "3101569132@qq.com";
		String mail2 = "1936343575@qq.com";
		try {
			mailUtil.sendMail(mail2, MailTemplate.REGISTER);
			// mailUtil.sendMail(mail2, MailTemplate.REGISTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1(){
		System.out.println(USERNAME + " - " + PASSWORD + " - " + HOST + " - " + PORT);
	}


}
