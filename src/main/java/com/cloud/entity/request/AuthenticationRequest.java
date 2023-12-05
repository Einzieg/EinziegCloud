package com.cloud.entity.request;

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

	@NotNull(message = "用户名不能为空")
	private String username;

	@NotNull(message = "密码不能为空")
	@Size(min = 8, max = 16, message = "密码长度必须在8-16位之间")
	String password;
}
