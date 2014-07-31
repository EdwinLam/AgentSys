package com.edwin.agentsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edwin.agentsys.dao.UserDao;
import com.edwin.agentsys.model.User;


/*
 * Service层，在此可以进行事物管理
 */
@Component("userService")
public class UserService {
	@Resource(name="userDao")
	private UserDao dao;
	@Transactional
	public boolean insertUser(User user){
		dao.insertUser(user);
		return true;
	}
	@Transactional
	public boolean deleteById(long id){
		dao.deleteById(id);
		return true;
	}
	@Transactional
	public boolean  updateUser(User user){
		dao.Update(user);
		return true;
	}
	@Transactional
	public List<User> findAll(){
		return dao.findAll();
	}
	@Transactional
	public List<User> findByPhone(String phone){
		return dao.findByPhone(phone);
	}
	@Transactional
	public User findById(long id){
		return dao.findById(id);
	}
	}
