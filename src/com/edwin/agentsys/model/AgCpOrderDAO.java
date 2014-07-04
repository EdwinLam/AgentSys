package com.edwin.agentsys.model;

import java.util.Date;
import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * AgCpOrder entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.edwin.agentsys.model.AgCpOrder
 * @author MyEclipse Persistence Tools
 */
public class AgCpOrderDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AgCpOrderDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String TOTAL_PRICE = "totalPrice";

	public void save(AgCpOrder transientInstance) {
		log.debug("saving AgCpOrder instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AgCpOrder persistentInstance) {
		log.debug("deleting AgCpOrder instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AgCpOrder findById(java.lang.Integer id) {
		log.debug("getting AgCpOrder instance with id: " + id);
		try {
			AgCpOrder instance = (AgCpOrder) getSession().get(
					"com.edwin.agentsys.model.AgCpOrder", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AgCpOrder instance) {
		log.debug("finding AgCpOrder instance by example");
		try {
			List results = getSession()
					.createCriteria("com.edwin.agentsys.model.AgCpOrder")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AgCpOrder instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AgCpOrder as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByTotalPrice(Object totalPrice) {
		return findByProperty(TOTAL_PRICE, totalPrice);
	}

	public List findAll() {
		log.debug("finding all AgCpOrder instances");
		try {
			String queryString = "from AgCpOrder";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AgCpOrder merge(AgCpOrder detachedInstance) {
		log.debug("merging AgCpOrder instance");
		try {
			AgCpOrder result = (AgCpOrder) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AgCpOrder instance) {
		log.debug("attaching dirty AgCpOrder instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AgCpOrder instance) {
		log.debug("attaching clean AgCpOrder instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}