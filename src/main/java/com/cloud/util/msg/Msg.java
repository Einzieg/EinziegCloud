package com.cloud.util.msg;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一返回结果类
 *
 * @author Einzieg
 * @date 2023/07/14
 */
@Data
@AllArgsConstructor
public class Msg<T> {

	// 状态码
	private Integer code;

	// 提示信息
	private String msg;

	// 返回给浏览器的数据
	private T data;

	// 接口请求时间
	private long timestamp;

	public Msg() {
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * 默认请求成功
	 *
	 * @return {@code Msg<?>}
	 */
	public static Msg<?> success() {
		Msg<?> msg = new Msg<>();
		msg.setCode(ResultCode.SUCCESS.getCode());
		msg.setMsg(ResultCode.SUCCESS.getMsg());
		return msg;
	}

	/**
	 * 请求成功
	 *
	 * @param resultCode {@code ResultCode}
	 * @return {@code Msg<?>}
	 */
	public static Msg<?> success(ResultCode resultCode) {
		Msg<?> result = new Msg<>();
		result.setCode(ResultCode.SUCCESS.getCode());
		result.setMsg(ResultCode.SUCCESS.getMsg());
		return result;
	}

	/**
	 * 请求成功
	 * 携带数据返回
	 *
	 * @param data 返回数据
	 * @return {@code Msg<T>}
	 */
	public static <T> Msg<T> success(T data) {
		Msg<T> msg = new Msg<>();
		msg.setCode(ResultCode.SUCCESS.getCode());
		msg.setMsg(ResultCode.SUCCESS.getMsg());
		msg.setData(data);
		return msg;
	}

	/**
	 * 默认请求失败
	 *
	 * @return {@code Msg<?>}
	 */
	public static Msg<?> fail() {
		Msg<?> msg = new Msg<>();
		msg.setCode(ResultCode.FAILED.getCode());
		msg.setMsg(ResultCode.FAILED.getMsg());
		return msg;
	}

	/**
	 * 定义请求失败
	 *
	 * @param resultCode {@code ResultCode}
	 * @return {@code Msg<?>}
	 */
	public static Msg<?> fail(ResultCode resultCode) {
		Msg<?> result = new Msg<>();
		result.setCode(resultCode.getCode());
		result.setMsg(resultCode.getMsg());
		return result;
	}

	/**
	 * 请求失败
	 * 携带数据返回
	 *
	 * @param data 返回数据
	 * @return {@code Msg<T>}
	 */
	public static <T> Msg<T> fail(T data) {
		Msg<T> msg = new Msg<>();
		msg.setCode(ResultCode.FAILED.getCode());
		msg.setMsg(ResultCode.FAILED.getMsg());
		msg.setData(data);
		return msg;
	}

	/**
	 * 自定义返回状态码、消息、数据
	 *
	 * @param resultCode {@code ResultCode}
	 * @param data       数据
	 */
	public Msg(ResultCode resultCode, T data) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
		this.data = data;
		this.timestamp = System.currentTimeMillis();
	}

}
