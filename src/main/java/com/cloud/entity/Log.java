package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("cloud_log")
public class Log {

	/**
	 * 主键
	 */
	@TableId(value = "ID", type = IdType.AUTO)
	private Long id;

	/**
	 * 运行结果
	 */
	@TableField(value = "OUTCOME")
	private String outcome;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME")
	private Date createTime;

	/**
	 * 耗时
	 */
	@TableField(value = "TAKE_TIME")
	private Long takeTime;

	/**
	 * http请求方法
	 */
	@TableField(value = "HTTP_METHOD")
	private String httpMethod;

	/**
	 * 类方法
	 */
	@TableField(value = "CLASS_METHOD")
	private String classMethod;

	/**
	 * 请求URL
	 */
	@TableField(value = "REQUEST_URL")
	private String requestUrl;

	/**
	 * 请求IP
	 */
	@TableField(value = "IP")
	private String ip;

	/**
	 * 用户
	 */
	@TableField(value = "USER")
	private String user;

	/**
	 * 模块
	 */
	@TableField(value = "MODEL")
	private String model;

	/**
	 * 说明
	 */
	@TableField(value = "DETAIL")
	private String detail;

	@TableField(value = "RETURN_BODY")
	private String returnBody;
}
