package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Address;

public class AddressTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private Address a;

	@Before
	public void set_up() {
		this.emf = Persistence.createEntityManagerFactory("vetPU");
		this.em = emf.createEntityManager();
		a = em.find(Address.class, 1);
	}
	
	@After
	public void tear_down() {
		em.close();
		emf.close();
		a = null;
	}
	
	@Test
	public void test_address_location_connection() {
		assertEquals(1, a.getLocation().getId());
	}

}
