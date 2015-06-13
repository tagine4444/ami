(function(){
	
		var app = angular.module('AmiCustModule',[]);
		
		
		
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($scope, $http, $window,$location) {
			
			
			
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
			
			var newRequest = {
					'hospitalAndClientInfo'	: hospitalAndClientInfo,
					'patientInfo'			: patientInfo,
					'requestedServices'     : requestedServices,
					'vetObservation'		: vetObservation
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

		});
		
		
		
		// ============ SearchRequest ===============
		app.controller('SearchRequestCtrl', function ($scope, $window,$location) {
			$scope.page = 'searchRequests';

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