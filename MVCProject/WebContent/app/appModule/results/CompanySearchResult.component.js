angular.module('appModule').component('companySearchResult', {
	templateUrl : 'app/appModule/results/CompanySearchResult.component.html',
	controllerAs : 'vm',
	controller : function(vetService,$scope) {
		var vm = this;
		vm.companySearchResult = "";
		reload();
		
		function reload(){
			  vetService.index().then(function(res){
				  vm.results = res.data;
			  }).catch(function(error){
				  console.log(error);
			  });
		  }
		
		$scope.$on('search-company', function(e,args){
			//console.log('scope hit');
			vm.companySearchResult = args.companySearchResult;
		})
	}
});