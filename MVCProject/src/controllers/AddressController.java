package controllers;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.AddressDAO;
import entities.Address;

@RestController
public class AddressController {
	@Autowired
	private AddressDAO dao;

	@RequestMapping(path = "address", method = RequestMethod.GET)
	public Set<Address> todo(HttpServletRequest req, HttpServletResponse res) {
		return dao.index();
	}

	@RequestMapping(path = "address/{aid}", method = RequestMethod.GET)
	public Address show(HttpServletRequest req, HttpServletResponse res, @PathVariable int aid) {
		Address address = null;
		try {
			address = dao.show(aid);
		} catch (Exception e) {
			res.setStatus(401);
			return null;
		}
		return address;
	}
	
	@RequestMapping(path = "address", method = RequestMethod.POST)
	public Address create(HttpServletRequest req, HttpServletResponse res,
			@RequestBody String stringJson) {
		
		Address address = dao.create(stringJson);
		if(address == null ) {
			res.setStatus(400);
		}
		return address;
	}
	
	@RequestMapping(path = "user/{uid}/location/{lid}/address/{aid}", method = RequestMethod.PUT)
	public Address update(HttpServletRequest req, HttpServletResponse res, @PathVariable int aid, @PathVariable int uid, @PathVariable int lid, @RequestBody String stringJson) {
		Address address = dao.update(stringJson, aid,lid);
		if(address == null) {
			res.setStatus(400);
		}
		return address;
	}
	
	@RequestMapping(path = "address/{aid}", method = RequestMethod.DELETE)
	public boolean destroy(HttpServletRequest req, HttpServletResponse res, @PathVariable int aid) {
		return dao.delete(aid);
	}

}
