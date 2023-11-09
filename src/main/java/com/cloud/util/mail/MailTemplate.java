package com.cloud.util.mail;

import lombok.Getter;

@Getter
public enum MailTemplate {
	REGISTER("验证码","注册账号", "templates/emailTemplates/EmailVerificationCode.html"),
	FORGET("验证码","找回密码", "templates/emailTemplates/EmailVerificationCode.html"),
	CHANGE("验证码","修改密码", "templates/emailTemplates/EmailVerificationCode.html");

	private final String title;
	private final String operation;
	private final String path;

	MailTemplate(String title, String operation, String path) {
		this.title = title;
		this.operation = operation;
		this.path = path;
	}
}
