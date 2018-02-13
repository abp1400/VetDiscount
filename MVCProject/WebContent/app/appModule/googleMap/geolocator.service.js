angular.module('appModule').factory('geolocator', function($window) {
	var service = {};

	service.geolocate = function() {
		return new Promise(function(resolve, reject) {
			$window.navigator.geolocation.getCurrentPosition(function(position) {
				resolve({
					lat : position.coords.latitude,
					lng : position.coords.longitude
				})
			})
		})
	}

	return service;
})
