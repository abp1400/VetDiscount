package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Company;
import entities.Type;

public class SmokeTest {
	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	public void set_up() {
		this.emf = Persistence.createEntityManagerFactory("vetPU");
		this.em = emf.createEntityManager();
	}
	
	@After
	public void tear_down() {
		em.close();
		emf.close();
	}
	
	@Test
	public void smoke_test() {
		boolean test = true;
		assertEquals(true, test);
	}
	
	@Test
	public void CompanyMappings() {
		Company c = em.find(Company.class, 1);
		assertEquals("Skill Distillery",c.getName());
		
		String email = c.getOwner().getEmail();
		
		assertEquals("hunter@SD.com",email);
		
		int size =  c.getLocations().size();	
		
		assertEquals(size,1);
	}
	
	
	@Test 
	public void TypeMappings() {
		Company c = em.find(Company.class, 1);
		
		assertEquals(c.getType().getName(),"Education");
		
		Type t = em.find(Type.class, 1);
		
		assertEquals(t.getCompanies().size(),1);
		
		assertEquals(t.getName(),"Education");
	}
	
}
