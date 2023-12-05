package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cloud_role")
public class Role {

	@TableId(value = "ID", type = IdType.AUTO)
	private String id;

	@TableField("ROLE_CODE")
	private String code;

	@TableField("ROLE_NAME")
	private String name;

	@TableField("ROLE_LEVEL")
	private Integer level;

}
