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
						var hospitalId =  response.data.hospitalId;
						var masterUser =  response.data.masterUser;

						var amiUser = {
							getUserName: function(){
								return user;
							},
							getHospitalName: function(){
								return hospitalName;
							},
							getHospitalId: function(){
								return hospitalId;
							},
							getMasterUser: function(){
								return masterUser;
							}
						};
						
						deferred.resolve(amiUser);
					}, 
					function(response) {
							alert( response.data.message);
					}, 
					function(update) {
							
					});	
					
					
					return deferred.promise;
			}
			
		});
		
		
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($scope, $http, $window,$location, $modal, amiService,animals,species, amiServices,animalService, myAmiRequest) {
			
			$scope.page = 'newRequest';
			
			// this is a hack the files uploads should be in imagesAndDocuments
			var isUpdate = new Boolean(myAmiRequest.amiRequest);
			$scope.isEditable = true;
			
			if(isUpdate==true){
				$scope.newRequest = myAmiRequest.amiRequest;
				$scope.fileUploads = myAmiRequest.fileUploads;
				$scope.isEditable = new Boolean(myAmiRequest.editable);
			}else{
				$scope.newRequest = myAmiRequest;
				$scope.fileUploads = {};
			}
		
			$scope.saveAction = '';
			
			var Years  = 'Years';
			var Months = 'Months';
			var ZeroYears = '0 year';
			var ZeroMonths = '0 months';
			
			// ----- End of Set the species -----
			for (var i in animals) {
			
			  var anAnimal = animals[i];
			  var aSpecies = anAnimal.name;
			  
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
			// ----- End of Set the species -----
			
			
			// ----- Set the breeds -----
			var speciesValue        = $scope.newRequest.patientInfo.species;
			
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
			// ----- End of Set the breeds -----
			
			
			var promise = amiService.getAmiUser();
			
			promise.then(function(amiUser) {
				$scope.userName     = amiUser.getUserName();
				$scope.hospitalName = amiUser.getHospitalName();
				$scope.hospitalId = amiUser.getHospitalId();
			}, 
			function(response) {
			  alert( response.data.message);
			}, 
			function(update) {
				alert( response.data.message);
			});
			
			$scope.animalWeightUomList  = ['LB', 'KG'];
			$scope.labsList 			= ['Select Lab','PCL', 'IDEXX', 'Antech'];
//			$scope.breedsList 			= ['Select Breed'];
//			$scope.animalSexList		= ['Sex','F/s', 'M/c', 'F','M'];
			$scope.animalSexList		= ['Sex','F/s', 'M/c', 'F','M'];
			$scope.animalAgeYearsList 	= [Years ];
			$scope.animalAgeMonthsList =  [Months];
			$scope.serviceCategory = 'Imaging Modalities';
			
			
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
					}else{
						
					}
			    }
			}
			
			$scope.isImageModalityNotSelected = function(){ 
				
				if( $scope.serviceCategory == 'Imaging Modalities'){
					return false;
				}
				
				return true;
			}
			
			$scope.servicesList = {
				'Contrast Radiography'   :contrastRadioGraphy,
				'Computed Tomography'    :computedTomography,
				'Radiography/Fluoroscopy':radiographyFluoroscopy,
				'Ultrasound'             :ultrasound
			};
			
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
				}else{
					
					// no service category picked,when showing 'Imaging Modalities'
					$scope.servicesListByCategory= [];
				}
				
			}
			//$scope.disableBreedList = true;
			$scope.updateBreedListBox = function(){
				var speciesValue        = $scope.newRequest.patientInfo.species;
				
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
				var yearIsEmpty   =   new String(yearValue).indexOf("year") == -1;
				
				var monthValue     = $scope.newRequest.patientInfo.animalAgeMonths;
				var monthIsEmpty   =   monthValue.indexOf("month") == -1;
				
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
					$scope.newRequest.hospitalAndClientInfo.isEmployee	  = true;
				}else{
					$scope.newRequest.hospitalAndClientInfo.isEmployee	  = false;
				}
				
			}
			
			$scope.isInterpretationOnly =function(isInterpretationOnly){
				if(isInterpretationOnly){
					$scope.newRequest.requestedServices.isInterpretationOnly	  = true;
					$scope.newRequest.requestedServices.selectedServices = [];
				}else{
					$scope.newRequest.requestedServices.isInterpretationOnly	  = false;
				}
				
			}
			
			$scope.selectThisService = function(aServiceType, aService){
				
				
				var serviceToAdd = aServiceType +" - "+aService.name;
				
				var serviceAlredayAdded = false;
				for(var i = 0; i < $scope.newRequest.requestedServices.selectedServices.length; i++) {
				    if ($scope.newRequest.requestedServices.selectedServices[i] == serviceToAdd) {
				    	serviceAlredayAdded = true;
				    	return;
				    }
				}
				
				$scope.newRequest.requestedServices.selectedServices.push(serviceToAdd);
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
			$scope.isSaveAsDraft= function(){
				if($scope.saveAction == 'draft'){
					return true;
				}else{
					return false;
				}
				
			}
			
			$scope.saveNewRequest = function(){
				
				
				if ($scope.isSaveAsDraft()){
					
					$scope.saveAction == '';
						
					var data = {amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName, hospitalId:$scope.hospitalId };
					
					var res = $http.post('amicusthome/amidraftrequest',data);
					res.success(function(data, status, headers, config) {
//					$scope.newRequest.id = data.id;
						$scope.newRequest.requestNumber = data.requestNumber;
						//$location.path('/searchRequest');
					});
					res.error(function(data, status, headers, config) {
						alert( "failure message: " + JSON.stringify({data: data}));
					});	
					return;
				}
				
				if ($scope.isSubmit()){
					$scope.saveAction == '';
					
					if ($scope.newRequestForm.$valid) {
					
						var data = {amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName, hospitalId:$scope.hospitalId };
						
						var res = $http.post('amicusthome/amirequest',data);
						res.success(function(data, status, headers, config) {
							$scope.newRequest.requestNumber = data.requestNumber;
							$location.path('/searchRequest');
						});
						res.error(function(data, status, headers, config) {
							alert( "failure message: " + JSON.stringify({data: data}));
						});	
						
					}else{
						
						$scope.saveAction == '';
						alert('some errors found');
					}
				}	
			   
			}
			
			
			//var myModal = $modal({controller: this, templateUrl: './processmodal.html', show: false});
			var myModal = $modal({title: '', content: 'Please wait...', show: false});
			
			$scope.uploader = {
					
					fileAdded: function ($flow, $file, $message) {
					    $file.msg = $message;// Just display message for a convenience
					  },
				
					fileSubmitted: function ($flow, $file, $message) {
						 
						var reqNumber = $scope.newRequest.requestNumber;
						$flow.opts.query = { requestNumber: reqNumber };
					    //$file.msg = $message;// Just display message for a convenience

					},
					
					fileCompleted: function ($flow, $file, $message) {
					    
					    var myRequestNumber = $scope.newRequest.requestNumber;
					    var promise = animalService.getUploadedFiles(myRequestNumber);
						
						promise.then(function(result) {
							$scope.fileUploads  = result.data;
							$scope.$apply();
						}, 
						function(response) {
						  alert( response.data.message);
						}, 
						function(update) {
							alert( response.data.message);
						});
						
						myModal.$promise.then(myModal.hide);
					    
					},
					 fileUploadStarted: function ($flow, $file, $message) {
						 myModal.$promise.then(myModal.show);
//						$file.msg = $message;// Just display message for a convenience
					}
						
				};
				
		    $scope.upload = function () {
		    
		      $scope.uploader.flow.upload(); 
		    }
		    
		    
		    $scope.deleteUploadedFile = function (fileName) {
		    	
		    	var data = {requestNumber: $scope.newRequest.requestNumber , fileName: fileName};
				
				var res = $http.post('/ami/upload/delete',data).then(function(response) {
					
					$scope.fileUploads = response.data;
				},
				function(response) {
					var uploadedFileRes =  response.data.uploadedFiles;
					alert('finaly done ' + uploadedFileRes);
				});
				
		    }
		    
		    $scope.hasBeenSaved = function(){
				var hasBeenSaved = Boolean( $scope.newRequest.requestNumber );
				return hasBeenSaved;
			}
		    
		    $scope.showTransferFileMsg = function(){
				var hasBeenSaved = Boolean( $scope.newRequest.requestNumber );
				var uploadSelected = $scope.newRequest.imagesAndDocuments.hasDocumentDeliveredByUpload
				return !hasBeenSaved && uploadSelected;
			}
		    
		    
		    
		    $scope.showIncompleteFormMsg = function(){
		    	this.isShowIncompleteFormMsg = true;
		    };
		    
		    $scope.dontShowIncompleteFormMsg = function(){
		    	this.isShowIncompleteFormMsg = false;
		    }

		});
		
		
		
		// ============ SearchRequest ===============
		app.controller('SearchRequestCtrl', function ($scope, $http, $window,$location, pendingRequests, draftRequests, amiRequestFactory) {
			$scope.page = 'searchRequests';
			$scope.searchType = 'all';
			$scope.pendingRequests = pendingRequests;
			$scope.draftRequests = draftRequests;
			
			$scope.searchBy = 'Search By';
			$scope.searchByPlaceHolder1 = 'Enter Criteria';
			$scope.searchByPlaceHolder2 = '';
			$scope.searchByInput1Visible = true;
			$scope.searchByInput2Visible = false;
			$scope.isSearchAllowed = false;
			$scope.searchResults = [];
			
			$scope.isSearchBySearchBy = false;
			$scope.isSearchByRequestNumber = false;
			$scope.isSearchByAnimalName = false;
			
			
			$scope.updateSearchInputDisplay =  function(){
				
				if($scope.searchBy.indexOf("Date") !=-1){
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.searchByInput1Visible = true;
					$scope.searchByInput2Visible = true;
					$scope.isSearchAllowed = true;
					$scope.searchByPlaceHolder1 = 'Beg Date mm/dd/yy';
					$scope.searchByPlaceHolder2 = 'End Date mm/dd/yy';
				}
				else if($scope.searchBy.indexOf("Last 50") !=-1){
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.isSearchAllowed = true;
					$scope.searchByInput1Visible = false;
					$scope.searchByInput2Visible = false;
					$scope.searchByPlaceHolder1 = 'Select a Search';
					$scope.searchByPlaceHolder2 = '';
				}
				else if($scope.searchBy.indexOf("Search By") !=-1){
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.isSearchAllowed = false;
					$scope.searchByInput1Visible = true;
					$scope.searchByInput2Visible = false;
					$scope.searchByPlaceHolder1 = 'Select a Search';
					$scope.searchByPlaceHolder2 = '';
				}
				else{
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.isSearchAllowed = true;
					$scope.searchByInput1Visible = true;
					$scope.searchByInput2Visible = false;
					$scope.searchByPlaceHolder1 = 'Enter Criteria';
					$scope.searchByPlaceHolder2 = '';
				}
			}
			
			
			$scope.doSearch =  function(){
				$scope.searchResults = [];
				var requestNumber = $scope.searchByInput1;
				var date2 = $scope.searchByInput2;
				
				$scope.whichSearch();
				
				if( $scope.isSearchByRequestNumber ){
					amiRequestFactory.getAmiRequest(requestNumber).then(
	             			function(result){
	             				var myResult = result.data;
	             				$scope.searchResults.push(myResult);
	             			}	
	             	);
				}
				else if( $scope.isSearchByAnimalName ){
					
					amiRequestFactory.getAmiRequestByAnimalName(requestNumber).then(
							function(result){
								$scope.searchResults= result.data;
							}	
					);
				}
				else if($scope.isSearchByClientLastName){
					
					amiRequestFactory.getAmiRequestByClientLastName(requestNumber).then(
							function(result){
								$scope.searchResults= result.data;
							}	
					);
				}
				else if($scope.isSearchByRequestSubmittedDate){
					
					amiRequestFactory.getAmiRequestBySubmittedDateRange(requestNumber,date2).then(
							function(result){
								$scope.searchResults= result.data;
							}	
					);
				}
				else if($scope.isSearchByLast50Requests){
					
					amiRequestFactory.getAmiRequestByLast50Records().then(
							function(result){
								$scope.searchResults= result.data;
							}	
					);
				}
			}
			
			$scope.whichSearch = function(){
				
				$scope.isSearchBySearchBy = false;
				$scope.isSearchByRequestNumber = false;
				$scope.isSearchByAnimalName = false;
				$scope.isSearchByRequestSubmittedDate = false;
				$scope.isSearchByClientLastName = false;
				$scope.isSearchByLast50Requests = false;
				
				if($scope.searchBy.indexOf("Search By") !=-1){
					$scope.isSearchBySearchBy = true;
				}
				else if($scope.searchBy.indexOf("Request Number") !=-1){
					$scope.isSearchByRequestNumber = true;
				}
				else if($scope.searchBy.indexOf("Animal Name") !=-1){
					$scope.isSearchByAnimalName = true;
				}
				else if($scope.searchBy.indexOf("Request Submitted Date") !=-1){
					$scope.isSearchByRequestSubmittedDate = true;
				}
				
				else if($scope.searchBy.indexOf("Client Last Name") !=-1){
					$scope.isSearchByClientLastName = true;
				}
				else if($scope.searchBy.indexOf("Last 50 Request") !=-1){
					$scope.isSearchByLast50Requests = true;
				}
			}
			
				
			
		});
		// ============ SearchRequest ===============
		app.controller('EditRequestCtrl', function ($scope, $http, $window,$location, $routeParams, amiRequest) {

// Not used anymore, as the Edit is handled by NewRequestCtrl. Same as create... as long as request is draft
			
//			$scope.page = 'EditRequest';
//			
//			var myAmiRequest = {};
//			var amiRequest1 =	angular.fromJson(amiRequest);
//			angular.copy(amiRequest1, myAmiRequest);
//			
//			$scope.requestNumber = $routeParams.requestNumber;
//			//$scope.animalName = myAmiRequest.patientInfo.animalName;
//			$scope.amiRequest = myAmiRequest.amiRequest;
			
			
			
		});
		
		// ============ Profile ===============
		app.controller('ProfileCtrl', function ($scope, $window,$location, myHospital, amiService) {
			$scope.page = 'profile';
			
			var promise = amiService.getAmiUser();
			
			promise.then(function(amiUser) {
				$scope.userName     = amiUser.getUserName();
				$scope.hospitalName = amiUser.getHospitalName();
				$scope.hospitalId = amiUser.getHospitalId();
				$scope.masterUser = amiUser.getMasterUser();
			}, 
			function(response) {
			  alert( response.data.message);
			}, 
			function(update) {
				alert( response.data.message);
			});
			
			$scope.hospital = myHospital;

		});
		
		// ============ Help ===============
		app.controller('NewUserCtrl', function ($scope, $window,$location) {
			$scope.page = 'New User';
			
			var newUser ={
				'userName'  : '',
				'pwd'       : '',
				'firstName' : '',
				'lastName'  : '',
				'occupation': '',
				'isVet'     : false
			};
			
			$scope.newUser = newUser;
			
			$scope.isUserAVet =function(isUserAVet){
				if(isUserAVet){
					$scope.newUser.isVet = true;
				}else{
					$scope.newUser.isVet = false;
				}
				
			}
			
			
			

		});
		
		
		// ============ Help ===============
		app.controller('HelpCtrl', function ($scope, $window,$location) {
			$scope.page = 'help';

		});
})();	