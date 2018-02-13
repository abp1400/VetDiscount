package controllers;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.DiscountDAO;
import entities.Discount;

@RestController
public class DiscountController {
	@Autowired
	private DiscountDAO dao;
	
	@RequestMapping(path = "discount/{did}", method = RequestMethod.GET)
	public Discount show(HttpServletRequest req, HttpServletResponse res, @PathVariable int did) {
		Discount d = dao.showDiscount(did);
		if (d == null) {
			res.setStatus(404);
			return null;
		} else {
			return d;

		}

	}

	@RequestMapping(path = "discount/location/{lid}", method = RequestMethod.GET)
	public Set<Discount> discountsByLocation(HttpServletRequest req, HttpServletResponse res, @PathVariable int lid) {
		return dao.getDiscountsForLocation(lid);
	}

	@RequestMapping(path = "{uid}/discount/location/{lid}", method = RequestMethod.POST)
	public Discount create(HttpServletRequest req, HttpServletResponse res, 
			@RequestBody String stringJson,
			@PathVariable int uid,
			@PathVariable int lid) {

		Discount discount = dao.createDiscount(stringJson, uid, lid);
		if (discount == null) {
			res.setStatus(400);
		}
		return discount;
	}

	@RequestMapping(path = "user/{uid}/discount/{did}", method = RequestMethod.DELETE)
	public boolean destroy(HttpServletRequest req, HttpServletResponse res, @PathVariable int did,
			@PathVariable int uid) {
		Boolean b = dao.deleteDiscount(did, uid);
		if (b == true) {
			res.setStatus(201);
		} else {
			res.setStatus(400);
		}
		
		return b;
	}

	@RequestMapping(path = "user/{uid}/discount/{did}", method = RequestMethod.PUT)
	public Discount update(HttpServletRequest req, HttpServletResponse res, @PathVariable int did,
			@PathVariable int uid, @RequestBody String stringJson) {
		Discount discount = dao.updateDiscount(did, uid, stringJson);
		if (discount == null) {
			res.setStatus(400);
		}
		return discount;
	}
	@RequestMapping(path="user/{uid}/discounts", method = RequestMethod.GET)
	public List<Discount> getDiscountsByUid(HttpServletResponse res, @PathVariable int uid) {
		
		return dao.getDiscountsbyUid(uid);
	}

}
