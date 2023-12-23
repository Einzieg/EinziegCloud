package com.cloud.entity.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * 注册请求
 *
 * @author Einzieg
 */
@Data
@Builder
public class RegisterRequest {

	@Size(min = 1, message = "用户名不能为空")
	private String username;

	@Size(min = 6, message = "邮箱格式不正确")
	@Email(message = "邮箱格式不正确")
	private String email;

	@Size(min = 8, max = 16, message = "密码长度必须在8-16位之间")
	private String password;

	@Size(min = 6, max = 6, message = "验证码错误")
	private String verificationCode;

}
