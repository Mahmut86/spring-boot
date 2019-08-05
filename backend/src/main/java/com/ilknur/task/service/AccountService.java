package com.ilknur.task.service;

import com.ilknur.task.entities.User;

public interface AccountService {
	public User saveUser(User user);
	public User findUserByUserName(String username);
}
