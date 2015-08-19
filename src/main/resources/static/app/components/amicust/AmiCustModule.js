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
			
			this.getUser = function() {
				
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
		
		app.service('amiService', function( $http, $q, userNameService) {
				
				var deferred = $q.defer();
					
				this.getAmiUser = function(){	
					
					var promise = userNameService.getUser();
					
					promise.then(function(response) {
						
						var user =  response.data.user;
						var hospitalName =  response.data.hospitalName;

						var amiUser = {
							getUserName: function(){
								return user;
							},
							getHospitalName: function(){
								return hospitalName;
							}
						};
						
						deferred.resolve(amiUser);
					}, function(response) {
					  alert( response.data.message);
					}, function(update) {
					});	
					
					
					return deferred.promise;
			}
			
		});
		
		
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($scope, $http, $window,$location, amiService,animals,species, amiServices) {
			
			$scope.page = 'newRequest';
			
			$scope.saveAction = '';
			
			var Years  = 'Years';
			var Months = 'Months';
			var ZeroYears = '0 year';
			var ZeroMonths = '0 months';
			
			for (var i in animals) {
			
			  var anAnimal = animals[i];
			  var aSpecies = anAnimal.id;
			  var anAnimalBreed = anAnimal.breeds;
				
			  if(aSpecies== 'Canine'){
				  $scope.breedsCanine =  anAnimalBreed;
			  }else if(aSpecies== 'Feline' ){
					$scope.breedsFeline =  anAnimalBreed;
			  }else if(aSpecies == 'Bovine'){
					$scope.breedsBovine =  anAnimalBreed;
			  }
			  else if(aSpecies == 'Birds'){
				$scope.breedsBirds  =  anAnimalBreed;
				
			  } else if( aSpecies == 'Others'){
					$scope.breedsOthers =  anAnimalBreed;
			  }
			  
			}
			
			$scope.speciesList = species;
			$scope.speciesList.splice (0,0, 'Select Species');
			
			var promise = amiService.getAmiUser();
			
			promise.then(function(amiUser) {
				$scope.userName     = amiUser.getUserName();
				$scope.hospitalName = amiUser.getHospitalName();
			}, 
			function(response) {
			  alert( response.data.message);
			}, 
			function(update) {
				alert( response.data.message);
			});
			
			$scope.animalWeightUomList = ['LB', 'KG'];
			$scope.labsList 			= ['Select Lab','PCL', 'IDEXX', 'Antech'];
			$scope.breedsList 			= ['Select Breed'];
			$scope.animalSexList		= ['Sex','F/s', 'M/c', 'F','M'];
			$scope.animalAgeYearsList 	= [Years ];
			$scope.animalAgeMonthsList =  [Months];
			$scope.serviceCategory = '';
			
			
			for(var i=0; i<=100 ; i++){
				if(i>1){
					var aYear = i + ' years';;
				}else if(i=1){
					var aYear = i + ' year';
				}else if(i==0){
					var aYear = ZeroYears;
				}

				
				$scope.animalAgeYearsList.push(aYear);
			}
			
			for(var i=1; i<=12 ; i++){
				if(i>1){
					var aYear = i + ' months';
				}else{
					
					var aYear = i + ' month';
				}
				
				$scope.animalAgeMonthsList.push(aYear);
			}
			
			var servicesMap = new Map();
				
			var servicesMap1 =	angular.fromJson(amiServices);
			angular.copy(servicesMap1, servicesMap);
			
			
			var contrastRadioGraphy = null;
			var computedTomography = null;
			var radiographyFluoroscopy  = null;
			var ultrasound              = null;
				
			for(var i in servicesMap) {
			    if (servicesMap.hasOwnProperty(i)) {
			    
			    	var ServiceCategory = i;
			    	var servicesForCategory = servicesMap[ServiceCategory];
			    	
			    	if( ServiceCategory =="CONTRAST_RADIOGRAPHY"){
			    		 contrastRadioGraphy = servicesForCategory;
			    		
			    	} else if(ServiceCategory =="COMPUTED_TOMOGRAPHY"){
			    		computedTomography = servicesForCategory;
			    		
			    	} else if(ServiceCategory =="RADIOGRAPHY_FLUOROSCOPY"){
			    		radiographyFluoroscopy = servicesForCategory;
					    	
					}else if(ServiceCategory =="ULTRASOUND"){
						ultrasound = servicesForCategory;;
					}
			    }
			}
			
			
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
			patientInfo.animalAgeYears 	= '';
			patientInfo.animalAgeMonths = '';
			patientInfo.ageLabel		= '';
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
					'requestNumber'         :'',
					'hospitalAndClientInfo'	: hospitalAndClientInfo,
					'patientInfo'			: patientInfo,
					'requestedServices'     : requestedServices,
					'vetObservation'		: vetObservation,
					'imagesAndDocuments'    : imagesAndDocuments
			};
			
			$scope.newRequest = newRequest;
			
			$scope.updateServicesListByCategory = function(){
			
				var selectedSvcCategory = $scope.serviceCategory;
				var isContrastRadiography = new String(selectedSvcCategory).toLowerCase().indexOf("contrast radiography") > -1;
				var isComputedTomography = new String(selectedSvcCategory).toLowerCase().indexOf("computed tomography") > -1;
				var isRadiographyFluoroscopy = new String(selectedSvcCategory).toLowerCase().indexOf("radiography/fluoroscopy") > -1;
				var isUltrasounds = new String(selectedSvcCategory).toLowerCase().indexOf("ultrasound") > -1;
				
				
				if(isContrastRadiography){
					$scope.servicesListByCategory= contrastRadioGraphy;
				}else if(isComputedTomography){
					$scope.servicesListByCategory= computedTomography;
					
				}else if(isRadiographyFluoroscopy){
					$scope.servicesListByCategory= radiographyFluoroscopy;
					
				}else if(isUltrasounds){
					$scope.servicesListByCategory= ultrasound;
				}
				
			}
			
			$scope.disableBreedList = true;
			$scope.updateBreedListBox = function(){
				var speciesValue        = newRequest.patientInfo.species;
				
				$scope.disableBreedList = false;
				var isCanine = new String(speciesValue).toLowerCase().indexOf("canine") > -1;
				var isFeline = new String(speciesValue).toLowerCase().indexOf("feline") > -1;
				var isBovine = new String(speciesValue).toLowerCase().indexOf("bovine") > -1;
				var isBirds = new String(speciesValue).toLowerCase().indexOf("birds") > -1;
				var isOthers = new String(speciesValue).toLowerCase().indexOf("others") > -1;
				
				if(isCanine){
					$scope.breedsList  = $scope.breedsCanine;
				}else if( isFeline){
					$scope.breedsList  = $scope.breedsFeline;
				}else if(isBovine){
					$scope.breedsList  = $scope.breedsBovine;
				}else if(isBirds){
					$scope.breedsList  = $scope.breedsBirds;
				}
				else if(isOthers){
					$scope.breedsList  = $scope.breedsOthers;
				}
				else{
					$scope.breedsList  = ['Select Breed'];
					$scope.disableBreedList = true;
				}
			}
			
			$scope.updateAnimalYearsLabel =function(){

				var yearValue     = $scope.newRequest.patientInfo.animalAgeYears;
				var yearIsEmpty   =   new String(yearValue).indexOf("Years") > -1;
				
				var monthValue     = $scope.newRequest.patientInfo.animalAgeMonths;
				var monthIsEmpty   =   new String(monthValue).indexOf("Months") > -1;
				
				if( yearIsEmpty && monthIsEmpty){
					$scope.newRequest.patientInfo.ageLabel = '';
				
				}else if (!yearIsEmpty && monthIsEmpty) {
					$scope.newRequest.patientInfo.ageLabel = yearValue + ' old';
					
				}else if( yearIsEmpty && !monthIsEmpty){
					$scope.newRequest.patientInfo.ageLabel =  monthValue +' old';
				}
				
				else if( !yearIsEmpty && !monthIsEmpty){
					$scope.newRequest.patientInfo.ageLabel = yearValue + ' and '+ monthValue +' old';
				}
				
			}
			$scope.isShowAnimalMonthLabel =function(){
				
				if(newRequest.patientInfo.animalAgeMonths = Months || newRequest.patientInfo.animalAgeMonths == ZeroMonths){
					return false;
				}
				return true;
			}
			
			
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
				requestedServices.selectedServices.push(aServiceType +" - "+aService.name);
			}
			
			
			
			$scope.deleteFilteredItem = function(anItem){
				
				
				for(var i = $scope.newRequest.requestedServices.selectedServices.length - 1; i >= 0; i--) {
				    if($scope.newRequest.requestedServices.selectedServices[i] === anItem) {
				    	$scope.newRequest.requestedServices.selectedServices.splice(i, 1);
				    }
				}
			}
		
			$scope.setAction= function(action){
				$scope.saveAction = action;
			}
			
			$scope.isSubmit= function(){
				if($scope.saveAction == 'submit'){
					return true;
				}else{
					return false;
				}
					
			}
			
			
			
			$scope.saveNewRequest = function(){
				
				
				if (!$scope.isSubmit()){
					
					
					var data = {amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName };
					
					var res = $http.post('amicusthome/amidraftrequest',data);
					res.success(function(data, status, headers, config) {
						$scope.newRequest.id = data.id;
						$scope.newRequest.requestNumber = data.requestNumber;
					});
					res.error(function(data, status, headers, config) {
						alert( "failure message: " + JSON.stringify({data: data}));
					});	
					return;
				}
				
				if ($scope.newRequestForm.$valid) {
				
					var data = {amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName };
					
					var res = $http.post('amicusthome/amirequest',data);
					res.success(function(data, status, headers, config) {
						$scope.newRequest.id = data.id;
						$scope.newRequest.requestNumber = data.requestNumber;
					});
					res.error(function(data, status, headers, config) {
						alert( "failure message: " + JSON.stringify({data: data}));
					});	
				}else{
					
					alert('some errors found');
				}
				
			   
			}
			
			
			$scope.uploader = {
					fileAdded: function ($flow, $file, $message) {
						//console.log($flow, $file, $message); // Note, you have to JSON.parse message yourself.
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
		    
		    $scope.hoverIn = function(){
		    	
		    	var hasBeenSaved = Boolean( $scope.newRequest.id );
		    	if(hasBeenSaved){
		    		this.hoverEdit = false;
		    	}else{
		    		
		    		this.hoverEdit = true;
		    	}
		    	
		    };

		    $scope.hoverOut = function(){
		    	this.hoverEdit = false;
		    };
		    
		    $scope.hasBeenSaved = function(){
				var hasBeenSaved = Boolean( $scope.newRequest.id );
				return hasBeenSaved;
			}
		    
		    $scope.showIncompleteFormMsg = function(){
		    	this.isShowIncompleteFormMsg = true;
		    };
		    
		    $scope.dontShowIncompleteFormMsg = function(){
		    	this.isShowIncompleteFormMsg = false;
		    }

		});
		
		
		
		// ============ SearchRequest ===============
		app.controller('SearchRequestCtrl', function ($scope, $http, $window,$location) {
			$scope.page = 'searchRequests';
			$scope.searchType = 'pending';
			
			
			
		});
		// ============ SearchRequest ===============
		app.controller('EditRequestCtrl', function ($scope, $http, $window,$location, $routeParams, amiRequest) {
			$scope.page = 'EditRequest';
			
			var myAmiRequest = {};
			var amiRequest1 =	angular.fromJson(amiRequest);
			angular.copy(amiRequest1, myAmiRequest);
			
			$scope.requestNumber = $routeParams.requestNumber;
			//$scope.animalName = myAmiRequest.patientInfo.animalName;
			$scope.amiRequest = myAmiRequest.amiRequest;
			
			
			
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