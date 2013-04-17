package com.customized.tools.persist.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import com.customized.tools.persist.DataSourceUtil;
import com.customized.tools.persist.H2Helper;
import com.customized.tools.persist.JPAUtil;
import com.customized.tools.persist.test.po.Task;

public class JPAUtilTest extends TestBase{

	public static void main(String[] args) throws Exception {
		
		testWithProps();
		
		testWithDS();
	}
	
	/**
	 * Show how to create JPA EntityManager via DataSource
	 *   1. A DataSource reference with a persistence unit must be available
	 *   2. A jndi.properties file should be provide, which Hibernate used to lookup DataSource
	 */
	protected static void testWithDS() {
		
		H2Helper.startH2Server();
		
		DataSourceUtil.setupDataSource("jdbc/test-ds");
		
		EntityManager em = JPAUtil.getEntityManager("com.customized.tools.persist.test");
		
		execute(em);
		
		JPAUtil.destory();
		
		H2Helper.stopH2Server();
	}

	/**
	 * Show how to create JPA EntityManager via properties file which contain Hibernate DB pool initial parameter
	 *   1. persistence unit should be provider
	 *   2. a properties file should be provider which contain Hibernate pool initial info
	 * @throws Exception
	 */
	protected static void testWithProps() throws Exception {

		H2Helper.startH2Server();
		
		EntityManager em = JPAUtil.getEntityManager("com.customized.tools.persist.test.props", loadProps("h2.properties"));
				
		execute(em);
		
		JPAUtil.destory();
		
		H2Helper.stopH2Server();
	}
	
	@SuppressWarnings("rawtypes")
	private static Map loadProps(String name) throws Exception {
		
		InputStream in = null;
		Properties props = new Properties();
		
		try {
			in = new FileInputStream(name);
			props.load(in);
			return props ;
		} catch (Exception e) {
			throw e ;
		} finally {
			if(null != in) {
				in.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void execute(EntityManager em) {
		
		em.getTransaction().begin();
		Task task = new Task();
		task.setName("JPA J2SE Test Project");
		em.persist(task);
		task = new Task();
		task.setName("JPA JTA Test Project");
		em.persist(task);
		em.getTransaction().commit();
		
		List<Task> list = em.createQuery("SELECT t From Task t").getResultList();
		for(Task t : list) {
			System.out.println(t.getId() + " - " + t.getName());
		}
		
		em.close();
	}

}
