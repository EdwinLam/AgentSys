package com.edwin.agentsys.dao;

import java.util.List;

import com.edwin.agentsys.model.OrderDetail;


public interface OrderDetailDao {

	public  void insertOrderDetail(OrderDetail orderDetail);

	public  void deleteById(long id);

	public  List<OrderDetail> findAll();

	public  void Update(OrderDetail orderDetail);
	
	public OrderDetail findById(long id);
	
	public  List<OrderDetail> findByOrderId(int orderId);

}