package com.cloud.service;

import com.cloud.util.msg.Msg;

public interface IEmailService {

	Msg<?> sendMailToRegistration(String email);
}
