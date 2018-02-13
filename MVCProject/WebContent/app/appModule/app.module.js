angular.module('appModule', ['ngMessages', 'ngRoute', 'authModule', 'ngMap'])
.config(function($routeProvider) {

	$routeProvider
	.when('/company/discount', {
		template : '<discount-form></discount-form>'
	})

//	.when('/register', {
//			template : '<register></register>'
//		})

//	.when('/login', {
//			template : '<login></login>'
//		})

	.when('/mypage',{
		template: '<mypage></mypage>'
	})

	.when('/', {
			template : '<results></results>'
		})


});
