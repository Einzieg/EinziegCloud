package com.cloud.controller;

import com.cloud.service.IEmailService;
import com.cloud.util.annotation.OLog;
import com.cloud.util.msg.Msg;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {

	private final IEmailService emailService;


	@GetMapping("/register")
	@OLog(model = "邮箱模块", detail = "注册邮件")
	public Msg<?> mail(@Email @RequestParam String email) {
		return emailService.sendMailToRegistration(email);
	}

}
