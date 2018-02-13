angular.module('appModule').component('location', {
	templateUrl : 'app/appModule/location/location.component.html',
	controllerAs : 'vm',
	controller : function($scope) {
		var vm = this;
		vm.location = null;
		vm.phoneNumber = null;
		
		function phoneNumberFormater(num){
			vm.phoneNumber = '(' + num.slice(0,3)
			+ ')' + num.slice(3,7) + '-' + num.slice(7,10);
		}
		
		$scope.$on('activeSelection', function(e,arg){
			//console.log('scope hit in discount');
			//console.log(arg);
			vm.location = arg;
			if(arg.phoneNumber != null && arg.phoneNumber.length === 10){
				phoneNumberFormater(arg.phoneNumber);
			} else {
				vm.phoneNumber = null;
			}
			
		});
	}
});
