package com.cloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class CloudTest {

	@BeforeEach
	public void setUp() {
	}

	@Test
	void contextLoads() {
		System.out.println("Test");
	}
}
