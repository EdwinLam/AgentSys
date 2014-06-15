package com.edwin.agentsys.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * QxUser entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.edwin.agentsys.model.QxUser
 * @author MyEclipse Persistence Tools
 */
public class QxUserDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(QxUserDAO.class);
	// property constants
	public static final String ACCOUNT = "account";
	public static final String NAME = "name";

	public void save(QxUser transientInstance) {
		log.debug("saving QxUser instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(QxUser persistentInstance) {
		log.debug("deleting QxUser instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public QxUser findById(java.lang.Integer id) {
		log.debug("getting QxUser instance with id: " + id);
		try {
			QxUser instance = (QxUser) getSession().get(
					"com.edwin.agentsys.model.QxUser", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(QxUser instance) {
		log.debug("finding QxUser instance by example");
		try {
			List results = getSession()
					.createCriteria("com.edwin.agentsys.model.QxUser")
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
		log.debug("finding QxUser instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from QxUser as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccount(Object account) {
		return findByProperty(ACCOUNT, account);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findAll() {
		log.debug("finding all QxUser instances");
		try {
			String queryString = "from QxUser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public QxUser merge(QxUser detachedInstance) {
		log.debug("merging QxUser instance");
		try {
			QxUser result = (QxUser) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(QxUser instance) {
		log.debug("attaching dirty QxUser instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(QxUser instance) {
		log.debug("attaching clean QxUser instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}