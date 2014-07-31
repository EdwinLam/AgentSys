package com.edwin.agentsys.dao;

import java.util.List;

import com.edwin.agentsys.model.Order;


public interface OrderDao {

	public  void insertOrder(Order order);

	public  void deleteById(long id);

	public  List<Order> findAll();

	public  void Update(Order order);
			
	public Order findById(long id);
	
	public List<Order> orderFind(int userId,int offset,int pageSize,int status,String orderNo);
	
	public int orderSumUp(int userId,int status,String orderNo);

}