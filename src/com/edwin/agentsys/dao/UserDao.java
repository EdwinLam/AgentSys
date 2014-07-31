package com.edwin.agentsys.dao;

import java.util.List;

import com.edwin.agentsys.model.User;


public interface UserDao {

	public  void insertUser(User user);

	public  void deleteById(long id);

	public  List<User> findAll();

	public  void Update(User user);
	
	public List<User> findByPhone(String phone);
		
	public User findById(long id);

}