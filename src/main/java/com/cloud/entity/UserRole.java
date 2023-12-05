package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("cloud_user_role")
public class UserRole {

	@TableId(value = "ID", type = IdType.AUTO)
	private String id;

	@TableField("USER_ID")
	private String userId;

	@TableField("ROLE_ID")
	private String roleId;

}
