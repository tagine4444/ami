
var chidra = angular.module('chidra',['ngRoute','AmiCustModule']);

chidra.config(['$routeProvider',
                    function($routeProvider) {	
                      $routeProvider.
                        when('/', {
                        	templateUrl: '/app/components/amicust/newrequest.html',
                            controller: 'NewRequestCtrl'
                        }).
                        when('/searchRequest', {
                        	templateUrl: '/app/components/amicust/searchrequests.html',
                        	controller: 'SearchRequestCtrl'
                        }).
                        when('/profile', {
                        	templateUrl: '/app/components/amicust/profile.html',
                        	controller: 'ProfileCtrl'
                        }).
                        when('/help', {
                        	templateUrl: '/app/components/amicust/help.html',
                        	controller: 'HelpCtrl'
                        }).
                        when('/error', {
                          templateUrl: '/app/components/error/error.html',
                        }).
                        otherwise({
                          redirectTo: '/error'
                        });
}]);
		
