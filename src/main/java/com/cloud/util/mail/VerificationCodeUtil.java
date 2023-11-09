package com.cloud.util.mail;

import java.util.Random;

/**
 * 验证码生成工具类
 *
 * @author Einzieg
 * @date 2023/11/08
 */
public class VerificationCodeUtil {

	private static final int GENERATE_VERIFICATION_CODE_LENGTH = 6;

	private static final String[] MATE_CODE = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z"};

	public static String generateVerificationCode() {
		Random random = new Random();
		StringBuilder verificationCode = new StringBuilder();
		while (verificationCode.length() < GENERATE_VERIFICATION_CODE_LENGTH) {
			verificationCode.append(MATE_CODE[random.nextInt(MATE_CODE.length)]);
		}
		return verificationCode.toString();
	}

	public static void main(String[] args) {
		System.out.println(generateVerificationCode());
	}
}
