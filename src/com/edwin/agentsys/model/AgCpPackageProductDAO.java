package com.edwin.agentsys.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.edwin.agentsys.test.HibernateSessionFactory;

/**
 	* A data access object (DAO) providing persistence and search support for AgCpPackageProduct entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.edwin.agentsys.model.AgCpPackageProduct
  * @author MyEclipse Persistence Tools 
 */
@Scope("prototype")
@Service("agCpPackageProductDAO")
public class AgCpPackageProductDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AgCpPackageProductDAO.class);
		//property constants
	public static final String PACKAGE_ID = "packageId";
	public static final String PRODUCT_ID = "productId";
	private static Session session= HibernateSessionFactory.getSession();



    
    public void save(AgCpPackageProduct transientInstance) {
        log.debug("saving AgCpPackageProduct instance");
        try {
        	Transaction tr =session.beginTransaction(); // 开始事务
            session.save(transientInstance);
            log.debug("save successful");
            tr.commit();
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
        session.flush();
    }
    
	public void delete(AgCpPackageProduct persistentInstance) {
        log.debug("deleting AgCpPackageProduct instance");
        try {
        	Transaction tr =session.beginTransaction(); // 开始事务
            session.delete(persistentInstance);
            log.debug("delete successful");
            tr.commit();
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
        session.flush();
    }
    
    public AgCpPackageProduct findById( java.lang.Integer id) {
        log.debug("getting AgCpPackageProduct instance with id: " + id);
        try {
            AgCpPackageProduct instance = (AgCpPackageProduct) session
                    .get("com.edwin.agentsys.model.AgCpPackageProduct", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(AgCpPackageProduct instance) {
        log.debug("finding AgCpPackageProduct instance by example");
        try {
            List results = session
                    .createCriteria("com.edwin.agentsys.model.AgCpPackageProduct")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding AgCpPackageProduct instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from AgCpPackageProduct as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPackageId(Object packageId
	) {
		return findByProperty(PACKAGE_ID, packageId
		);
	}
	
	public List findByProductId(Object productId
	) {
		return findByProperty(PRODUCT_ID, productId
		);
	}
	

	public List findAll() {
		log.debug("finding all AgCpPackageProduct instances");
		try {
			String queryString = "from AgCpPackageProduct";
	         Query queryObject = session.createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public AgCpPackageProduct merge(AgCpPackageProduct detachedInstance) {
        log.debug("merging AgCpPackageProduct instance");
        try {
            AgCpPackageProduct result = (AgCpPackageProduct) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AgCpPackageProduct instance) {
        log.debug("attaching dirty AgCpPackageProduct instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(AgCpPackageProduct instance) {
        log.debug("attaching clean AgCpPackageProduct instance");
        try {
                      	session.buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}