package data;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Address;
import entities.Company;
import entities.Location;
import entities.Type;

@Transactional
@Repository
public class LocationDAOImpl implements LocationDAO {

	@PersistenceContext
	private EntityManager em;

	// index - show all locations
	@Override
	public Set<Location> index() {
		String query = "SELECT DISTINCT l FROM Location l";
		List<Location> locations = em.createQuery(query, Location.class).getResultList();
		Set<Location> set = new HashSet<>(locations);
		return set;
	}

	// show a location by id
	@Override
	public Location show(int lid) {

		Location location = em.find(Location.class, lid);

		if (location == null || location.getId() != lid) {
			return null;
		}

		return location;
	}

	// create a location for a company
	@Override
	public Location create(String json, int cid, int aid) {

		ObjectMapper mapper = new ObjectMapper();
		Location location = null;

		try {

			location = mapper.readValue(json, Location.class);

			Company company = em.find(Company.class, cid);
			Address address = em.find(Address.class, aid);
			if (company.getId() == cid) {
				location.setCompany(company);
				location.setAddress(address);

				em.persist(location);
				em.flush();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;

	}

	// delete a location
	@Override
	public Boolean delete(int lid, int cid) {
		Location l = em.find(Location.class, lid);
		String query = "DELETE FROM Location l WHERE l.id = :lid";
		if (l.getCompany().getId() == cid) {
			int delete = em.createQuery(query).setParameter("lid", lid).executeUpdate();
			if (delete > 0) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	// update a location
	@Override
	public Location update(String json, int lid, int cid) {

		ObjectMapper mapper = new ObjectMapper();
		Location updateLocation = null;
		Location oldLocation = null;
		try {
			updateLocation = mapper.readValue(json, Location.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		oldLocation = em.find(Location.class, lid);
		if (oldLocation.getCompany().getId() == cid) {
			oldLocation.setHours(updateLocation.getHours());
			oldLocation.setPhoneNumber(updateLocation.getPhoneNumber());
			return oldLocation;
		} else {
			return null;
		}
	}

	// Location search by keyword
	@Override
	public List<Location> getAllLocationsByKeyword(String keyword) {
		String query = "SELECT l FROM Location l WHERE l.company.name" + " LIKE CONCAT('%', :company,'%')"
				+ " OR l.phoneNumber LIKE CONCAT('%', :phoneNumber,'%')"
				+ " OR l.address.street LIKE CONCAT('%', :street,'%')"
				+ " OR l.address.city LIKE CONCAT('%', :city,'%')" + " OR l.address.state LIKE CONCAT('%', :state,'%')"
				+ " OR l.address.zip LIKE CONCAT('%', :zip,'%')";

		return em.createQuery(query, Location.class).setParameter("company", keyword)
				.setParameter("phoneNumber", keyword).setParameter("street", keyword).setParameter("city", keyword)
				.setParameter("state", keyword).setParameter("zip", keyword).getResultList();
	}

	// Location search by keyword with filters
	@Override
	public List<Location> getAllLocationsByKeywordWithFilters(String keyword, String distance, String typeId) {
		String filter = "";
		int distanceInt;
		int typeIdInt;
		Type type = null;

		try {
			distanceInt = Integer.parseInt(distance);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			distanceInt = 3;
		}

		try {
			typeIdInt = Integer.parseInt(typeId);
			type = em.find(Type.class, typeIdInt);
		} catch (Exception e) {
			e.printStackTrace();
			typeIdInt = 0;
		}
		
		for(int x=0; x<20; x++) {
			System.out.println("-----------------------------------");
		}
		
		System.out.println(type);	
		
		for(int x=0; x<20; x++) {
			System.out.println("-----------------------------------");
		}
		
		String query = "SELECT l FROM Location l WHERE l.company.type = :type AND l.company.name" + " LIKE CONCAT('%', :company,'%')"
				+ " OR l.phoneNumber LIKE CONCAT('%', :phoneNumber,'%')"
				+ " OR l.address.street LIKE CONCAT('%', :street,'%')"
				+ " OR l.address.city LIKE CONCAT('%', :city,'%')" + " OR l.address.state LIKE CONCAT('%', :state,'%')"
				+ " OR l.address.zip LIKE CONCAT('%', :zip,'%') ";
		
		
		return em.createQuery(query, Location.class).setParameter("company", keyword)
				.setParameter("phoneNumber", keyword).setParameter("street", keyword).setParameter("city", keyword)
				.setParameter("state", keyword).setParameter("zip", keyword)
				.setParameter("type", type).getResultList();
	
	}

	// Get Locations by company id
	@Override
	public List<Location> getLocationsByCompanyId(int cid) {
		String query = "SELECT l FROM Location l WHERE l.company.id = :cid";

		return em.createQuery(query, Location.class).setParameter("cid", cid).getResultList();
	}

	@Override
	public List<Location> getLocationsbyUid(int uid) {
		String q = "SELECT l FROM Location l where l.company.owner.id = :uid";
		List<Location> locations = em.createQuery(q,Location.class).setParameter("uid", uid).getResultList();
		return locations;
	}

}
