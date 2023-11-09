package com.cloud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

	private static final String SECRET_KEY =
			"B?E+ShuviDoura(H+MbQeShVmYq3t6w9z$C&F)J@NcRfUjWnZr4u7x!A%D*G-KaPdSgVkYp3s5vq3t6w9z$C&F)H@McQfTjWnZr4u7x!A%D*G-KaNdRgUkXp2s5v8y/B?E(H+MbQeSh";

	/**
	 * 提取所有声明
	 *
	 * @param token 令牌
	 * @return {@code Claims}
	 */
	private static Claims extractAllClaims(String token) {
		return Jwts.parserBuilder() // 获取一个Jwt解析器构建器
				.setSigningKey(getSignInKey())  // 设置Jwt验证签名
				.build()
				.parseClaimsJws(token)  // 解析Jwt令牌
				.getBody(); // 获取Jwt令牌的主体(Body)其中的声明信息
	}

	/**
	 * 获取签名密钥
	 *
	 * @return {@code Key} 用于验证Jwt签名的密钥。签名密钥必须与生成Jwt令牌时使用的密钥相同，否则无法正确验证Jwt的真实性。
	 */
	private static Key getSignInKey() {
		byte[] keyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}


	/**
	 * 从Jwt提取特定声明（Claims）信息
	 *
	 * @param token          令牌
	 * @param claimsResolver 解析器
	 * @return {@code T}
	 */
	public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);  // 获取JWT令牌中的所有声明信息,存储在Claims对象中
		return claimsResolver.apply(claims);
	}

	/**
	 * 生成Jwt令牌
	 *
	 * @param extraClaims 额外的声明信息
	 * @param userDetails 用户详细信息
	 * @return {@code String}
	 */
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()   // 获取一个JWT构建器
				.setClaims(extraClaims) // 设置JWT令牌的声明（Claims）部分
				.setSubject(userDetails.getUsername())  // 设置JWT令牌的主题（Subject）部分
				.setIssuedAt(new Date(System.currentTimeMillis())) // 设置JWT令牌的签发时间
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 设置JWT令牌的过期时间（24小时）
				.signWith(getSignInKey(), SignatureAlgorithm.HS512)   // 对JWT令牌进行签名
				.compact(); // 生成最终的JWT令牌字符串
	}

	/**
	 * 生成Jwt令牌
	 *
	 * @param userDetails 用户详细信息
	 * @return {@code String}
	 */
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	/**
	 * 令牌是否有效
	 *
	 * @param token       令牌
	 * @param userDetails 用户详细信息
	 * @return boolean
	 */
	public static boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !extractClaim(token, Claims::getExpiration).before(new Date()));
	}

	/**
	 * 提取用户名
	 *
	 * @param token 令牌
	 * @return {@code String}
	 */
	public static String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}


}
