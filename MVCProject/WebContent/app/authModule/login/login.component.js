angular.module('authModule').component('login', {
	templateUrl : 'app/authModule/login/login.component.html',
	controllerAs : 'vm',
	controller : function(authService) {
		var vm = this;

		vm.login = function(user) {
			authService.login(user);
			
		
		}
	}
});
