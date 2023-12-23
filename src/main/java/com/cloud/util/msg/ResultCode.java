package com.cloud.util.msg;

import lombok.Getter;

@Getter
public enum ResultCode {

	SUCCESS(200, "请求成功"),

	FAILED(500, "请求失败"),

	LOGIN_FAILED(500, "登录失败"),

	BODY_NOT_MATCH(400, "请求体格式错误"),

	TOKEN_EXPIRED(403, "token已过期"),

	TOKEN_LAPSE(403, "token已失效"),

	BAD_CREDENTIALS(403, "账号或密码错误"),

	DISABLE(403, "账户已被禁用"),

	LOCKED(403, "账户已被锁定"),

	VALIDATE_FAILED(405, "参数校验不通过"),

	VALIDATE_CODE_FAILED(405, "验证码错误"),

	VALIDATE_CODE_EXPIRED(405, "验证码已过期"),

	TOO_MANY_REQUESTS(200, "请求过于频繁"),

	ALREADY_REGISTERED(507, "该用户名已存在"),

	EMAIL_ALREADY_REGISTERED(507, "该邮箱已被注册"),

	ERROR(500, "未知错误");

	private final int code;
	private final String msg;

	ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
