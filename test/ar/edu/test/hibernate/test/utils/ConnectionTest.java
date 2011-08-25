package ar.edu.test.hibernate.test.utils;


import org.junit.Assert;
import org.junit.Test;

import ar.edu.unlam.hibernate.utils.HibernateUtils;

public class ConnectionTest {
	
	@Test
	public void testConection(){
		Assert.assertNotNull(HibernateUtils.getSession());
	}

}
