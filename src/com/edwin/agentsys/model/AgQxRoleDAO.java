package com.edwin.agentsys.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for AgQxRole entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.edwin.agentsys.model.AgQxRole
  * @author MyEclipse Persistence Tools 
 */
public class AgQxRoleDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AgQxRoleDAO.class);
		//property constants
	public static final String NAME = "name";



    
    public void save(AgQxRole transientInstance) {
        log.debug("saving AgQxRole instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(AgQxRole persistentInstance) {
        log.debug("deleting AgQxRole instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public AgQxRole findById( java.lang.Integer id) {
        log.debug("getting AgQxRole instance with id: " + id);
        try {
            AgQxRole instance = (AgQxRole) getSession()
                    .get("com.edwin.agentsys.model.AgQxRole", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(AgQxRole instance) {
        log.debug("finding AgQxRole instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.edwin.agentsys.model.AgQxRole")
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
      log.debug("finding AgQxRole instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from AgQxRole as model where model." 
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
	

	public List findAll() {
		log.debug("finding all AgQxRole instances");
		try {
			String queryString = "from AgQxRole";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public AgQxRole merge(AgQxRole detachedInstance) {
        log.debug("merging AgQxRole instance");
        try {
            AgQxRole result = (AgQxRole) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AgQxRole instance) {
        log.debug("attaching dirty AgQxRole instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(AgQxRole instance) {
        log.debug("attaching clean AgQxRole instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}