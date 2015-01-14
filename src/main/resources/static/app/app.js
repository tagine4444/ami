
var chidra = angular.module('chidra',['ngRoute','AmiCustCtrl']);

chidra.config(['$routeProvider',
                    function($routeProvider) {
	
                      $routeProvider.
                        when('/', {
                          templateUrl: './app/components/amicust/amicust.html'
                          
                        }).
                        when('/amicust', {
                        	templateUrl: './app/components/amicust/amicust.html',
                            controller: 'AmiCustCtrl'
                        }).
                        when('/error', {
                          templateUrl: './app/components/error/error.html',
                        }).
                        otherwise({
                          redirectTo: '/error'
                        });
}]);
		
