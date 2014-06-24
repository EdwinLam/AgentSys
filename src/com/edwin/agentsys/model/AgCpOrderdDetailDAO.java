package com.edwin.agentsys.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for AgCpOrderdDetail entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.edwin.agentsys.model.AgCpOrderdDetail
  * @author MyEclipse Persistence Tools 
 */
public class AgCpOrderdDetailDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AgCpOrderdDetailDAO.class);
		//property constants
	public static final String ORDER_ID = "orderId";
	public static final String PACKAGE_ID = "packageId";



    
    public void save(AgCpOrderdDetail transientInstance) {
        log.debug("saving AgCpOrderdDetail instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(AgCpOrderdDetail persistentInstance) {
        log.debug("deleting AgCpOrderdDetail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public AgCpOrderdDetail findById( java.lang.Integer id) {
        log.debug("getting AgCpOrderdDetail instance with id: " + id);
        try {
            AgCpOrderdDetail instance = (AgCpOrderdDetail) getSession()
                    .get("com.edwin.agentsys.model.AgCpOrderdDetail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(AgCpOrderdDetail instance) {
        log.debug("finding AgCpOrderdDetail instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.edwin.agentsys.model.AgCpOrderdDetail")
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
      log.debug("finding AgCpOrderdDetail instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from AgCpOrderdDetail as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByOrderId(Object orderId
	) {
		return findByProperty(ORDER_ID, orderId
		);
	}
	
	public List findByPackageId(Object packageId
	) {
		return findByProperty(PACKAGE_ID, packageId
		);
	}
	

	public List findAll() {
		log.debug("finding all AgCpOrderdDetail instances");
		try {
			String queryString = "from AgCpOrderdDetail";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public AgCpOrderdDetail merge(AgCpOrderdDetail detachedInstance) {
        log.debug("merging AgCpOrderdDetail instance");
        try {
            AgCpOrderdDetail result = (AgCpOrderdDetail) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AgCpOrderdDetail instance) {
        log.debug("attaching dirty AgCpOrderdDetail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(AgCpOrderdDetail instance) {
        log.debug("attaching clean AgCpOrderdDetail instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}