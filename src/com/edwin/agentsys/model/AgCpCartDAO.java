package com.edwin.agentsys.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * A data access object (DAO) providing persistence and search support for
 * AgCpCart entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.edwin.agentsys.model.AgCpCart
 * @author MyEclipse Persistence Tools
 */
@Scope("prototype")
@Service("agCpCartDAO")
public class AgCpCartDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AgCpCartDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String PACKAGE_ID = "packageId";
	public static final String COUNT = "count";

	public void save(AgCpCart transientInstance) {
		log.debug("saving AgCpCart instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AgCpCart persistentInstance) {
		log.debug("deleting AgCpCart instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AgCpCart findById(java.lang.Integer id) {
		log.debug("getting AgCpCart instance with id: " + id);
		try {
			AgCpCart instance = (AgCpCart) getSession().get(
					"com.edwin.agentsys.model.AgCpCart", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AgCpCart instance) {
		log.debug("finding AgCpCart instance by example");
		try {
			List results = getSession()
					.createCriteria("com.edwin.agentsys.model.AgCpCart")
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
		log.debug("finding AgCpCart instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AgCpCart as model where model."
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

	public List findByPackageId(Object packageId) {
		return findByProperty(PACKAGE_ID, packageId);
	}

	public List findByCount(Object count) {
		return findByProperty(COUNT, count);
	}

	public List findAll() {
		log.debug("finding all AgCpCart instances");
		try {
			String queryString = "from AgCpCart";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AgCpCart merge(AgCpCart detachedInstance) {
		log.debug("merging AgCpCart instance");
		try {
			AgCpCart result = (AgCpCart) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AgCpCart instance) {
		log.debug("attaching dirty AgCpCart instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AgCpCart instance) {
		log.debug("attaching clean AgCpCart instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}