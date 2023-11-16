package com.cloud.util.msg;

import lombok.Getter;

@Getter
public enum ResultCode {
	SUCCESS(200, "请求成功"),
	FAILED(500, "请求失败"),
	TOKEN_EXPIRED(403, "token已过期"),
	BAD_CREDENTIALS(403, "账号或密码错误"),
	DISABLE(403, "账户已被禁用"),
	LOCKED(403, "账户已被锁定"),
	VALIDATE_FAILED(405, "参数检验失败"),
	VALIDATE_CODE_FAILED(405, "验证码错误"),
	VALIDATE_CODE_EXPIRED(405, "验证码已过期"),
	TOO_MANY_REQUESTS(429, "请求过于频繁"),
	ALREADY_REGISTERED(507, "该用户已存在"),
	ERROR(500, "未知错误");

	private final int code;
	private final String msg;

	ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
