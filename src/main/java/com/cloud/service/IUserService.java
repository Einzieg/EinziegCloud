package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.User;

import java.util.Optional;

public interface IUserService extends IService<User> {

	Optional<User> findUserByEmail(String email);

	Optional<User> findUserByName(String name);

}
