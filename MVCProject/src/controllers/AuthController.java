package controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.AuthDAO;
import data.UserDAO;
import entities.User;

@RestController
public class AuthController {
	
	@Autowired
	  private AuthDAO authDAO;
	
//	@Autowired
//	private UserDAO userDAO;
//	
	  @RequestMapping(path = "auth/register", method = RequestMethod.POST)
	  public User register(HttpSession session, HttpServletResponse res, @RequestBody User user) {
	    User regUser = authDAO.register(user);
	    if(regUser == null) {
	    	res.setStatus(400);
	    	return null;
	    }
	    session.setAttribute("user", regUser);
	    return regUser;
	  }
	  
	  @RequestMapping(path = "auth/login", method = RequestMethod.POST)
	  public User login(HttpSession session, HttpServletResponse res, @RequestBody User user) {
		  User loggedInUser = authDAO.login(user);
		  if(loggedInUser == null) {
			  res.setStatus(401);
			  return null;
		  }
		  session.setAttribute("user", loggedInUser);
		  return loggedInUser;
	  }
	  
	  @RequestMapping(path = "auth/logout", method = RequestMethod.POST)
	  public Boolean logout(HttpSession session, HttpServletResponse response) {
		session.removeAttribute("user");
		if(session.getAttribute("user") != null) {
			return false;
		}
		return true;
	  }
	  
	  //Alternate way to do this
//	  @RequestMapping(path = "/auth/logout", method = RequestMethod.POST)
//	  public Boolean logout(HttpServletRequest req, HttpServletResponse res) {
//	    req.getSession().removeAttribute("user");
//	    return true;
//	  }
	  
	  @RequestMapping(path = "auth/unauthorized")
	  public String unauth(HttpServletResponse response) {
	    response.setStatus(401);
	    return "unauthorized";
	  }
	  
	  

}
