package com.cloud.entity.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 身份验证请求
 *
 * @author Einzieg
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

	@NotNull(message = "邮箱不能为空")
	@Email(message = "邮箱格式不正确")
	private String email;

	@NotNull(message = "密码不能为空")
	@Size(min = 8, max = 16, message = "密码长度必须在8-16位之间")
	String password;
}
