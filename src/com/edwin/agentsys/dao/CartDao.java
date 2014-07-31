package com.edwin.agentsys.dao;

import java.util.List;

import com.edwin.agentsys.model.Cart;


public interface CartDao {

	public  void insertCart(Cart cart);

	public  void deleteById(long id);

	public  List<Cart> findAll();

	public  void Update(Cart cart);
			
	public Cart findById(long id);
	
	public List<Cart> findByUserId(int userId);
}