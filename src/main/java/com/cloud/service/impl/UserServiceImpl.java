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
	 *
	 * @param email 邮箱
	 * @return {@code Optional<User>}
	 */
	@Override
	public Optional<User> findUserByEmail(String email) {
		var user = getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
		return Optional.ofNullable(user).isEmpty() ? Optional.empty() : Optional.of(user);
	}

	/**
	 * 根据用户名加载用户
	 *
	 * @param name 用户名
	 * @return {@code Optional<User>}
	 */
	@Override
	public Optional<User> findUserByName(String name) {
		var user = getOne(new LambdaQueryWrapper<User>().eq(User::getName, name));
		return Optional.ofNullable(user).isEmpty() ? Optional.empty() : Optional.of(user);
	}


}
