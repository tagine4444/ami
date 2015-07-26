(function(){
	
	
	 // I transform the error response, unwrapping the application dta from
    // the API response payload.
    function handleError( response ) {
        // The API response from the server should be returned in a
        // nomralized format. However, if the request was not handled by the
        // server (or what not handles properly - ex. server error), then we
        // may have to normalize it on our end, as best we can.
        if (
            ! angular.isObject( response.data ) ||
            ! response.data.message
            ) {
            return( $q.reject( "An unknown error occurred." ) );
        }
        // Otherwise, use expected error message.
        return( $q.reject( response.data.message ) );
    }
    // I transform the successful response, unwrapping the application data
    // from the API response payload.
    function handleSuccess( response ) {
        return( response.data );
    }
    
    
		var app = angular.module('AmiCustModule',['flow']);
		
		
		/**
		 * 
		 * when it thinks testing your app unit test with Karma,
		 * this solution was better than getting token via AJAX.
		 * Because low-level Ajax request correctly doesn't work on Karma
		 * 
		 * Helper idea to me :
		 * http://stackoverflow.com/questions/14734243/rails-csrf-protection-angular-js-protect-from-forgery-makes-me-to-log-out-on/15761835#15761835 
		 * 
		 */
		var csrftoken =  (function() {
		    // not need Jquery for doing that
		    var metas = window.document.getElementsByTagName('meta');
		
		    // finding one has csrf token 
		    for(var i=0 ; i < metas.length ; i++) {
		
		        if ( metas[i].name === "csrf-token") {
		
		            return  metas[i].content;       
		        }
		    }  
		
		})();

		// adding constant into our app
		
		app.constant('CSRF_TOKEN', csrftoken); 
		
		app.service('userNameService', function( $http, $q) {
			
			var deferred = $q.defer();
			this.getUserName = function() {
				
				var res = $http.get('/ami/getuserid.json');
				
				res.success(function(data, status, headers, config) {
					deferred.resolve();
				});
				res.error(function(data, status, headers, config) {
					deferred.reject('failure to get user name');
				});	

				return res;
	        }
			
		});
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($scope, $http, $window,$location, userNameService) {

			$scope.userName='';
			var promise = userNameService.getUserName();
			
			promise.then(function(response) {
				$scope.userName= response.data.userName;
			}, function(response) {
			  alert( response.data.message);
			}, function(update) {
//			  alert('Got notification: ' + update);
			});
			
			$scope.page = 'newRequest';
			
			$scope.labs 				= ['PCL', 'IDEXX', 'Antech'];
			$scope.speciesList 			= ['Canine','Feline','Bovine','Birds'];
			$scope.breedsList 			= ['Greman Sheppard','Chiwawa','Boxer','Poodle'];
			$scope.labsList 			= ['PCL', 'IDEXX', 'Antech'];
			$scope.animalSexList		= ['F/s', 'M/c', 'F','M'];
			$scope.animalAgeYearsList 	= [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
			$scope.animalAgeMonthsList =  [0,1,2,3,4,5,6,7,8,9,10,11,12];
			$scope.serviceCategory = '';
			
			
			var contrastRadioGraphy 	= ['Myelogram','Arthrogram'];
			var computedTomography 		= ['Brain With Contrast','Brain With Contrast and/or CSF tap'];
			var radiographyFluoroscopy  = ['Thorax','Abdomen','Pelvis'];
			var ultrasound              = ['Abdomen','Heart','Uterus'];
			
			
			$scope.servicesList = {
				'Contrast Radiography'   :contrastRadioGraphy,
				'Computed Tomography'    :computedTomography,
				'Radiography/Fluoroscopy':radiographyFluoroscopy,
				'Ultrasound'             :ultrasound
			};
			
			
			var hospitalAndClientInfo = { };
			hospitalAndClientInfo.labs 			  = '';
			hospitalAndClientInfo.vet			  = '';
			hospitalAndClientInfo.labAccount 	  = '';
			hospitalAndClientInfo.clientFirstName = '';
			hospitalAndClientInfo.clientLastName  = '';
			hospitalAndClientInfo.clientId		  = '';
			hospitalAndClientInfo.isEmployee	  = false;
			
			var patientInfo = { };
			patientInfo.animalName 		= '';
			patientInfo.animalSex		='';
			patientInfo.animalWeight 	= '';
			patientInfo.animalWeightUom = '';
			patientInfo.animalAgeYears 	= 0;
			patientInfo.animalAgeMonths 	= 0;
			patientInfo.species 		= '';
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
			vetObservation.fractious	=false;
			vetObservation.died			=false;
			vetObservation.euthanized	=false;
			vetObservation.exam ='';
			vetObservation.tentativeDiagnosis = '';
			
			var imagesAndDocuments = {};
			imagesAndDocuments.hasDocumentDeliveredByUpload  = false;
			imagesAndDocuments.hasDocumentDeliveredByCarrier = false;
			imagesAndDocuments.hasDocumentDeliveredByEmail   = false;
			imagesAndDocuments.notes = '';
			
			var newRequest = {
					'hospitalAndClientInfo'	: hospitalAndClientInfo,
					'patientInfo'			: patientInfo,
					'requestedServices'     : requestedServices,
					'vetObservation'		: vetObservation,
					'imagesAndDocuments'    : imagesAndDocuments
			};
			
			$scope.isEmployee =function(isEmployee){
				if(isEmployee){
					hospitalAndClientInfo.isEmployee	  = true;
				}else{
					hospitalAndClientInfo.isEmployee	  = false;
				}
				
			}
			
			$scope.isInterpretationOnly =function(isInterpretationOnly){
				if(isInterpretationOnly){
					requestedServices.isInterpretationOnly	  = true;
					requestedServices.selectedServices = [];
				}else{
					requestedServices.isInterpretationOnly	  = false;
				}
				
			}
			
			$scope.selectThisService = function(aServiceType, aService){
				requestedServices.selectedServices.push(aServiceType +"-"+aService);
			}
			
			$scope.deleteFilteredItem = function(hashKey, sourceArray){
				  angular.forEach(sourceArray, function(obj, index){
					  
				    // sourceArray is a reference to the original array passed to ng-repeat, 
				    // rather than the filtered version. 
				    // 1. compare the target object's hashKey to the current member of the iterable:
				    if (obj.$$hashKey === hashKey) {
				      // remove the matching item from the array
				      sourceArray.splice(index, 1);
				      // and exit the loop right away
				      return;
				    };
				  });
				}
			
			//$scope.hospitalAndClientInfo = hospitalAndClientInfo;
			$scope.newRequest = newRequest;
			
			$scope.saveNewRequest = function(){
				
			   var res = $http.post('amicusthome/amirequest',$scope.newRequest);
				res.success(function(data, status, headers, config) {
					$scope.message = data;
				});
				res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});	
			}
			
			
			$scope.uploader = {
					fileAdded: function ($flow, $file, $message) {
						console.log($flow, $file, $message); // Note, you have to JSON.parse message yourself.
					    $file.msg = $message;// Just display message for a convenience
					  },
				
					fileSubmitted: function ($flow, $file, $message) {
						console.log($flow, $file, $message); // Note, you have to JSON.parse message yourself.
					    //$file.msg = $message;// Just display message for a convenience
					}
						
				};
				
			    $scope.upload = function () {
			      $scope.uploader.flow.upload(); 
			  
			    }

		});
		
		
		
		// ============ SearchRequest ===============
		app.controller('SearchRequestCtrl', function ($scope, $http, $window,$location) {
			$scope.page = 'searchRequests';
			
			
			$scope.uploader = {
				fileAdded: function ($flow, $file, $message) {
					console.log($flow, $file, $message); // Note, you have to JSON.parse message yourself.
				    $file.msg = $message;// Just display message for a convenience
				  },
			
				fileSubmitted: function ($flow, $file, $message) {
					console.log($flow, $file, $message); // Note, you have to JSON.parse message yourself.
				    //$file.msg = $message;// Just display message for a convenience
				}
					
			};
			
			
			
		    $scope.upload = function () {
		      $scope.uploader.flow.upload(); 
		  
		    }
			
		});
	
		
		// ============ Profile ===============
		app.controller('ProfileCtrl', function ($scope, $window,$location) {
			$scope.page = 'profile';

		});
		
		
		// ============ Help ===============
		app.controller('HelpCtrl', function ($scope, $window,$location) {
			$scope.page = 'help';

		});
})();	