angular.module('appModule').component('myLocations', {
	templateUrl : 'app/appModule/userProfile/myLocations.component.html',
	controllerAs : 'vm',
	controller : function(authService,vetService,$rootScope) {
		var vm = this;
		vm.results = [];
		vm.active = null;
		vm.editLocation = null;
		vm.editLocationAddress=null;
		
		
		var reload = function(){vetService.getLocationsbyUid().then(function(res){
			vm.results = res.data;
		  }).catch(function(error){
			  console.log(error);
		  });
		};
		reload();
		
		vm.makeActive = function(result){
			vm.active = result;
			$rootScope.$broadcast('activeSelection', vm.active);
		}
		
		vm.deleteLocation = function(location){
			vetService.deleteLocation(location).then(reload);
		}
		
		vm.setEditLocation = function(location) {
			vm.editLocation = location;
		}
		
		vm.setEditLocationAddress = function (address) {
			vm.editLocationAddress = address;
			
		}
		
		vm.doEditLocation = function (location) {
			vetService.updateLocation (location).then(reload);
		}
		
		vm.doEditAddress = function (address) {
			var lid = vm.editLocation.id;
			vetService.updateAddress(address,lid).then(reload);
		}
		
		
		
	}
});