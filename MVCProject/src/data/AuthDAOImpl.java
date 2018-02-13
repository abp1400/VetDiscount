package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.User;

@Transactional
@Repository
public class AuthDAOImpl implements AuthDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public User register(User user) {
		String passwordSha = encoder.encode(user.getPassword());
		user.setPassword(passwordSha);
		em.persist(user);
		em.flush();
		return user;
	}
	
	@Override
	public User login(User user) throws NoResultException {
		String query = "SELECT u FROM User u WHERE u.username = :username";
		List<User> users = em.createQuery(query, User.class)
		                        .setParameter("username", user.getUsername())
		                        .getResultList();
		if(users.size() > 0) {
			User managedUser = users.get(0);
			if (encoder.matches(user.getPassword(), managedUser.getPassword())) {
				return managedUser;
			}
		}
		
		return null;
	}

	

}
