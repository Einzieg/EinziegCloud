package com.cloud.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class AuthenticationDTO implements Serializable, UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;

	private String id;

	private String email;

	private String password;

	private String name;

	private Boolean state;

	private Boolean locked;

	private String roleCode;

	private String roleName;

	private String roleLevel;


	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleCode != null ? List.of((GrantedAuthority) () -> roleLevel) : null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * 账户未过期
	 *
	 * @return boolean
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 账户非锁定
	 *
	 * @return boolean
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	/**
	 * 凭证未过期
	 *
	 * @return boolean
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否启用
	 *
	 * @return boolean
	 */
	@Override
	public boolean isEnabled() {
		return !state;
	}
}
