package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Location;

public class LocationTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private Location l;

	@Before
	public void set_up() {
		this.emf = Persistence.createEntityManagerFactory("vetPU");
		this.em = emf.createEntityManager();
		l = em.find(Location.class, 1);
	}
	
	@After
	public void tear_down() {
		em.close();
		emf.close();
		l = null;
	}
	
	@Test
	public void test_location_discount_connection() {
		assertEquals(1, l.getDiscounts().size());
	}
	
	@Test
	public void test_location_user_connection() {
		assertEquals("HunterK", l.getOwner().getUsername());
	}
	
	@Test
	public void test_location_address_connection() {
		assertEquals("Denver", l.getAddress().getCity());
	}
	
	@Test
	public void test_location_company_connection() {
		assertEquals("Skill Distillery", l.getCompany().getName());
	}
}
