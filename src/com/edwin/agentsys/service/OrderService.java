package com.edwin.agentsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edwin.agentsys.dao.OrderDao;
import com.edwin.agentsys.model.Order;


/*
 * Service层，在此可以进行事物管理
 */
@Component("orderService")
public class OrderService {
	@Resource(name="orderDao")
	private OrderDao dao;
	@Transactional
	public boolean insertOrder(Order order){
		dao.insertOrder(order);
		return true;
	}
	@Transactional
	public boolean deleteById(long id){
		dao.deleteById(id);
		return true;
	}
	@Transactional
	public boolean  updateOrder(Order order){
		dao.Update(order);
		return true;
	}
	@Transactional
	public List<Order> findAll(){
		return dao.findAll();
	}

	@Transactional
	public Order findById(long id){
		return dao.findById(id);
	}
	
	@Transactional
	public List<Order>orderFind(int userid,int offset,int pageSize,int status,String orderNo){
		return dao.orderFind(userid,offset, pageSize, status, orderNo);
	}
	
	@Transactional
	public int orderSumUp(int userId,int status,String orderNo){
		return dao.orderSumUp(userId, status, orderNo);
	}
	}
