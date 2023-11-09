package com.cloud.util.msg;

import lombok.Getter;

@Getter
public enum ResultCode {
	SUCCESS(200, "请求成功"),
	FAILED(500, "请求失败"),
	VALIDATE_FAILED(405, "参数检验失败"),
	ERROR(500, "未知错误");

	private final int code;
	private final String msg;

	ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
