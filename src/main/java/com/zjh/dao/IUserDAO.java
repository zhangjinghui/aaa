package com.zjh.dao;

import com.zjh.vo.User;

public interface IUserDAO {
	public User login(User user);
	public boolean register(String filePath,User user);
}
