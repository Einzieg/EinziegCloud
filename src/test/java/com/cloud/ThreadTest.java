package com.cloud;

import org.springframework.boot.test.context.SpringBootTest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ThreadTest {
	static List<Integer> list = new ArrayList<>();

	public static void main(String[] args) {
		// 开启线程 统计平台线程数
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
			ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
			updateMaxThreadNum(threadInfo.length);
		}, 10, 10, TimeUnit.MILLISECONDS);

		long start = System.currentTimeMillis();
		// 虚拟线程
		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
		// 使用平台线程
		// ExecutorService executor =  Executors.newFixedThreadPool(200);
		for (int i = 0; i < 1000000; i++) {
			executor.submit(() -> {
				try {
					// 线程睡眠 0.5 s，模拟业务处理
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException ignored) {
				}
			});
		}
		executor.close();
		System.out.println("max：" + list.get(0) + " platform thread/os thread");
		System.out.printf("totalMillis：%dms\n", System.currentTimeMillis() - start);


	}

	// 更新创建的平台最大线程数
	private static void updateMaxThreadNum(int num) {
		if (list.isEmpty()) {
			list.add(num);
		} else {
			Integer integer = list.get(0);
			if (num > integer) {
				list.add(0, num);
			}
		}
	}


}
