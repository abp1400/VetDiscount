package entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String email;
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy="creator" ,fetch = FetchType.EAGER)
	private Set<Discount> discounts;
	
	@JsonIgnore
	@OneToOne(mappedBy="owner")
	private Company company;
	
	@JsonIgnore
	@OneToMany(mappedBy="owner")
	private Set<Location> locations;
	
	///GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public Set<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Set<Location> getLocations() {
		return locations;
	}
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	////Everything else
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	
	
	
	
}
