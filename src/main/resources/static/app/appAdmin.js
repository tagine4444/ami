
var amiadmin = angular.module('amiadmin',[ 'ngRoute','AmiAdminModule']);


amiadmin.factory('amiadminFactory', function($http,$q, $routeParams){return {
	
	getAllHospitalViews: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amiadminhome/hospitalview');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get hospital');
			});	

			return res;
	 },
	 
	 getHospitalView: function(hospitalId){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amiadminhome/hospitalviewbyhospitalid?hospitalId='+hospitalId);
		 
		 res.success(function(data, status, headers, config) {
			 deferred.resolve();
		 });
		 res.error(function(data, status, headers, config) {
			 deferred.reject('failure to get hospital');
		 });	
		 
		 return res;
	 },

	getNewMasterUser: function(){ 
		var newUser ={
				'user'   : '', 
				'pwd'   	 : '',
				'firstName'  : '',
				'lastName'   : '',
				'email'   	 : '',
				'occupation' : ''
			};
		return newUser;
	},
    getNewHospital: function(){ 
		 var hospital ={
				 	'name'  	: '', //default to phone
					'addresses'   : [],
					'phones'	: [],
					'emails'	: [],
					'notes'		:''
		 };
		 return hospital;
	 }
	
}});





amiadmin.config(['$routeProvider','$httpProvider',
                function($routeProvider) {	
                  $routeProvider.
                    when('/', {
                    	templateUrl: '/app/components/amiadmin/casequeue.html',
                    	controller: 'CaseQueueCtrl',
                    }).
                    when('/hospitalAdminSearch', {
                    	templateUrl: '/app/components/amiadmin/hospitaladminsearch.html',
                    	controller: 'HospitalAdminSearchCtrl',
                    	   resolve: {
                          	
                          	allHospitalViews: ['amiadminFactory', function (amiadminFactory) {
                          		return amiadminFactory.getAllHospitalViews().then(
                          			function(result){
                          				return result.data;
                          			}	
                          		);
                              }],
                    	   }
                    }).
                    when('/hospitalAdminCreate', {
                    	templateUrl: '/app/components/amiadmin/hospitaladmincreate.html',
                    	controller: 'HospitalAdminCreateCtrl',
                    }).
                    when('/hospitalAdminUpdate/:hospitalId', {
                    	templateUrl: "/app/components/amiadmin/hospitaladminupdate.html",
                        controller: "HospitalAdminUpdateCtrl",
                    	 resolve: {
                         	
                    		 myHospitalView: ['amiadminFactory','$route', function (amiadminFactory, $route) {
                         		
                    			var hospitalId = $route.current.params.hospitalId;
                    			
                         		return amiadminFactory.getHospitalView(hospitalId).then(
                         			function(result){
                         				var myResult = result.data;
                         				return myResult;
                         			});
                             }]
                    	 }// resolve
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
		
