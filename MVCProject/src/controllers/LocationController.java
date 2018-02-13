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

import data.LocationDAO;
import entities.Location;

@RestController
public class LocationController {
	
	@Autowired
	private LocationDAO locationDAO;
	
	//Search by location
	@RequestMapping(path="location/search/{searchString}", method=RequestMethod.GET)
	public List<Location> search(HttpServletRequest req, HttpServletResponse res, 
			@PathVariable String searchString) {
		List<Location> searchedLocations = locationDAO.getAllLocationsByKeyword(searchString);
		if(searchedLocations== null) {
			res.setStatus(400);
		}
		else {
			res.setStatus(201);
		}
		return searchedLocations;
	}
	
	//Search by location
		@RequestMapping(path="location/search/{searchString}/{distance}/{typeId}", method=RequestMethod.GET)
		public List<Location> searchWithFilters(HttpServletRequest req, HttpServletResponse res, 
				@PathVariable String searchString, @PathVariable String distance,
				@PathVariable String typeId) {
			List<Location> searchedLocations = locationDAO.getAllLocationsByKeywordWithFilters(searchString,distance,typeId);
			if(searchedLocations== null) {
				res.setStatus(400);
			}
			else {
				res.setStatus(201);
			}
			return searchedLocations;
		}
	
	//Get Locations by company id
	@RequestMapping(path="{cid}/location", method=RequestMethod.GET)
	public List<Location> getLocations(HttpServletRequest req, HttpServletResponse res,
			@PathVariable int cid) {
		List<Location> locations = locationDAO.getLocationsByCompanyId(cid);
		
		if(locations == null) {
			res.setStatus(400);
		}
		else {
			res.setStatus(201);
		}
		System.out.println(locations);
		return locations;
	}
	
//  Index - GET - location
	@RequestMapping(path="location", method=RequestMethod.GET)
	public Set<Location> index(HttpServletRequest req, HttpServletResponse res){
		return locationDAO.index();
	}

//  Show - GET - location/{lid}
	@RequestMapping(path="location/{lid}", method=RequestMethod.GET)
	public Location show(HttpServletRequest req, HttpServletResponse res, 
			@PathVariable int lid) {
		return locationDAO.show(lid);
	}

//  Create - POST - location/{cid}
	@RequestMapping(path="location/{cid}/{aid}", method=RequestMethod.POST)
	public Location create(HttpServletRequest req, HttpServletResponse res, 
			@PathVariable int cid, @PathVariable int aid,
			@RequestBody String locationJson) {
		
		Location location = locationDAO.create(locationJson, cid, aid);
		
		if(location == null) {
			res.setStatus(400);
		}
		else {
			res.setStatus(201);
		}
		
		return location;
	}

//  Update - PUT - location/{lid}
	@RequestMapping(path="user/{uid}/company/{cid}/location/{lid}", method=RequestMethod.PUT)
	public Location update(HttpServletRequest req, HttpServletResponse res, 
			@PathVariable int lid, @PathVariable int cid, @PathVariable int uid,
			@RequestBody String locationJson) {
		
		Location location = locationDAO.update(locationJson, lid, cid);
		
		if(location == null) {
			res.setStatus(400);
		}
		else {
			res.setStatus(201);
		}
		
		return location;
	}

//  Delete - DELETE - location/{lid}
	@RequestMapping(path="{cid}/location/{lid}", method=RequestMethod.DELETE)
	public Boolean delete(HttpServletRequest req, HttpServletResponse res, 
			@PathVariable int lid, @PathVariable int cid) {
		
		boolean result = locationDAO.delete(lid, cid);
		
		if(result == false) {
			res.setStatus(400);
		}
		else {
			res.setStatus(201);
		}
		
		return result;
	}
	
	@RequestMapping(path="user/{uid}/locations",method=RequestMethod.GET)
	public List<Location> getLocationsbyUid(HttpServletResponse res, @PathVariable int uid) {
		return locationDAO.getLocationsbyUid(uid);
	} 

}
