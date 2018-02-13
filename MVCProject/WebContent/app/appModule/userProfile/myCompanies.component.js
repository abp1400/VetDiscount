angular.module('appModule').component('myCompanies', {
	templateUrl : 'app/appModule/userProfile/myCompanies.component.html',
	controllerAs : 'vm',
	controller : function(authService,vetService,$rootScope) {
		var vm = this;
		vm.results = [];
		vm.editUser = null;
		
		
		vetService.companybyUid().then(function(res){
			vm.results = res.data;
		  }).catch(function(error){
			  console.log(error);
		  });
		
		
		var companyReload = function() {
			
			vetService.companybyUid().then(function(res){
				vm.results = res.data;
				vm.makeActive(vm.results[0]);
			  }).catch(function(error){
				  console.log(error);
			  });
			
		}
		
		
//		vm.makeActive = function(result){
//			vm.active = result;
//			$rootScope.$broadcast('activeSelection', vm.active);
//			
//		}
			
		vm.setEditCompany = function (company)	{
		vm.editCompany = company;
		
		}
		
		vm.doEditCompany = function (company) {
			vetService.updateCompany(company).then(companyReload);
		}
		
		vm.deleteCompany = function(company){
			vetService.deleteCompany(company.id).then(companyReload);
			companyReload();
		}
	}
});
