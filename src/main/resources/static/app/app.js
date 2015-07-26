
var chidra = angular.module('chidra',['flow','ngRoute','AmiCustModule']);


chidra.config(['$routeProvider','flowFactoryProvider','$httpProvider', 'CSRF_TOKEN',
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
		
