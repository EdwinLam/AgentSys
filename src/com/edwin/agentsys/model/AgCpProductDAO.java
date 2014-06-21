package com.edwin.agentsys.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for AgCpProduct entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.edwin.agentsys.model.AgCpProduct
  * @author MyEclipse Persistence Tools 
 */
public class AgCpProductDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AgCpProductDAO.class);
		//property constants
	public static final String NAME = "name";
	public static final String IMG_URL = "imgUrl";
	public static final String INTRODUCE = "introduce";
	public static final String DEFAULT_PACKAGE_ID = "defaultPackageId";



    
    public void save(AgCpProduct transientInstance) {
        log.debug("saving AgCpProduct instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(AgCpProduct persistentInstance) {
        log.debug("deleting AgCpProduct instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public AgCpProduct findById( java.lang.Integer id) {
        log.debug("getting AgCpProduct instance with id: " + id);
        try {
            AgCpProduct instance = (AgCpProduct) getSession()
                    .get("com.edwin.agentsys.model.AgCpProduct", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(AgCpProduct instance) {
        log.debug("finding AgCpProduct instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.edwin.agentsys.model.AgCpProduct")
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
      log.debug("finding AgCpProduct instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from AgCpProduct as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	public List findByImgUrl(Object imgUrl
	) {
		return findByProperty(IMG_URL, imgUrl
		);
	}
	
	public List findByIntroduce(Object introduce
	) {
		return findByProperty(INTRODUCE, introduce
		);
	}
	
	public List findByDefaultPackageId(Object defaultPackageId
	) {
		return findByProperty(DEFAULT_PACKAGE_ID, defaultPackageId
		);
	}
	

	public List findAll() {
		log.debug("finding all AgCpProduct instances");
		try {
			String queryString = "from AgCpProduct";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public AgCpProduct merge(AgCpProduct detachedInstance) {
        log.debug("merging AgCpProduct instance");
        try {
            AgCpProduct result = (AgCpProduct) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AgCpProduct instance) {
        log.debug("attaching dirty AgCpProduct instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(AgCpProduct instance) {
        log.debug("attaching clean AgCpProduct instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public  List findByPage(int offset,int pagesize){
    	log.debug("finding by page");
		try {
			String queryString = "from AgCpProduct";
	         Query queryObject = getSession().createQuery(queryString);
	         if (offset != 0 && pagesize != 0) {
	        	 queryObject.setFirstResult((offset - 1) * pagesize);
	        	 queryObject.setMaxResults(pagesize);
	            }
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("inding by page failed", re);
			throw re;
		}
    }
}