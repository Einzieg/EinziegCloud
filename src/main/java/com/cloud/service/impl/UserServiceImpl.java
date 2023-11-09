package com.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.entity.User;
import com.cloud.mapper.UserMapper;
import com.cloud.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	/**
	 * 根据邮箱加载用户
	 * @param email 邮箱
	 * @return {@code Optional<User>}
	 */
	@Override
	public Optional<User> loadUserByEmail(String email) {
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getEmail, email);
		User user = this.getOne(queryWrapper);
		return Optional.ofNullable(user).isEmpty() ? Optional.empty() : Optional.of(user);
	}

}
