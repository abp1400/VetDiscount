package entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
public class Location {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String hours;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private User owner;


	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	//@JsonIgnore
	@JsonManagedReference
	@OneToOne
	@JoinColumn(name="address_id")
	private Address address;
	
//	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(name="participating_locations", joinColumns=@JoinColumn(name="location_id"), inverseJoinColumns=@JoinColumn(name="discount_id"))
	@ManyToMany(fetch = FetchType.EAGER,
	        cascade =
	        {
	                CascadeType.DETACH,
	                CascadeType.MERGE,
	                CascadeType.REFRESH,
	                CascadeType.PERSIST
	        },
	        targetEntity = Discount.class)
	@JoinTable(name = "participating_locations",
    joinColumns = @JoinColumn(name = "location_id"),
    inverseJoinColumns = @JoinColumn(name = "discount_id"))
	private Set<Discount> discounts;

	public int getId() {
		return id;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", hours=" + hours + ", phoneNumber=" + phoneNumber + ", owner=" + owner + ", address=" + address + "]";
	}
	
	
}
