package com.cloud.util.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作异常日志管理注解，用于AOP
 *
 * @author Einzieg
 */
@Target(ElementType.METHOD) // 注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) // 注解在哪个阶段执行
@Documented // 生成文档
public @interface OLog {
	String model() default ""; // 操作模块

	String type() default "";  // 操作类型

	String detail() default "";  // 操作说明
}
