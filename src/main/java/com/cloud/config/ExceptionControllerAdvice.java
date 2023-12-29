package com.cloud.config;

import com.cloud.util.msg.Msg;
import com.cloud.util.msg.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	 * JWT-TOKEN过期异常
	 *
	 * @param e {@link ExpiredJwtException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(ExpiredJwtException.class)
	public Msg<?> handleExpiredJwtException(ExpiredJwtException e) {
		return Msg.fail(ResultCode.TOKEN_LAPSE);
	}

	/**
	 * JWT-TOKEN解析错误异常
	 *
	 * @param e {@link MalformedJwtException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(MalformedJwtException.class)
	public Msg<?> handleMalformedJwtException(MalformedJwtException e) {
		return Msg.fail(ResultCode.TOKEN_FORMAT_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Msg<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return new Msg<>(ResultCode.BODY_NOT_MATCH, e.getMessage());
	}

	/**
	 * 参数校验异常
	 *
	 * @param e {@link MethodArgumentNotValidException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Msg<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return new Msg<>(ResultCode.VALIDATE_FAILED, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public Msg<?> handleConstraintViolationException(ConstraintViolationException e) {
		return new Msg<>(ResultCode.VALIDATE_FAILED, e.getMessage());
	}

	/**
	 * 凭据无效异常
	 *
	 * @param e {@link BadCredentialsException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public Msg<?> handleBadCredentialsException(BadCredentialsException e) {
		return Msg.fail(ResultCode.BAD_CREDENTIALS);
	}

	/**
	 * 不支持的JWT-TOKEN
	 * @param e {@link UnsupportedJwtException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(UnsupportedJwtException.class)
	public Msg<?> handleUnsupportedJwtException(UnsupportedJwtException e) {
		return Msg.fail(ResultCode.TOKEN_UNSUPPORTED);
	}

	/**
	 * 账户禁用
	 *
	 * @param e {@link DisabledException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(DisabledException.class)
	public Msg<?> handleDisabledException(DisabledException e) {
		return Msg.fail(ResultCode.DISABLE);
	}

	/**
	 * 账户锁定异常
	 *
	 * @param e {@link LockedException}
	 * @return {@code Msg<?>}
	 */
	@ExceptionHandler(LockedException.class)
	public Msg<?> handleLockedException(LockedException e) {
		return Msg.fail(ResultCode.LOCKED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public Msg<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
		return Msg.fail(ResultCode.BAD_CREDENTIALS);
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
