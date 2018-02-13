angular.module('appModule').factory('vetService', function($http, authService) {
	var service = {};
	// login Auth
	var checkLogin = function() {
		var user = authService.getToken();
		if(!user.id) {
			$location.path('/login')
			return;
		}
		return user;
	}

	// index
	service.index = function() {
        return $http({
            method : 'GET',
            url : 'rest/location'
        });
    }

	// index for companies
	service.company = function() {
        return $http({
            method : 'GET',
            url : 'rest/company'
        });
    }

	//index for companies by user
	service.companybyUid = function () {
		var user = checkLogin();

		return $http({
			method:'GET',
			url:'rest/user/' + user.id + '/companies'

		});

	}

	// get types
//	service.getTypes = function() {
//		return $http({
//			method : 'GET',
//			url : 'rest/company/' + cid
//		})
//	}

	// search navbar
	service.search = function(searchTerm){
		if(searchTerm.trim()===null ||searchTerm.trim()===""){
			return $http({
				method : 'GET',
				url : 'rest/location'
			});
		}

		return $http({
			method : 'GET',
			url : 'rest/location/search/' + searchTerm
		});
	}

	service.searchWithFilters = function(searchTerm,typeId,distance){
		if(searchTerm.trim()===null ||searchTerm.trim()===""){
			return $http({
				method : 'GET',
				url : 'rest/location'
			});
		}

		return $http({
			method : 'GET',
			url : 'rest/location/search/' + searchTerm + '/' + distance + '/' + typeId
		});
	}

	// search company
	service.searchCompany = function(searchCompany){
		return $http({
			method : 'GET',
			url : 'rest/company/search/' + searchCompany
		});
	}

	// get locations by company id
	service.getLocations = function(cid){
		return $http({
			method : 'GET',
			url : 'rest/' + cid + '/location'
		});
	}


	//index for locations by user
	service.getLocationsbyUid = function (){
		var user =  checkLogin();

		return $http({
			method:'GET',
			url: 'rest/user/'+ user.id + '/locations'

		})
	}

	// get company by id
	service.getCompany = function(cid){
		return $http({
			method : 'GET',
			url : 'rest/company/' + cid
		});
	}

	//create discount
	service.createDiscount = function(discount, lid) {
		var user = checkLogin();

		return $http({
			method : 'POST',
			url : 'rest/' + user.id + '/discount/location/' + lid,
			headers : {
		        'Content-Type' : 'application/json'
		     },
		     data : discount
		})
	};

	//index for discounts by user

	service.getDiscountsByUid = function () {
			var user = checkLogin();

			return $http({
				method: 'GET',
				url: 'rest/user/'+ user.id + '/discounts'
			})
	};

	//create company

	service.createCompany = function(company, tid) {
		var user = checkLogin();

		return $http({
			method : 'POST',
			url : 'rest/' + user.id + '/company/' + tid,
			headers : {
		        'Content-Type' : 'application/json'
		     },
			data : company
		});
	};

	service.createLocation = function(location, companyId, addressId) {
		return $http({
			method : 'POST',
			url : 'rest/location/' + companyId + '/' + addressId,
			headers : {
		        'Content-Type' : 'application/json'
		     },
			data : location
		})
	};

	service.createAddress = function(address) {
		return $http({
			method : 'POST',
			url : 'rest/address',
			headers : {
		        'Content-Type' : 'application/json'
		     },
			data : address
		});
	};


	//update Company
	service.updateCompany = function(company) {
		var user = checkLogin();

		return $http({
			method : 'PUT',
			url : 'rest/user/' + user.id + '/company/' + company.id,
			headers : {
		        'Content-Type' : 'application/json'
		     },
			data : company
		});
	};



	//distance between two points
	service.distance = function(origin, destination) {
		//maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=518+West+14th+Street+Houston+TX&destinations=12411+Wedgehill+Lane+Houston+TX
		console.log('getting distance');
		return $http({
			method : 'GET',
			url : 'https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=' + origin +'&destinations=' + destination + '&key=AIzaSyDhVMJxcZGC4H1OSLiRbyrRgyZwbpJ7XYs'
		})
	};


	service.allTypes = function(){
		return $http({
			method : 'GET',
			url : 'rest/company/all/types'
		})
	}

	service.getLatLong = function(address) {
		var street = address.street.split(' ').join('+');
		var city = address.city.split(' ').join('+');
		return $http({
			method : 'GET',
			url : 'https://maps.googleapis.com/maps/api/geocode/json?address=' + street + '+' + city + '+' + address.state + '+' + address.zip + '&key=AIzaSyDhVMJxcZGC4H1OSLiRbyrRgyZwbpJ7XYs'
		})
	}

	service.getLatLongForZip = function(zipcode) {
		return $http({
			method : 'GET',
			url : 'https://maps.googleapis.com/maps/api/geocode/json?address=' + zipcode + '&key=AIzaSyDhVMJxcZGC4H1OSLiRbyrRgyZwbpJ7XYs'
		})
	}

	service.updateUser = function(user){
		console.log('in method updateuser');
		if (!user) return;

		return $http({
			method : 'PUT',
			url : 'rest/user/'+ user.id,
			headers : {
				'Content-Type' : 'application/json'
			},
			data : user
		})
	}

	service.deleteLocation = function(location){
		console.log('in method deleteLocation');

		return $http({
			method : 'DELETE',
			url : 'rest/' + location.company.id + '/location/' + location.id,
			headers : {
				'Content-Type' : 'application/json'
			}
		})
	}

	service.deleteDiscount = function(did){
		console.log('in method deleteDiscount');
		var user = checkLogin();
		return $http({
			method : 'DELETE',
			url : 'rest/user/' + user.id + '/discount/' + did,
			headers : {
				'Content-Type' : 'application/json'
			}
		})
	}

	service.deleteCompany = function(companyId){
		console.log('in method deleteCompany');
		var user = checkLogin();
		return $http({
			method : 'DELETE',
			url : 'rest/'+ user.id +'/company/' + companyId,
			headers : {
				'Content-Type' : 'application/json'
			}
		})
	}

	//update discount

	service.updateDiscount = function (discount) {
		var user = checkLogin();

		return $http({
			method : 'PUT',
			url : 'rest/user/'+ user.id + '/discount/' + discount.id,
			headers : {
		        'Content-Type' : 'application/json'
		     },
			data : discount
		});
	}
	
	//update location
	
	service.updateLocation = function (location) {
		var user = checkLogin();
		return $http({
			method:'PUT',
			url:'rest/user/'+ user.id +'/company/' + location.company.id + '/location/' + location.id,
			headers:{
				'Content-Type' : 'application/json'
			},
			data: location
		});	
	}
	
	//update address
	
	service.updateAddress = function (address,lid) {
		var user = checkLogin();
		return $http({
			method: 'PUT',
			url: 'rest/user/' + user.id + '/location/'+ lid + '/address/'+address.id,
			headers:{
				'Content-Type' : 'application/json'
			},
			data: address
		});
	}
	


	return service;
})
