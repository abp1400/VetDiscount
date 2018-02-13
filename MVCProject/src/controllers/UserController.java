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

import data.UserDAO;
import entities.User;


@RestController
public class UserController {

	@Autowired
	private UserDAO userDAO;
	


	@RequestMapping(path = "user/{uid}", method = RequestMethod.PUT)
	public User update(@RequestBody String json, HttpServletResponse res,
			@PathVariable int uid) {
		User u = userDAO.update(json, uid);
		if (u == null) {
			res.setStatus(400);
		} else {
			res.setStatus(200);
		}
		return u;
	}
	

	
	
	
	
	

}