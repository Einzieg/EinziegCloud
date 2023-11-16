package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cloud.util.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("CLOUD_USER")
public class User implements Serializable, UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;

	@TableField("EMAIL")
	private String email;

	@TableField("PASSWORD")
	private String password;

	@TableField("NAME")
	private String name;

	@EnumValue()
	private Role role;

	@TableField(value = "REGISTER_DATE", fill = FieldFill.INSERT)
	private Date registerDate;

	@TableField(value = "LAST_LOGIN_DATE", fill = FieldFill.INSERT_UPDATE)
	private Date lastLoginDate;

	@TableField(value = "REGISTER_IP")
	private String registerIp;

	@TableField(value = "LAST_LOGIN_IP")
	private String lastLoginIp;

	@TableField(value = "STATE", fill = FieldFill.INSERT)
	private Boolean state;

	@TableField(value = "LOCKED", fill = FieldFill.INSERT)
	private Boolean locked;

	@JsonIgnore
	@TableField(value = "TOKEN")
	private String token;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return email;
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
