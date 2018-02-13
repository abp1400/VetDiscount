package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Company;
import entities.Location;
import entities.Type;
import entities.User;

@Transactional
@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@PersistenceContext
	EntityManager em;

	@Override
	public Set<Company> index() {
		List c = new ArrayList<>();
		String query = "SELECT c from Company c";
		c = em.createQuery(query, Company.class).getResultList();
		Set<Company> set = new HashSet<>(c);
		return set;
	}
	
	// Company search by keyword
		@Override
		public List<Company> getAllCompaniesByKeyword(String keyword) {
			String query = "SELECT c FROM Company c WHERE c.name" + " LIKE CONCAT('%', :company,'%')";

			return em.createQuery(query, Company.class).setParameter("company", keyword).getResultList();
		}
	
	@Override
	public List<Type> getAllTypes(){
		List t = new ArrayList<>();
		String query = "SELECT t from Type t";
		t = em.createQuery(query, Type.class).getResultList();
		return t;
	}

	@Override
	public Company show(int id) {

		Company c = em.find(Company.class, id);
		c.getLocations().size();
		return c;
	}

	@Override
	public Company create(int uid, String json, int tid) {
		User u = em.find(User.class, uid);
		Type t = null;
		
		if(tid > 0) {
			t = em.find(Type.class, tid);
			
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Company newCompany = mapper.readValue(json, Company.class);
			newCompany.setOwner(u);
			if(t != null) {
				newCompany.setType(t);
				
			}
			em.persist(newCompany);
			em.flush();
			return newCompany;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean delete(int cid, int uid) {
		Company c = em.find(Company.class, cid);
		if (uid == c.getOwner().getId()) {
			String query = "Select l.id FROM Location l WHERE l.company.id=:cid";
			List<Integer> l = em.createQuery(query).setParameter("cid", cid).getResultList();
			for (Integer child : l) {
				em.createQuery("DELETE from Location l where l.id = :id").setParameter("id", child).executeUpdate();
			}
			;
			em.remove(c);
			if (em.find(Company.class, cid) == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Company update(String json, int cid, int uid) {
		ObjectMapper mapper = new ObjectMapper();
		User u = em.find(User.class, uid);
		if (u.getId() == uid) {
			try {
				Company updatedCompany = mapper.readValue(json, Company.class);
				Company managed = em.find(Company.class, cid);
				managed.setName(updatedCompany.getName());
				managed.setStoreUrl(updatedCompany.getStoreUrl());
				managed.setIsChain(updatedCompany.getIsChain());
				return managed;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<Company> getLocationsByCompany(String keyword) {
		String query = "SELECT c FROM Company c WHERE c.name LIKE CONCAT('%', :company,'%')";

		return em.createQuery(query, Company.class)
				.setParameter("company", keyword)
				.getResultList();
	}

	@Override
	public List<Company> getCompanybyUid(int uid) {
		String query = "SELECT c from Company c WHERE c.owner.id=:uid";
		List<Company> companies = em.createQuery(query,Company.class).setParameter("uid",uid).getResultList();
		return companies;
	}

}
