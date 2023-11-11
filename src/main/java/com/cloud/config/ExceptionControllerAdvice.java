package com.cloud.config;

import com.cloud.util.msg.Msg;
import com.cloud.util.msg.ResultCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class ExceptionControllerAdvice {

	/**
	 * 参数校验异常
	 *
	 * @param e {@link MethodArgumentNotValidException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Msg<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("参数校验异常: {}", Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e);
		return new Msg<>(ResultCode.VALIDATE_FAILED, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public Msg<?> handleConstraintViolationException(ConstraintViolationException e) {
		log.error("参数校验异常: {}", e.getMessage(), e);
		return new Msg<>(ResultCode.VALIDATE_FAILED, e.getMessage());
	}

	@ExceptionHandler(DisabledException.class)
	public Msg<?> handleDisabledException(DisabledException e) {
		log.error("账户被禁用: {}", e.getMessage(), e);
		return new Msg<>(ResultCode.FAILED, e.getMessage());
	}

	/**
	 * 系统异常 预期以外异常
	 */
	@ExceptionHandler(Exception.class)
	public Msg<?> handleUnexpectedServer(Exception e) {
		log.error("系统异常: {}", e.getMessage(), e);
		return new Msg<>(ResultCode.ERROR, e.getMessage());
	}

	/**
	 * 所有异常的拦截
	 */
	@ExceptionHandler(Throwable.class)
	public Msg<?> exception(Throwable e) {
		log.error("抛出异常: {}", e.getMessage(), e);
		return new Msg<>(ResultCode.ERROR, e.getMessage());
	}


}
