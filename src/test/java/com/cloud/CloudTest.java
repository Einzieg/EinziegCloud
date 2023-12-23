package com.cloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


// 忽略检查
@SuppressWarnings("ALL")
@SpringBootTest
@RunWith(SpringRunner.class)
class CloudTest {

	@BeforeEach
	public void setUp() {
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		int 零 = 0;

		List 一组 = List.of(1, 2, 3);
		List 二组 = List.of(1, 2, 3);
		List 三组 = List.of(1, 2, 3);

		for (int 一 = 零; 一 < 一组.size(); 一++) {
			for (int 二 = 零; 二 < 二组.size(); 二++) {
				for (int 三 = 零; 三 < 三组.size(); 三++) {
					System.out.println(一组.get(一) + " " + 二组.get(二) + " " + 三组.get(三));
				}
			}
		}
	}
}
