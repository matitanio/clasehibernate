package ar.edu.test.hibernate.model;

import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.hibernate.model.City;
import ar.edu.unlam.hibernate.model.Person;
import ar.edu.unlam.hibernate.utils.HibernateUtils;


public class PersonTests {
	
	private static Log logger = LogFactory.getLog(PersonTests.class);
	
	/**
	 * Nos aseguramos que la persona a insertar no exista
	 */
	@Before
	public void beforeTest(){
		deletePerson();
	}
	
	/**
	 * Eliminamos la persona que insertamos
	 */
	@After
	public void afterTest(){
		deletePerson();
	}
	
	private void deletePerson(){
		Session session = HibernateUtils.getSession(); 
		Person p1 = (Person)session.get(Person.class, 1L);
		City c1 = (City)session.get(City.class, 1L);
		if(p1 != null){
			session.beginTransaction();
			session.delete(p1);
			session.delete(c1);
			session.getTransaction().commit();
		}
	}
	
	@Test
	public void testInsertPerson(){
		Session session = HibernateUtils.getSession(); 
		Calendar dob = Calendar.getInstance();
		dob.clear();
		dob.add(Calendar.DAY_OF_MONTH, 22);
		dob.add(Calendar.MONTH, Calendar.OCTOBER);
		dob.add(Calendar.YEAR, 1938);
		
		City city = new City(1L,"Hill Valley");
		
		//creamos u	na persona con id 1 y nombre Emmet
		Person emmet = new Person(1L,"Emmet","Brown",dob.getTime(),city);
		
		//iniciamos una transaccion
		Transaction tx = session.beginTransaction();

		session.save(city);
		//salvamos esa persona en la session
		logger.info("Inserting[" + emmet + "]");
		session.save(emmet);
	
		//comiteamos la operacion
		tx.commit();
		session.flush();
	
		//verificamos que la persona este en la base de datos
		logger.info("Trying to get[" + emmet + "]");
		Person p1 = (Person) session.get(Person.class, 1L);
		Assert.assertEquals(emmet, p1);
		
	}	
}
