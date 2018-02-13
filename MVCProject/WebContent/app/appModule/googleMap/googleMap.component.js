angular.module('appModule').component('googleMap', {
	templateUrl : 'app/appModule/googleMap/googleMap.component.html',
	controllerAs : 'vm',
	controller : function($timeout, geolocator, $scope, $rootScope, NgMap, $filter, vetService) {
		var vm = this;
		vm.googleMapsUrl = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDhVMJxcZGC4H1OSLiRbyrRgyZwbpJ7XYs';
		vm.pos = {lat : 39.6088537, lng : -104.902828};
		vm.results = null;
		vm.markers = [];
		vm.origin = null;
		vm.destination = null;
		vm.selectedLocation = null;
		vm.distance = 1;
		vm.zipcode = undefined;
		vm.oldzip = null;
		vm.mapOptions = {
			center: vm.pos,
			markers : [],
			zoom: 11
		};

		NgMap.getMap().then(function(map) {
    			vm.map = map;
  		});

		// geolocator.geolocate().then(function(position){
        	// 	vm.mapOptions = {
          //     		center: position,
          //     		markers : [],
          //     		zoom: 11
          // 	}
		// 	vm.pos = position;
        	// 	$scope.$apply();
		// 	vm.broadcastOrigin();
      	// })

		vm.broadcastOrigin = function() {
			$rootScope.$broadcast('origin', {
				origin : vm.pos
			});
		}
		vm.broadcastOrigin();
		$scope.$on('search-event', function(e,args){
			console.log('map got search');
			vm.results = args.searchResults;
			vm.distance = args.distance;
			vm.oldzip = vm.zipcode;
			vm.zipcode = args.zipcode;
			setTimeout(function() {
				vm.updateMarkers();
				$scope.$apply();
			}, 100)
		})


		vm.updateMarkers = function() {
			if (vm.zipcode !== '' && vm.zipcode !== vm.oldzip) {
				vetService.getLatLongForZip(vm.zipcode).then(function(res) {
					vm.mapOptions.center = {
						lat : res.data.results[0].geometry.location.lat,
						lng : res.data.results[0].geometry.location.lng
					}
					vm.pos = {
						lat : res.data.results[0].geometry.location.lat,
						lng : res.data.results[0].geometry.location.lng
					}
					console.log('map got origin');
					vm.updateOrigin();
				})
			}
			vm.distanceFilter()
			vm.markers = [];
			var counter = 0;
			vm.results.forEach(function(item) {
				item.markerId = counter;
				vm.markers.push(item)
				counter++;
			})
			console.log(vm.markers);
			vm.mapOptions.markers = vm.markers;
		}

		vm.showDetail = function(event, markerId) {
			vm.mapOptions.markers.forEach(function(mark) {
				if (mark.id === markerId) {
					vm.selectedLocation = mark;
				}
			})
			vm.origin = vm.pos.lat + ',' + vm.pos.lng;
			vm.destination = vm.selectedLocation.address.lat + ',' + vm.selectedLocation.address.longitude;
			vm.map.showInfoWindow('info', this);
		}

		vm.distanceFilter = function() {
			vm.results = $filter('distanceFilter')(vm.results, vm.distance)
		}

		$scope.$on('activeSelection', function(e,arg){
			vm.selectedLocation = arg;
			vm.showDetail(null, vm.selectedLocation);
		});

		vm.updateOrigin = function() {
			$rootScope.$broadcast('originUpdate', {
				origin : vm.pos
			});
		}



	}
});
