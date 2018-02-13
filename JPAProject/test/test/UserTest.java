package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.User;

public class UserTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private User u;

	@Before
	public void set_up() {
		this.emf = Persistence.createEntityManagerFactory("vetPU");
		this.em = emf.createEntityManager();
		u = em.find(User.class, 1);
	}
	
	@After
	public void tear_down() {
		em.close();
		emf.close();
		u = null;
	}
	
	@Test
	public void test_user_company_connection() {
		assertEquals("Skill Distillery", u.getCompany().getName());
	}
	
	@Test
	public void test_user_discount_connection() {
		assertEquals(1, u.getDiscounts().size());
	}
	
	@Test
	public void test_user_location_connection() {
		assertEquals(1, u.getLocations().size());
	}
}
