package com.edwin.agentsys.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.edwin.agentsys.dao.OrderDao;
import com.edwin.agentsys.model.Order;
import com.edwin.agentsys.model.Product;


@Component("orderDao")
public class OrderDaoImpl implements OrderDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate template;
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDao#insertOrder(cn.net.msg.model.Order)
	 */
	@Override
	public void insertOrder(Order Order){
		 long x=(Long) template.save(Order);
		 System.out.println(x);
	}
 
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(long id){
		Order Order=(Order) template.get(Order.class, id);
		template.delete(Order);
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDao#findById(java.lang.String)
	 */
	@Override
	public List<Order> findAll(){
		@SuppressWarnings("unchecked")
		List<Order> list=template.find ( "from Order" );
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.OrderDao#Update(cn.net.msg.model.Order)
	 */
	@Override
	public void Update(Order Order){
		template.update(Order);
	}


	@Override
	public Order findById(long id) {
		return template.get(Order.class, id);
	}
	
	@Override
	public List<Order> orderFind(int userId,final int offset,final int pageSize,int status,String orderNo){
		String sql="from product where 1=1 ";
		if(status!=0){
			sql+=" and status="+status;
		}
		if(userId!=0){
			sql+=" and user_id="+userId;
		}
		if(orderNo!=null&&!orderNo.equals("")){
			sql+=" and orderNo="+orderNo;	
		}
		final String dealSql=sql;
        List list = template.executeFind(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException, SQLException {  
                List result = session.createQuery(dealSql).setFirstResult(offset)  
                                .setMaxResults(pageSize)  
                                .list();  
                return result;  
            }  
        });  
        return list;  
	}
	
	public int orderSumUp(int userId,int status,String orderNo){
		String sql="from order where 1=1 ";
		if(status!=0){
			sql+=" and status="+status;
		}
		if(userId!=0){
			sql+=" and user_id="+userId;
		}
		if(orderNo!=null&&!orderNo.equals("")){
			sql+=" and orderNo="+orderNo;	
		}
		List<Order> list=template.find ( sql );
		return list.size();
	}
}
