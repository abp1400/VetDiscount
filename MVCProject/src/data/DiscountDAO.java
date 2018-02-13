package data;

import java.util.List;
import java.util.Set;

import entities.Discount;

public interface DiscountDAO {
	
	public Discount showDiscount (int discountId);
	public Set<Discount> getDiscountsForLocation(int locationId);
	public Discount createDiscount(String json, int uid, int lid);
	public boolean deleteDiscount(int discountId, int userId);
	public Discount updateDiscount(int discountId, int uid,String json);
	public List<Discount> getDiscountsbyUid (int uid);
}
