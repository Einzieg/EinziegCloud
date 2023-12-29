package com.cloud.config;

import com.cloud.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * jwt身份验证过滤器
 *
 * @author Einzieg
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})  // @Lazy: 用于解决循环依赖问题
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;

	/**
	 * 在Spring Boot由于全局异常处理<code>@RestControllerAdvice</code>只会去捕获所有<code>Controller</code>层抛出的异常，所以在<code>filter</code>当中抛出的异常异常类是没有感知的。
	 * <p>
	 * 所以需要将异常转移到Controller层，然后由全局异常处理器去捕获异常并处理。
	 */
	@Qualifier("handlerExceptionResolver")
	private final HandlerExceptionResolver resolver;


	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                @NonNull HttpServletResponse response,
	                                @NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userName;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// resolver.resolveException(request, response, null, new ExpiredJwtException(null, null, null));
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		try {
			userName = JwtUtil.extractUsername(jwt);
		} catch (ExpiredJwtException e) {
			System.out.println("jwt过期===================" + e);
			resolver.resolveException(request, response, null, new ExpiredJwtException(null, null, null));
			return;
		} catch (UnsupportedJwtException e) {
			System.out.println("jwt不支持===================" + e);
			resolver.resolveException(request, response, null, new UnsupportedJwtException(null, null));
			return;
		} catch (MalformedJwtException e) {
			System.out.println("jwt格式错误===================" + e);
			resolver.resolveException(request, response, null, new MalformedJwtException(null, null));
			return;
		} catch (SignatureException e) {
			System.out.println("jwt签名错误===================" + e);
			resolver.resolveException(request, response, null, new SignatureException(null, null));
			return;
		} catch (Exception e) {
			System.out.println("jwt验证失败===================" + e);
			resolver.resolveException(request, response, null, new Exception("凭证解析失败", null));
			return;
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails;
			try {
				userDetails = userDetailsService.loadUserByUsername(userName);
			} catch (UsernameNotFoundException e) {
				resolver.resolveException(request, response, null, new UsernameNotFoundException(null));
				return;
			}
			if (!userDetails.isAccountNonLocked() || !userDetails.isEnabled()) {
				// TODO 重写返回信息
				filterChain.doFilter(request, response);
				return;
			}
			if (JwtUtil.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
				);
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}


}
