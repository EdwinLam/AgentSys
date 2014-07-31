package com.edwin.agentsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edwin.agentsys.dao.CartDao;
import com.edwin.agentsys.model.Cart;


/*
 * Service层，在此可以进行事物管理
 */
@Component("cartService")
public class CartService {
	@Resource(name="cartDao")
	private CartDao dao;
	@Transactional
	public boolean insertCart(Cart cart){
		dao.insertCart(cart);
		return true;
	}
	@Transactional
	public boolean deleteById(long id){
		dao.deleteById(id);
		return true;
	}
	@Transactional
	public boolean  updateCart(Cart cart){
		dao.Update(cart);
		return true;
	}
	@Transactional
	public List<Cart> findAll(){
		return dao.findAll();
	}

	@Transactional
	public Cart findById(long id){
		return dao.findById(id);
	}
	
	@Transactional
	public List<Cart> findByUserId(int userId){
		return dao.findByUserId(userId);
	}
	}
