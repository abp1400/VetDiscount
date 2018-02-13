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

import entities.User;

@Transactional
@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Set<User> index() {
		List<User> a = new ArrayList<>();
		String query = "SELECT a from User a";
		a = em.createQuery(query, User.class).getResultList();
		Set<User> set = new HashSet<>(a);
		return set;

	}

	@Override
	public User show(int id) {
		return em.find(User.class, id);
	}

	@Override
	public User create(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User newUser = mapper.readValue(json, User.class);
			em.persist(newUser);
			em.flush();
			return newUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean delete(int id) {
		User user = em.find(User.class, id);
		em.remove(user);
		if (em.find(User.class, id) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User update(String json, int id) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User updatedUser = mapper.readValue(json, User.class);
			User managed = em.find(User.class, id);
			managed.setUsername(updatedUser.getUsername());
			managed.setEmail(updatedUser.getEmail());
			return managed;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
