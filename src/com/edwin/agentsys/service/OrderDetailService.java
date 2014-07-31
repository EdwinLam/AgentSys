package com.edwin.agentsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edwin.agentsys.dao.OrderDetailDao;
import com.edwin.agentsys.model.OrderDetail;


/*
 * Service层，在此可以进行事物管理
 */
@Component("orderDetailService")
public class OrderDetailService {
	@Resource(name="orderDetailDao")
	private OrderDetailDao dao;
	@Transactional
	public boolean insertOrderDetail(OrderDetail orderDetail){
		dao.insertOrderDetail(orderDetail);
		return true;
	}
	@Transactional
	public boolean deleteById(long id){
		dao.deleteById(id);
		return true;
	}
	@Transactional
	public boolean  updateOrderDetail(OrderDetail orderDetail){
		dao.Update(orderDetail);
		return true;
	}
	@Transactional
	public List<OrderDetail> findAll(){
		return dao.findAll();
	}

	@Transactional
	public OrderDetail findById(long id){
		return dao.findById(id);
	}
	
	@Transactional
	public List<OrderDetail> findByOrderId(int orderid){
		return dao.findByOrderId(orderid);
	}
	}
