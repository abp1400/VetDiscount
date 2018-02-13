package data;

import java.util.Set;

import entities.User;

public interface UserDAO {

	public Set<User> index();

	public User show(int id);

	public User create(String json);

	public Boolean delete(int id);

	public User update(String json, int id);

}
