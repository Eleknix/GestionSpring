package fr.formation.inti.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import fr.formation.inti.entity.Employee;
import fr.formation.inti.entity.User;

public class GenericDaoHibernate<T, I extends Serializable> implements IGenericDao<T, I> {
	private static final Log log = LogFactory.getLog(GenericDaoHibernate.class);
	// SessionFactory sf = HibernateUtils.getSessionFactory();
	@Autowired
	private SessionFactory sessionFactory;
	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernate() {
		this.type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public GenericDaoHibernate(Class<T> type) {
		this.type = type;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Integer register(User u) {
		log.info("persisting Employee instance");
		try {
			getCurrentSession().persist(u);
			Integer id = u.getUserId();
			log.info("persist successful");
			return id;
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void del(Employee emp) {
		log.info("del Employee instance");
		try {
			getCurrentSession().delete(emp);
			log.info("del successful");
		} catch (RuntimeException re) {
			log.error("del failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public I save(T t) {
		I id = (I) getCurrentSession().save(t);
		return (I) id;
	}

	public void update(T t) {
		getCurrentSession().update(t);
	}

	public void delete(I i) {
		getCurrentSession().delete(i);
	}

	public T findById(I i) {
		T u = (T) getCurrentSession().get(this.type, i);
		return (T) u;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		String hql = "select e from " + this.type.getName() + " e";
		Query<T> query = getCurrentSession().createQuery(hql);
		List<T> datas = query.getResultList();
		return datas;
	}
}