package com.edwin.agentsys.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.edwin.agentsys.dao.UserDao;
import com.edwin.agentsys.model.User;


@Component("userDao")
public class UserDaoImpl implements UserDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate template;
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#insertUser(cn.net.msg.model.User)
	 */
	@Override
	public void insertUser(User user){
		 long x=(Long) template.save(user);
		 System.out.println(x);
	}
 
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(long id){
		User user=(User) template.get(User.class, id);
		template.delete(user);
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#findById(java.lang.String)
	 */
	@Override
	public List<User> findAll(){
		@SuppressWarnings("unchecked")
		List<User> list=template.find ( "from User" );
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.UserDao#Update(cn.net.msg.model.User)
	 */
	@Override
	public void Update(User user){
		template.update(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByPhone(String phone) {
		List<User> list=template.find("from User u where u.phone=?", phone);  
		return list;
	}

	@Override
	public User findById(long id) {
		return template.get(User.class, id);
	}
}
