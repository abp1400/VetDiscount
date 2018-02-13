package entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String amount;
	@Column(name = "start_date")
	private String startDate;
	@Column(name = "end_date")
	private String endDate;
	private String info;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User creator;
	@JsonIgnore
	//@ManyToMany(mappedBy="discounts" , cascade=CascadeType.REMOVE)
	
	@ManyToMany(
    cascade =
    {
    			CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    },
    targetEntity = Location.class)
@JoinTable(name = "participating_locations",
    inverseJoinColumns = @JoinColumn(name = "location_id"),
    joinColumns = @JoinColumn(name = "discount_id"))
	private Set<Location> locations;

	// GETTERS AND SETTERS
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getId() {
		return id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	// EVERYTHING ELSE
	@Override
	public String toString() {
		return "Discount [id=" + id + ", amount=" + amount + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", info=" + info + ", creator=" + creator + "]";
	}

}
