
var chidra = angular.module('chidra',['flow','ngRoute','mgcrea.ngStrap','angularMoment', 'AmiCustModule']);

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


chidra.filter('jsonDate', ['$filter', function ($filter) {
    return function (input, format) {
        
        if(input == null){ return ""; } 
        
        var myDate = $filter('date')(new Date(input.millis),format);
        return myDate;
    };
}]);


//chidra.factory('newUserFactory', function($http,$q, $routeParams) {return {
//	
//	getNewUser: function(){
//		var newUser ={
//				'userName'  : 'chq-joe',
//				'pwd'       : 'totopwd',
//				'pwd'       : 'totopwd',
//				'firstName' : 'joe',
//				'lastName'  : 'blow',
//				'occupation': 'tech',
//				'email'		: 'toto@hotmail.com',
//				'isVet'     : false
//			};
//	}
//}
//	
//}


chidra.factory('newUserFactory', function($http,$q, $routeParams) {return {
		getNewUser: function(){ 
			var newUser ={
					'userName'  : '',
					'pwd'       : '',
					'firstName' : '',
					'lastName'  : '',
					'occupation': 'Occupation',
					'occupationOther': '',
					'email'		: ''
				};
			return newUser;
		}
	}
});
chidra.factory('amiRequestFactory', function($http,$q, $routeParams) {return {
	getNewAmiRequest: function(){ 
		 
		var hospitalAndClientInfo = { };
		hospitalAndClientInfo.vet			  = '';
		hospitalAndClientInfo.clientFirstName = '';
		hospitalAndClientInfo.clientLastName  = '';
		hospitalAndClientInfo.clientId		  = '';
		hospitalAndClientInfo.isEmployee	  = false;
		
		var patientInfo = { };
		patientInfo.animalName 		= '';
		patientInfo.animalSex		= 'Sex';
		patientInfo.animalWeight 	= '';
		patientInfo.animalWeightUom = '';
		patientInfo.animalAgeYears 	= '';
		patientInfo.animalAgeMonths = '';
		patientInfo.ageLabel		= '';
		patientInfo.species 		= 'Select Species';
		patientInfo.breeds 			= '';
		
		var requestedServices = {};
		requestedServices.isInterpretationOnly = true;
		requestedServices.isStat = false;
		requestedServices.selectedServices = [];
		
		
		var vetObservation = {};
		vetObservation.anesthetized =false;
		vetObservation.sedated		=false;
		vetObservation.fasted		=false;
		vetObservation.enema		=false;
		vetObservation.painful		=false;
		vetObservation.fractious	=false;
		vetObservation.shocky		=false;
		vetObservation.dyspneic		=false;
		vetObservation.died			=false;
		vetObservation.euthanized	=false;
		vetObservation.exam ='';
		vetObservation.tentativeDiagnosis = '';
		
		var imagesAndDocuments = {};
		imagesAndDocuments.labs 			  = 'Select Lab';
		imagesAndDocuments.labAccount 	  = '';
		imagesAndDocuments.hasDocumentDeliveredByUpload  = false;
		imagesAndDocuments.hasDocumentDeliveredByCarrier = false;
		imagesAndDocuments.notes = '';
		imagesAndDocuments.fileUploads = [];
		 
		var newRequest = {
				'requestNumber'         :'',
				'hospitalAndClientInfo'	: hospitalAndClientInfo,
				'patientInfo'			: patientInfo,
				'requestedServices'     : requestedServices,
				'vetObservation'		: vetObservation,
				'imagesAndDocuments'    : imagesAndDocuments
		};
		
		
		return newRequest;
	 },
	 
	 getAmiRequest: function(requestNumber){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest?requestNumber='+requestNumber);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By request number');
			});	

			return res;
	 },
	 getAmiRequestByAnimalName: function(animalName){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/byanimals?animalName='+animalName);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By animal names');
			});	

			return res;
	 },
	 getAmiRequestByClientLastName: function(clientlastname){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/byclientlastname?clientlastname='+clientlastname);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By client last names');
			});	

			return res;
	 },
	 getAmiRequestBySubmittedDateRange: function(date1, date2){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/byreqdaterange?date1='+date1 +'&date2='+date2);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By submitted date range');
			});	

			return res;
	 },
	 getAmiRequestByLast50Records: function(){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/bylastnrecords');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By last n records');
			});	

			return res;
	 }
	 
}});


chidra.factory('animalService', function($http,$q, $routeParams){return {
	
	
	getHospital: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/hospital');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get hospital');
			});	

			return res;
	 },
	 
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
	 
	 getUploadedFiles: function(requestNumber){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/uploadedfiles?requestNumber='+requestNumber);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get uploaded files');
			});	

			return res;
	 },
	 
	 getPendingAmiRequest: function(){
		 
		 //var myRequestNumber = $route.current.requestNumber;
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/pending');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get Pending Ami Requests');
			});	

			return res;
	 },
	 getDraftAmiRequest: function(){
		 
		 //var myRequestNumber = $route.current.requestNumber;
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/draft');
		 
		 res.success(function(data, status, headers, config) {
			 deferred.resolve();
		 });
		 res.error(function(data, status, headers, config) {
			 deferred.reject('failure to get draft Ami Requests');
		 });	
		 
		 return res;
	 }
	 
	 

	
}});

chidra.config(['$routeProvider','flowFactoryProvider','$httpProvider', '$modalProvider','CSRF_TOKEN',
                    function($routeProvider) {	
                      $routeProvider.
                        when('/', {
                        	templateUrl: "/app/components/amicust/newrequest.html",
                            controller: "NewRequestCtrl",
                            
                            resolve: {
                            	
                                 myAmiRequest: ['amiRequestFactory','$route', function (amiRequestFactory, $route) {
                                	 return amiRequestFactory.getNewAmiRequest();
                                 }],
                            	
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
//                        	templateUrl: '/app/components/amicust/editrequest.html',
//                        	controller: 'EditRequestCtrl',
                        	templateUrl: "/app/components/amicust/newrequest.html",
                            controller: "NewRequestCtrl",
                        	 resolve: {
                             	
                             	myAmiRequest: ['amiRequestFactory','$route', function (amiRequestFactory, $route) {
                             		var requestNumber = $route.current.params.requestNumber;
                             		return amiRequestFactory.getAmiRequest(requestNumber).then(
                             			function(result){
                             				var myResult = result.data;
                             				return myResult;
                             			}	
                             		);
                                 }],
                                 
                                 
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
                        	 
                        	 
                        	 }// resolve
                        }).
                        when('/searchRequest', {
                        	templateUrl: '/app/components/amicust/searchrequests.html',
                        	controller: 'SearchRequestCtrl',
                        	 
                            resolve: {
                            	pendingRequests: ['animalService', function (animalService) {
                            		return animalService.getPendingAmiRequest().then(
                            			function(result){
                            				return result.data;
                            			}	
                            		);
                                }],
                                draftRequests: ['animalService', function (animalService) {
                            		return animalService.getDraftAmiRequest().then(
                            			function(result){
                            				return result.data;
                            			}	
                            		);
                                }]
                        
                            }
                        }).
                        when('/profile', {
                        	templateUrl: '/app/components/amicust/profile.html',
                        	controller: 'ProfileCtrl',
                        	resolve: {
                        		myHospital: ['animalService', function (animalService) {
                            		return animalService.getHospital().then(
                            			function(result){
                            				return result.data;
                            			}	
                            		);
                                }]
                        	 
                        	 
                        	 }// resolve
                        }).
                        when('/newUser', {
                        	templateUrl: '/app/components/amicust/newuser.html',
                        	controller: 'NewUserCtrl',
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

                       },
                       
                       function($modalProvider) {
                    	   angular.extend($modalProvider.defaults, {
                    	     html: true
                    	   });
                       }
                      
}]);
		
