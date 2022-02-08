package fr.formation.inti.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import fr.formation.inti.entity.Employee;

@Repository
public class EmployeeDao extends GenericDaoHibernate<Employee, Integer> implements IEmployeeDao {
	private final static Log log = LogFactory.getLog(EmployeeDao.class);
	private static EmployeeDao dao = null;

	public EmployeeDao() {
		log.info("--------------------- create new EmployeeDao()");
	}

	public static EmployeeDao createInstance() {
		log.info("----------------------- new EmployeeDao() by factory-method");
		if (dao == null)
			dao = new EmployeeDao();
		return dao;
	}

	@PostConstruct
	private void postConstruct() {
		log.info("-------------- init dao: @PostConstruct----------------");
	}

	@PreDestroy
	private void preDestroy() {
		log.info("-------------- destroy dao: @PreDestroy class session Factory----------------");
	}
}