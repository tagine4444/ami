
var amiadmin = angular.module('amiadmin',[ 'ngRoute','angularMoment','AmiAdminModule']);

amiadmin.filter('jsonDate', ['$filter', function ($filter) {
    return function (input, format) {
        
        
        if(input == null){ return ""; } 
        
        var myDate = $filter('date')(new Date(input.millis),format);
        return myDate;
    };
}]);


amiadmin.factory('amiadminFactory', function($http,$q, $routeParams){return {

	getAmiRequestByLastNRecords: function(limit){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amiadmin/amirequest/bylastnrecords?limit='+ limit);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By last n records');
			});	

			return res;
	 },
	 
	 getAmiRequestForAdmin: function(requestNumber){
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amiadmin/amirequest?requestNumber='+requestNumber);
		 
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
		 var res = $http.get('/ami/amiadmin/amirequest/byanimals?animalName='+animalName);
		 
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
		 var res = $http.get('/ami/amiadmin/amirequest/byclientlastname?clientlastname='+clientlastname);
		 
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
		 var res = $http.get('/ami/amiadmin/amirequest/byreqdaterange?date1='+date1 +'&date2='+date2);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By submitted date range');
			});	

			return res;
	 },
	 
	getPendingRequestsAllHospitals: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/pending/allhospitals');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get hospital');
			});	

			return res;
	 },
	 
	 getAmendedCases: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amiadmin/amendedcases');
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get amended cases');
			});	

			return res;
	 },
	 
	 getCasesPendingReviewAllHospitals: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amicusthome/amirequest/pendingreview/allhospitals');
		 
		 res.success(function(data, status, headers, config) {
			 deferred.resolve();
		 });
		 res.error(function(data, status, headers, config) {
			 deferred.reject('failure to get hospital');
		 });	
		 
		 return res;
	 },
	 getCasesPendingAccounting: function(){ 
		 
		 var deferred = $q.defer();
		 var res = $http.get('/ami/amiadmin/casespendingaccounting');
		 
		 res.success(function(data, status, headers, config) {
			 deferred.resolve();
		 });
		 res.error(function(data, status, headers, config) {
			 deferred.reject('failure to get hospital');
		 });	
		 
		 return res;
	 },
	 
	 getAmiRequest: function(requestNumber){
		 
		 var deferred = $q.defer(); 
		 var res = $http.get('/ami/amiadmin/switchcasetoinprogress?caseNumber='+requestNumber);
		 
		 res.success(function(data, status, headers, config) {
				deferred.resolve();
			});
			res.error(function(data, status, headers, config) {
				deferred.reject('failure to get AmiRequests By request number');
			});	

			return res;
	 },
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
					'notes'		:'',
                    'contract'  :'Not Contract',
                    'accountSize': 'Smaill'
		 };
		 return hospital;
	 },
    getContractList: function(){
        var contractList = ['Not Contract', 'Contract'];
        return contractList;
    },
    
	 getAccountList: function(){
		 var contractList = ['Small', 'Medium', 'Large', 'Extra Large'];
		 return contractList;
	 }
	
}});





amiadmin.config(['$routeProvider','$httpProvider',
                function($routeProvider) {	
                  $routeProvider.
                    when('/hospitalAdminSubmittedRequests', {
                    	templateUrl: '/app/components/amiadmin/casequeue.html',
                    	controller: 'CaseQueueCtrl',
                    	   resolve: {
                             	
                             	pendingRequestsAllHospitals: ['amiadminFactory', function (amiadminFactory) {
                             		return amiadminFactory.getPendingRequestsAllHospitals().then(
                             			function(result){
                             				return result.data;
                             			}	
                             		);
                                 }],
                                 amendedCases: ['amiadminFactory', function (amiadminFactory) {
                                	 return amiadminFactory.getAmendedCases().then(
                                			 function(result){
                                				 return result.data;
                                			 }	
                                	 );
                                 }],
                       	   }
                    	
                    }).
                    when('/hospitalAdminReviewCases', {
                    	templateUrl: '/app/components/amiadmin/casereviewqueue.html',
                    	controller: 'CaseReviewQueueCtrl',
                    	resolve: {
                    		
                    		casesPendingReview: ['amiadminFactory', function (amiadminFactory) {
                    			return amiadminFactory.getCasesPendingReviewAllHospitals().then(
                    					function(result){
                    						return result.data;
                    					}	
                    			);
                    		}],
                    	}
                    
                    }).
                    when('/hospitalAdminAccounting', {
                    	templateUrl: '/app/components/amiadmin/accounting.html',
                    	controller: 'AccountingCtrl',
                    	resolve: {
                    		
                    		casesPendingAccounting: ['amiadminFactory', function (amiadminFactory) {
                    			return amiadminFactory.getCasesPendingAccounting().then(
                    					function(result){
                    						return result.data;
                    					}	
                    			);
                    		}],
                    	}
                    
                    }).
                    when('/hospitalAdminSearchCases', {
                    	templateUrl: '/app/components/amiadmin/searchCase.html',
                    	controller: 'CaseSearchCtrl',
                    
                    }).
                    when('/hospitalAdminProcessCase/:caseNumber', {
                    	templateUrl: '/app/components/amiadmin/processCase.html',
                    	controller: 'CaseProcessingCtrl',
                    	resolve: {
                    		
                    		myCase: ['amiadminFactory','$route', function (amiadminFactory, $route) {
                         		var caseNumber = $route.current.params.caseNumber;
                         		return amiadminFactory.getAmiRequest(caseNumber).then(
                         			function(result){
                         				var myResult = result.data;
                         				return myResult;
                         			}	
                         		);
                             }]
                    	}
                    
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
                    when('/fileUploadView/:file', {
                    	templateUrl: '/app/components/common/fileuploadview.html',
                    	controller: 'FileUploadViewCtrl', 
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
		
