angular.module('appModule').filter('distanceFilter',function(){
	return function(resultsList,distance){
		var results = [];
		resultsList.forEach(function(item){
			if(parseInt(item.distance) < parseInt(distance)){
				results.push(item);
			}
		});
		return results;
	}
})
