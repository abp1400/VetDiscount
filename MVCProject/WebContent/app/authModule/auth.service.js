angular.module('authModule').factory('authService', function($http, $cookies, $location) {
	var service = {};
	// CHANGE THE ROUTES

	service.getToken = function() {
		var user = {};
		user.id = $cookies.get('uid');
		user.email = $cookies.get('email');
		user.username = $cookies.get('username');
		return user;
	}

	var saveToken = function(user) {
		$cookies.put('uid', user.id);
		$cookies.put('email', user.email);
		$cookies.put('username',user.username);
	}

	
	service.register = function(user) {
		return $http({
			method : 'POST',
			url : 'rest/auth/register',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : user
		}).then(function(response) {
			saveToken(response.data);
			$location.path('/mypage');
		})
	}

	var removeToken = function() {
		$cookies.remove('uid');
		$cookies.remove('email');
		$cookies.remove('username');
	}

	service.login = function(user) {
		return $http({
			method : 'POST',
			url : 'rest/auth/login',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : user
		}).then(function(response) {
			saveToken(response.data);
			$location.path('/mypage');
		})
	}

	service.logout = function() {
		return $http({
			method : 'POST',
			url : 'rest/auth/logout'
		}).then(function(response) {
			removeToken();
			$location.path('/');
		})
	}

	return service;
});
