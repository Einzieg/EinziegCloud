package com.cloud.util.mail;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

@Service
public class MailUtil {

	@Value("${spring.email.username}")
	private String USERNAME;
	@Value("${spring.email.password}")
	private String PASSWORD;
	@Value("${spring.email.host}")
	private String HOST;
	@Value("${spring.email.port}")
	private String PORT;

	TemplateEngine templateEngine = new TemplateEngine();

	/**
	 * 发送邮件
	 *
	 * @param to           接收方
	 * @param mailTemplate 邮件模板
	 */
	public void sendMail(String to, MailTemplate mailTemplate) throws MessagingException, IOException {
		System.out.println(USERNAME + " - " + PASSWORD + " - " + HOST + " - " + PORT);
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// 构建授权信息，用于进行SMTP进行身份验证
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		// 生成验证码
		String verifyCode = VerificationCodeUtil.generateVerificationCode();

		// 创建邮件内容
		String emailContent = loadTemplate(mailTemplate.getPath());

		Context context = new Context();
		context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
		context.setVariable("operation", mailTemplate.getOperation());

		// 模板引擎解析邮件模板
		String processedEmailContent = templateEngine.process(emailContent, context);

		// 创建邮件消息
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(USERNAME));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(mailTemplate.getTitle());
		message.setContent(processedEmailContent, "text/html;charset=UTF-8");
		// 发送邮件
		Transport.send(message);
	}

	/**
	 * 加载模板
	 *
	 * @param template 模板路径
	 * @return {@code String}
	 * @throws IOException IOException
	 */
	public String loadTemplate(String template) throws IOException {
		try (
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream(template);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								Objects.requireNonNull(inputStream),
								StandardCharsets.UTF_8
						)
				)
		) {
			StringBuilder emailContent = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				emailContent.append(line).append("\n");
			}
			return emailContent.toString();
		}
	}

}
