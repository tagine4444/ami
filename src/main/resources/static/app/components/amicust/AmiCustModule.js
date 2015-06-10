(function(){
	
		var app = angular.module('AmiCustModule',[]);
		
		
		
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($scope, $http, $window,$location) {
			
			
			$scope.page = 'newRequest';
			
			$scope.labs = ['PCL', 'IDEXX', 'Antech'];
			$scope.speciesList =['Canine','Feline','Bovine','Birds'];
			$scope.breedsList = ['Greman Sheppard','Chiwawa','Boxer','Poodle'];
			$scope.labsList 			  = ['PCL', 'IDEXX', 'Antech'];
			$scope.animalSexList		= ['F/s', 'M/c', 'F','M'];
			$scope.animalAgeYearsList 	= [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17];
			$scope.animalAgeMonthsList = [0,1,2,3,4,5,6,7,8,9,10,11,12];
			
			
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
			
			var newRequest = {
					'hospitalAndClientInfo'	: hospitalAndClientInfo,
					'patientInfo'			: patientInfo
			};
			
			$scope.isEmployee =function(isEmployee){
				if(isEmployee){
					hospitalAndClientInfo.isEmployee	  = true;
				}else{
					hospitalAndClientInfo.isEmployee	  = false;
				}
				
			}
			
					
			//$scope.hospitalAndClientInfo = hospitalAndClientInfo;
			$scope.newRequest = newRequest;
			
			
			
			$scope.saveNewRequest = function(){
				
			   alert('1 newRequest: '+  $scope.newRequest);
			   
			   var res = $http.post('amicusthome/amirequest',$scope.newRequest);
				res.success(function(data, status, headers, config) {
					alert('success');
					$scope.message = data;
				});
				res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});	
				alert('done');
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