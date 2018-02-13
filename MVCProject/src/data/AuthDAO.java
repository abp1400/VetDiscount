package data;

import entities.User;

public interface AuthDAO {
	public User register(User user);
	public User login(User user);
}
