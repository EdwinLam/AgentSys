package com.edwin.agentsys.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.edwin.agentsys.dao.ProductDao;
import com.edwin.agentsys.model.Order;
import com.edwin.agentsys.model.Product;


@Component("productDao")
public class ProductDaoImpl implements ProductDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate template;
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.ProductDao#insertProduct(cn.net.msg.model.Product)
	 */
	@Override
	public void insertProduct(Product Product){
		 long x=(Long) template.save(Product);
		 System.out.println(x);
	}
 
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.ProductDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(long id){
		Product Product=(Product) template.get(Product.class, id);
		template.delete(Product);
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.ProductDao#findById(java.lang.String)
	 */
	@Override
	public List<Product> findAll(){
		@SuppressWarnings("unchecked")
		List<Product> list=template.find ( "from Product" );
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.net.msg.dao.impl.ProductDao#Update(cn.net.msg.model.Product)
	 */
	@Override
	public void Update(Product Product){
		template.update(Product);
	}

	@Override
	public Product findById(long id) {
		return template.get(Product.class, id);
	}
	
	@Override
	public List<Product> indexFind(final int offset,final int pageSize,int typeId,String name){
		String sql="from Product where del=0 ";
		if(typeId!=0){
			sql+=" and type_id="+typeId;
		}
		if(name!=null&&!name.equals("")){
			sql+=" and name="+name;	
		}
		final String dealSql=sql;
        List list = template.executeFind(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException, SQLException {  
                List result = session.createQuery(dealSql).setFirstResult((offset-1)*pageSize+1)  
                                .setMaxResults(pageSize)  
                                .list();  
                return result;  
            }  
        });  
        return list;  
	}
	
	@Override
	public int indexFindTotal(int typeId,String name){
			String sql="from Product where del=0 ";
			if(typeId!=0){
				sql+=" and type_id="+typeId;
			}
			if(name!=null&&!name.equals("")){
				sql+=" and name="+name;	
			}
		List<Order> list=template.find ( sql );
		return list.size();
	}
}
