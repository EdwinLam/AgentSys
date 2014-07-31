package com.edwin.agentsys.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.edwin.agentsys.dao.OrderDetailDao;
import com.edwin.agentsys.model.OrderDetail;
import com.edwin.agentsys.model.User;


@Component("orderDetailDao")
public class OrderDetailDaoImpl implements OrderDetailDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate template;
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDetailDao#insertOrderDetail(cn.net.msg.model.OrderDetail)
	 */
	@Override
	public void insertOrderDetail(OrderDetail OrderDetail){
		 long x=(Long) template.save(OrderDetail);
		 System.out.println(x);
	}
 
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDetailDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(long id){
		OrderDetail OrderDetail=(OrderDetail) template.get(OrderDetail.class, id);
		template.delete(OrderDetail);
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDetailDao#findById(java.lang.String)
	 */
	@Override
	public List<OrderDetail> findAll(){
		@SuppressWarnings("unchecked")
		List<OrderDetail> list=template.find ( "from OrderDetail" );
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDetailDao#Update(cn.net.msg.model.OrderDetail)
	 */
	@Override
	public void Update(OrderDetail OrderDetail){
		template.update(OrderDetail);
	}

	@Override
	public OrderDetail findById(long id) {
		return template.get(OrderDetail.class, id);
	}
	
	@Override
	public  List<OrderDetail> findByOrderId(int orderId){
		@SuppressWarnings("unchecked")
		List<OrderDetail> list=template.find ( "from OrderDetail where order_id=?", orderId);
		return list;
	}
}
