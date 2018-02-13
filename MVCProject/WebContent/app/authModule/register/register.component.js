angular.module('authModule').component('register', {
	templateUrl : 'app/authModule/register/register.component.html',
	controller : function(authService) {
		var vm = this;

		vm.registerUser = function(user) {
			if (user.password === user.confirm) {
				delete user.confirm;
				authService.register(user);
			}
			else {
				console.log('passwords do not match');
			}
		}
	},
	controllerAs : 'vm'
});
