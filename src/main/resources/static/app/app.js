
var chidra = angular.module('chidra',['flow','ngRoute','AmiCustModule']);

//chidra.factory("animalService", function($q, $h){
//	   return {
//	       getAnimals: function(){
//	    	   var res = $http.get('/ami/animals');
//	    	   
//	    	   res.success(function(data, status, headers, config) {
//					deferred.resolve();
//				});
//				res.error(function(data, status, headers, config) {
//					deferred.reject('failure to get user name');
//				});	
//
//				return res;
//	       }
//	   }
//});

chidra.factory('animalService', function($http,$q, $routeParams){return {
	
	
	 getAnimals: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/animals');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get animals');
			});	

			return res;
	 },
	 
	 getSpecies: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/animals/species');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get species');
			});	

			return res;
	 },
	 
	 getAmiServices: function(){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/services');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AMI Services');
			});	

			return res;
	 },
	 
	 getAmiRequest: function(requestNumber){
		 
		 //var myRequestNumber = $route.current.requestNumber;
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest?requestNumber='+requestNumber);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AMI Services');
			});	

			return res;
	 }

	
}});

chidra.config(['$routeProvider','flowFactoryProvider','$httpProvider', 'CSRF_TOKEN',
                    function($routeProvider) {	
                      $routeProvider.
                        when('/', {
                        	templateUrl: "/app/components/amicust/newrequest.html",
                            controller: "NewRequestCtrl",
                            
                            resolve: {
                            	
                            	animals: ['animalService', function (animalService) {
                            		return animalService.getAnimals().then(
                            			function(result){
                            				return result.data;
                            			}	
                            		);
                                }],
                                species: ['animalService', function (animalService) {
                            		return animalService.getSpecies().then(
                            			function(result){
                            				return result.data;
                            			}	
                            		);
                                }],
                                amiServices:  ['animalService', function (animalService) {
                            		return animalService.getAmiServices().then(
                                			function(result){
                                				return result.data;
                                			}	
                                		);
                                    }],
                            
                            }
                        }).
                        when('/editRequest/:requestNumber', {
                        	templateUrl: '/app/components/amicust/editrequest.html',
                        	controller: 'EditRequestCtrl',
                        	 resolve: {
                             	
                             	amiRequest: ['animalService','$route', function (animalService, $route) {
                             		var requestNumber = $route.current.params.requestNumber;
                             		return animalService.getAmiRequest(requestNumber).then(
                             			function(result){
                             				return result.data;
                             			}	
                             		);
                                 }]
                        }
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
                        }),
                      
                      
                       function (flowFactoryProvider) {
                    	  flowFactoryProvider.defaults = {
                    		 
                    			  
		                	  target: '/ami/upload',
		                	  testChunks:false,
		                	  permanentErrors: [404, 500, 501],
		                	  maxChunkRetries: 1,
		                	  chunkRetryInterval: 5000,
		                	  simultaneousUploads: 4,
		                	  singleFile: true
                    	  };
                    	  flowFactoryProvider.on('catchAll', function (event) {
                    		  //console.log('catchAll', arguments);
                    		  
                    		  
                    		  //this.jsonResponse = arguments[2]; //Note this change
                              //json = this.jsonResponse;
                              //console.log(this.jsonResponse);
                              //json = angular.fromJson(this.jsonResponse);
                    	  });
                    	  // Can be used with different implementations of Flow.js
                    	  // flowFactoryProvider.factory = fustyFlowFactory;
                      },
                      function($httpProvider, CSRF_TOKEN) {
                          /**
                           * adds CSRF token to header
                           */
                          $httpProvider.defaults.headers.common['X-CSRF-TOKEN'] = CSRF_TOKEN;

                       }
                      
                      
}]);
		
