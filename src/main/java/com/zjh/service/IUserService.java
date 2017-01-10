package com.zjh.service;

import javax.servlet.http.HttpServletRequest;

import com.zjh.vo.User;

public interface IUserService {
	public User login(User user);
	public boolean register(HttpServletRequest request);
}
