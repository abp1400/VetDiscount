package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Discount;

public class DiscountTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private Discount d;

	@Before
	public void set_up() {
		this.emf = Persistence.createEntityManagerFactory("vetPU");
		this.em = emf.createEntityManager();
		d = em.find(Discount.class, 1);
	}
	
	@After
	public void tear_down() {
		em.close();
		emf.close();
		d = null;
	}
	
	@Test
	public void test_discount_location_connection() {
		assertEquals(1, d.getLocations().size());
	}
	
	@Test
	public void test_user_discount_connection() {
		assertEquals("HunterK", d.getCreator().getUsername());
	}
	
}
