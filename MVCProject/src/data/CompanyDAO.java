package data;

import java.util.List;
import java.util.Set;

import entities.Company;
import entities.Location;
import entities.Type;

public interface CompanyDAO {
	
	public Set<Company> index();
	public Company show(int id);
	public Company create(int uid,String json, int tid);
	public Boolean delete(int id, int uid);
	public Company update(String json, int cid, int uid);
	public List<Company> getLocationsByCompany(String json);
	public List<Company> getCompanybyUid (int uid); 
	List<Type> getAllTypes();
	List<Company> getAllCompaniesByKeyword(String keyword);

}
