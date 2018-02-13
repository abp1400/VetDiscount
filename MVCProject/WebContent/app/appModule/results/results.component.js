angular.module('appModule').component('results', {
	templateUrl : 'app/appModule/results/results.component.html',
	controllerAs : 'vm',
	controller : function(vetService,$scope,$rootScope,$filter) {
		var vm = this;
		//companies = [];
		vm.results = [];
		vm.active = null;
		vm.distance = null;
		vm.typeId = null;
		vm.origin = {};

		$scope.roundedDistance = function (d){
			return (Math.round(d * 100) / 100)
		}
		
		function reload(){
		vetService.index().then(function(res){
			vm.results = res.data;
			vm.getDistances();
			vm.makeActive(vm.results[0]);
		  }).catch(function(error){
			  console.log(error);
		  });
		}

		vm.makeActive = function(result){
			vm.active = result;
			$rootScope.$broadcast('activeSelection', vm.active);
		}

		$scope.$on('search-event', function(e,args){
			console.log('results got search');
			vm.results = args.searchResults;
			vm.origin = args.origin;
			vm.distance = args.distance;
			vm.getDistances();
			vm.distanceFilter();
			console.log(vm.origin);
			console.log(vm.results);
			if(vm.results.length === 0){
				vm.makeActive(null);
				console.log(vm.active);
			} else {
				vm.makeActive(vm.results[0]);
			}
			vm.typeId = args.type;
			$scope.$apply();
		})

		vm.distanceFilter = function() {
			vm.results = $filter('distanceFilter')(vm.results, vm.distance)
		}

		vm.getDistances = function() {
			var toRadians = function(num) {
				return num * (Math.PI / 180);
			}
			vm.results.forEach(function(item){
				if (item.distance === undefined) {
					var lat1 = item.address.lat;
					var lat2 = vm.origin.lat;
					var lon1 = item.address.longitude;
					var lon2 = vm.origin.lng;
					var φ1 = toRadians(lat1), φ2 = toRadians(lat2), Δλ = toRadians(lon2-lon1), R = 6371e3; // gives d in metres
					var d = Math.acos( Math.sin(φ1)*Math.sin(φ2) + Math.cos(φ1)*Math.cos(φ2) * Math.cos(Δλ) ) * R;
					var miles = d/1609.344;
					item.distance = miles;
				}
			})
		}

		// $scope.$on('origin', function(e, args) {
		// 	vm.origin = args.origin;
		// 	// reload();
		// })
		$scope.$on('originUpdate', function(e, args) {
			vm.origin = args.origin;
			console.log('results got origin');
		})
	}
});
