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

import entities.Address;
import entities.Location;


@Transactional
@Repository
public class AddressDAOImpl implements AddressDAO{
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
	public Set<Address> index() {
		List<Address> a = new ArrayList<>();
		String query = "SELECT a from Address a";
		a = em.createQuery(query, Address.class).getResultList();
		Set<Address> set = new HashSet<>(a);
		return set;
		
	}

	@Override
	public Address show(int id) {
		return em.find(Address.class, id);
	}

	@Override
	public Address create(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Address newAddress = mapper.readValue(json, Address.class);
			em.persist(newAddress);
			em.flush();
			return newAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Boolean delete(int id) {
		Address a = em.find(Address.class, id);
		em.remove(a);
		if (em.find(Address.class, id) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Address update(String json, int aid, int lid) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Address updatedAddress = mapper.readValue(json, Address.class);
			Address managed = em.find(Address.class, aid);
			managed.setStreet(updatedAddress.getStreet());
			managed.setCity(updatedAddress.getCity());
			managed.setState(updatedAddress.getState());
			managed.setZip(updatedAddress.getZip());
			return managed;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
}
