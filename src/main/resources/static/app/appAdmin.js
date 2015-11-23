
var amiadmin = angular.module('amiadmin',[ 'ngRoute','AmiAdminModule']);
amiadmin.config(['$routeProvider','$httpProvider',
                function($routeProvider) {	
                  $routeProvider.
                    when('/', {
                    	templateUrl: '/app/components/amiadmin/casequeue.html',
                    	controller: 'CaseQueueCtrl',
                    }).
                    when('/hospitalAdmin', {
                    	templateUrl: '/app/components/amiadmin/hospitaladmin.html',
                    	controller: 'HospitalAdminCtrl',
                    }).
                    when('/error', {
                      templateUrl: '/app/components/error/error.html',
                    }).
                    otherwise({
                      redirectTo: '/error'
                    }),
                    function($httpProvider, CSRF_TOKEN) {
                      /**
                       * adds CSRF token to header
                       */
                      $httpProvider.defaults.headers.common['X-CSRF-TOKEN'] = CSRF_TOKEN;

                   },
                   
                   function($modalProvider) {
                	   angular.extend($modalProvider.defaults, {
                	     html: true
                	   });
                   }
}]);
		
