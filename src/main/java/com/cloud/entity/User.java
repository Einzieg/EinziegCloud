package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cloud_user")
public class User {

	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;

	@TableField("EMAIL")
	private String email;

	@TableField("PASSWORD")
	private String password;

	@TableField("NAME")
	private String name;

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


}
