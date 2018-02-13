angular.module('appModule').component('myDiscounts', {
	templateUrl : 'app/appModule/userProfile/myDiscounts.component.html',
	controllerAs : 'vm',
	controller : function(authService,vetService,$rootScope) {
		var vm = this;
		vm.results = [];
		vm.active = null;
		vm.editDiscount = null;
		
		
		var reload = function(){vetService.getDiscountsByUid().then(function(res){
			vm.results = res.data;
			vm.makeActive(vm.results[0]);
		  }).catch(function(error){
			  console.log(error);
		  });
		};
		
		reload();
		
		vm.makeActive = function(result){
			vm.active = result;
			$rootScope.$broadcast('activeSelection', vm.active);
		}
		
		vm.deleteDiscount = function(discountId){
			vetService.deleteDiscount(discountId).then(reload);
		}
		
		vm.setEditDiscount = function(discount) {
			
			vm.editDiscount = discount;
		}
		
		vm.doEditDiscount = function (discount) {
			vetService.updateDiscount(discount).then(reload);
			
		}
		
	}
});