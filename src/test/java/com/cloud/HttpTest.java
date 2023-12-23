package com.cloud;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.DateUtils;

import java.util.Date;
import java.util.Locale;


@SpringBootTest
public class HttpTest {

	@Test
	public void test() {
		System.out.println(DateUtils.format(new Date(), Locale.CHINA));
	}

}
