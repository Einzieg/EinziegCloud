package com.cloud.entity.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


/**
 * 身份验证请求
 *
 * @author Einzieg
 */
@Data
@Builder
public class AuthenticationRequest {

	@NotNull(message = "用户名不能为空")
	@Size(min = 1, message = "用户名不能为空")
	private String username;

	@NotNull(message = "密码不能为空")
	@Size(min = 1, message = "密码不能为空")
	private String password;
}
