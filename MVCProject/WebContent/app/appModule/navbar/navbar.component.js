angular.module('appModule').component('navbar', {
	templateUrl : 'app/appModule/navbar/navbar.component.html',
	controllerAs : 'vm',
	controller : function(vetService, authService, $rootScope, $location, $filter,$scope) {
		var vm = this;
		vm.distances = ['1', '3', '5', '10', '15', '20', '25', '50']
		vm.searchTerm = "";
		vm.selected = null;
		vm.typeArr = [];
		vm.results = [];
		vm.typeId = null;
		vm.distance = vm.distances[0];
		vm.zipcode = undefined;

		vm.myPage = function(){

			$location.path('/mypage');
		}

		vm.loadTypes = function(){
			vetService.allTypes().then(function(res){
				vm.typeArr = res.data;
			});
		}

		vm.loadTypes();

		vm.checkLogin = function() {
			//console.log(authService.getToken().id);
			if(authService.getToken().id) {
				return true;
			}
			return false;
		}

		vm.typeFilter = function(){
			vm.results = $filter('typeFilter')(vm.results,vm.typeId);

		}

		vm.search = function() {
			vetService.search(vm.searchTerm).then(function(response) {
				$location.url('/');
				setTimeout(function(){
					vm.results = response.data;
					vm.typeFilter();
					vm.broadcast();
				}, 200)
			});
		}


		vm.broadcast = function(){
			console.log(vm.zipcode);
			$rootScope.$broadcast('search-event',{
				searchResults : vm.results,
				origin : vm.origin,
				distance : vm.distance,
				zipcode : vm.zipcode
			})
		}

		vm.createDiscount = function() {
			$location.path('/company/discount')
		}

		$scope.$on('origin', function(e, args) {
			vm.origin = args.origin;
			vm.search();
		})

		$scope.$on('originUpdate', function(e, args) {
			vm.origin = args.origin;
			console.log('navbar got origin');
			setTimeout(function() {

				vm.broadcast();
			}, 100)
		})
	}
});
