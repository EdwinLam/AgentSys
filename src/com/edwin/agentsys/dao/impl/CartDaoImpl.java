package com.edwin.agentsys.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.edwin.agentsys.dao.CartDao;
import com.edwin.agentsys.model.Cart;


@Component("cartDao")
public class CartDaoImpl implements CartDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate template;
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.CartDao#insertCart(cn.net.msg.model.Cart)
	 */
	@Override
	public void insertCart(Cart Cart){
		 long x=(Long) template.save(Cart);
		 System.out.println(x);
	}
 
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.CartDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(long id){
		Cart Cart=(Cart) template.get(Cart.class, id);
		template.delete(Cart);
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.CartDao#findById(java.lang.String)
	 */
	@Override
	public List<Cart> findAll(){
		@SuppressWarnings("unchecked")
		List<Cart> list=template.find ( "from Cart" );
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.CartDao#Update(cn.net.msg.model.Cart)
	 */
	@Override
	public void Update(Cart Cart){
		template.update(Cart);
	}


	@Override
	public Cart findById(long id) {
		return template.get(Cart.class, id);
	}
	
	@Override
	public List<Cart> findByUserId(int userId){
		@SuppressWarnings("unchecked")
		List<Cart> list=template.find ( "from Cart where user_id=? " ,userId);
		return list;
	}
}
