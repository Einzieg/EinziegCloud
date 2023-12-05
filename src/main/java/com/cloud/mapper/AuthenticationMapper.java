package com.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.entity.AuthenticationDTO;

/**
 * 用户Mapper
 *
 * @author Einzieg
 * @date 2023/11/09
 */
public interface AuthenticationMapper extends BaseMapper<AuthenticationDTO> {

	AuthenticationDTO loadAuthentication(String name);
}
