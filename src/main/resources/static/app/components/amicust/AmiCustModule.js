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
						var userEmail =  response.data.email;

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
							},
							getUserEmail: function(){
								return userEmail;
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
		
		
		app.controller('EditUserCtrl', function ($scope, $http, $window,$location, myAmiUser) {
				
			$scope.myAmiUser = myAmiUser;
			$scope.userUpdateValue = "";
			$scope.userUpdateAction ="";
			$scope.bluredPwd = "**********";
			
			var userUpdateType = {
					pwd:		{'myPwd': 'updatePwd'   },
					email:		{'myEmail': 'updateEmail' },
					firstName:	{'myFirstName': 'updateFirstName' },
					lastName:	{'myLastName': 'updateLastName' },
					occupation: {'myOccupation': 'updateOccupation'}
				}
			
			$scope.updateUserEmail = function(){
				$scope.modalLabel = "Update Email";
				$scope.modalCurrentLabel = "Current Email";
				$scope.modalCurrentValue = $scope.myAmiUser.email;
				$scope.modalNewValuePlaceHolder = "New Email";
				$scope.userUpdateAction = userUpdateType.email.myEmail;
			}
			
			$scope.updateUserPwd = function(){
				$scope.modalLabel = "Update Password";
				$scope.modalCurrentLabel = "Current Password";
				$scope.modalCurrentValue = $scope.bluredPwd;
				$scope.modalNewValuePlaceHolder = "New Password";
				$scope.userUpdateAction = userUpdateType.pwd.myPwd;
			}
			$scope.updateUserFirstName = function(){
				$scope.modalLabel = "Update First Name";
				$scope.modalCurrentLabel = "Current First Name";
				$scope.modalCurrentValue = $scope.myAmiUser.firstName;
				$scope.modalNewValuePlaceHolder = "New First Name";
				$scope.userUpdateAction = userUpdateType.firstName.myFirstName;
			}
			
			$scope.updateUserLastName = function(){
				$scope.modalLabel = "Update Last Name";
				$scope.modalCurrentLabel = "Current Last Name";
				$scope.modalCurrentValue = $scope.myAmiUser.lastName;
				$scope.modalNewValuePlaceHolder = "New Last Name";
				$scope.userUpdateAction = userUpdateType.lastName.myLastName;
			}
			
			$scope.updateUserOccupation = function(){
				$scope.modalLabel = "Update Occupation";
				$scope.modalCurrentLabel = "Current Occupation";
				$scope.modalCurrentValue = $scope.myAmiUser.occupation;
				$scope.modalNewValuePlaceHolder = "New Occupation";
				$scope.userUpdateAction = userUpdateType.occupation.myOccupation;
			}
			
			$scope.isEditUserSaveBtnEnabled = function(){
				
				if ( $scope.userUpdateAction == userUpdateType.pwd.myPwd ){
					$scope.showEmptyFieldHintInModal = false;
					if( $scope.userUpdateValue.length <5 ){
						 $scope.showPwdHintInModal = true;
						 return true;
					}
					$scope.showPwdHintInModal = false;
					return false;
				}
				
				$scope.showPwdHintInModal = false;
				if( $scope.userUpdateValue.length <2 ){
					 $scope.showEmptyFieldHintInModal = true;
					 return true;
				}
				$scope.showEmptyFieldHintInModal = false;
				return false;
			}
			
			$scope.cancelUserUpdateModal = function(){
				clearModal();
			}
			
			var clearModal = function(){
				$scope.modalLabel = "";
				$scope.modalCurrentLabel = "";
				$scope.modalCurrentValue = "";
				$scope.modalNewValuePlaceHolder = "";
				$scope.userUpdateAction ="";
				$scope.userUpdateValue = "";
				
				
				$scope.newHospitalUpdatePlaceHolderValue = "";
				$scope.hospitalUpdateAction = "";
				$scope.newHospitalUpdateValue= "";
				$scope.hospitalStringCurrentValue ="";
			}
			
			
			$scope.updateUser = function(){
				
				var url = '/ami/amicusthome/hospital/edituser';
				var data = {userName: $scope.myAmiUser.user, hospitalId: $scope.myAmiUser.hospitalId, value: $scope.userUpdateValue, 'action': $scope.userUpdateAction};
				var res = $http.post(url,data);
				
				//alert('$scope.userUpdateAction: ' + $scope.userUpdateAction);
				
				res.success(function(data, status, headers, config) {
					
					if( $scope.userUpdateAction == userUpdateType.pwd.myPwd){
						$scope.myAmiUser.pwd = $scope.userUpdateValue;
						//alert(' doing pwd' );
					}
					else if($scope.userUpdateAction == userUpdateType.email.myEmail){
						$scope.myAmiUser.email = $scope.userUpdateValue;
						//alert(' doing email' );
					}
					else if($scope.userUpdateAction == userUpdateType.firstName.myFirstName){
						$scope.myAmiUser.firstName = $scope.userUpdateValue;
						//alert(' doing first name' );
					}
					else if($scope.userUpdateAction == userUpdateType.lastName.myLastName){
						$scope.myAmiUser.lastName = $scope.userUpdateValue;
						//alert(' doing last name' );
					}
					else if($scope.userUpdateAction == userUpdateType.occupation.myOccupation){
						$scope.myAmiUser.occupation = $scope.userUpdateValue;
						//alert(' doing occupation' );
					}
					
					clearModal();
					
				});
				res.error(function(data, status, headers, config) {
					alert( $scope.userUpdateAction + " failed: " + JSON.stringify({data: data}));
				});	
				
			}
					
		});
		// ============ Controller ===============
		app.controller('CasePendingCtrl', function ($scope, $http, $window,$location, myCase) {
			
			$scope.page = 'searchRequests';
			$scope.amiCase = myCase;
			$scope.amiRequest = myCase.amiRequest;
			 
			
			$scope.addAmendment = function(newAmendment){ 
				
				var data = { caseNumber: $scope.amiCase.caseNumber, newAmendment: newAmendment};
				
				var res = $http.post('/ami/amiadmin/addAmendmentCust',data).then(
					function(response) {
						$scope.amiCase.amendments = response.data;
					},
					function(response) {
						alert('error capturing the amendment' );
					});
			}
		});
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($route, $scope, $http, $window,$location, $modal, amiService, amiServices,animalService, myAmiRequest, myHospital,animals ,speciesList) {
			
			$scope.page = 'newRequest';
			
		
			$scope.caseNumber =  $route.current.params.requestNumber;
			
			// this is a hack the files uploads should be in imagesAndDocuments
//			var isUpdate = new Boolean(myAmiRequest.amiRequest);
			var isUpdate = new Boolean($scope.caseNumber);
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
			
			var Years  = '';
			var Months = '';
			var ZeroYears = '0 year';
			var ZeroMonths = '0 months';
			
			
			
			$scope.breedsList = [];
			
			$scope.speciesBreedMap = {};
			for (var i in animals) {
				  var species = animals[i].name;
				  var myBreeds = animals[i].breeds;
				  $scope.speciesBreedMap[species.toLowerCase()] =  myBreeds;
				  
			}
			
			$scope.updateBreedList = function(){
				
				$scope.breedsList = [];
				var speciesValue = $scope.newRequest.patientInfo.species.trim();
			
				var foundMatch = false;
				for (var i in animals) {
					  var species = animals[i].name;
					  foundMatch = new String(speciesValue).toLowerCase().indexOf(species.toLowerCase()) > -1;
					  
					  if(foundMatch){
						  $scope.breedsList =  $scope.speciesBreedMap[speciesValue.toLowerCase()];
						  break;
					  }
				}
				
				
				
			}
			
			$scope.hospitalEmails = myHospital.hospital.emails;
			$scope.contract= myHospital.hospital.contract;
			$scope.accountSize=myHospital.hospital.accountSize;
			
			var dupeEmails = [];
			for(var i = 0; i < $scope.hospitalEmails.length; i++) {
				if(dupeEmails.indexOf($scope.hospitalEmails[i].value) > -1){
					 $scope.hospitalEmails.splice(i,1);
				}else{
					dupeEmails.push($scope.hospitalEmails[i].value);
				}
			}
			
			$scope.userEmailIsHospitalEmail = false;
			
			var promise = amiService.getAmiUser();
			
			promise.then(function(amiUser) {
				$scope.userName     = amiUser.getUserName();
				$scope.hospitalName = amiUser.getHospitalName();
				$scope.hospitalId = amiUser.getHospitalId();
				$scope.userEmail =  amiUser.getUserEmail();
				
				
				for(var i = 0; i < $scope.hospitalEmails.length; i++) {
				    if ($scope.hospitalEmails[i] == $scope.userEmail) {
				    	$scope.userEmailIsHospitalEmail = true;
				    	return;
				    }
				}
				
			}, 
			function(response) {
			  alert( response.data.message);
			}, 
			function(update) {
				alert( response.data.message);
			});
			
			$scope.speciesList = speciesList;
			$scope.animalWeightUomList  = ['LB', 'KG'];
			$scope.labsList 			= ['Select Lab','PCL', 'IDEXX', 'Antech'];
			$scope.animalSexList		= ['Sex','Female spayed', 'Male castrated', 'Female','Male'];
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
			
			
			$scope.sendRequestToPersonalEmail =function(isSendRequestToPersonalEmail){
				if(isSendRequestToPersonalEmail){
					$scope.newRequest.hospitalAndClientInfo.isRequestSentToPersonalEmail = true;
				}else{
					$scope.newRequest.hospitalAndClientInfo.isRequestSentToPersonalEmail = false;
				}
				
			}
			$scope.isEmployee =function(isEmployee){
				if(isEmployee){
					$scope.newRequest.hospitalAndClientInfo.isEmployee	  = true;
				}else{
					$scope.newRequest.hospitalAndClientInfo.isEmployee	  = false;
				}
				
			}
			$scope.isOrganization =function(isOrganization){
				if(isOrganization){
					$scope.newRequest.hospitalAndClientInfo.isOrganization	  = true;
				}else{
					$scope.newRequest.hospitalAndClientInfo.isOrganization	  = false;
				}
				
			}
			
			
			$scope.isInterpretationOnly =function(isInterpretationOnly){
				
				if(isInterpretationOnly){
					$scope.newRequest.requestedServices.isInterpretationOnly	  = true;
					$scope.newRequest.requestedServices.selectedServices = [];
					$scope.serviceCategory = 'Imaging Modalities';
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
				if($scope.isSubmit()){
					$scope.submitted=true;
				}else{
					$scope.submitted=false;
				}
				
				
			}
			
			$scope.isSubmit= function(){
				if($scope.saveAction == 'submit'){
					return true;
				}else{
					$scope.submitted=false;
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
			
			$scope.saveForUpload= function(){
				
				var data = {caseNumber: $scope.caseNumber, amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName, hospitalId:$scope.hospitalId, contract: $scope.contract, accountSize:$scope.accountSize };
				
				var res = $http.post('amicusthome/amidraftrequest',data);
				res.success(function(data, status, headers, config) {
					$scope.caseNumber = data.caseNumber;
				});
				res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});	
				
			}
			
			
			$scope.deleteDraftCase = function(){
				
				var data = {caseNumber: $scope.caseNumber };
				
				var res = $http.post('amicusthome/deleteamidraftrequest',data);
				res.success(function(data, status, headers, config) {
					$scope.caseNumber = data.caseNumber;
					$location.path('/searchRequest/draftRequests/all');
				});
				res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});	
				
				return;
			}
			
			$scope.saveNewRequest = function(){
				
				
				if ($scope.isSaveAsDraft()){
					
					$scope.saveAction == '';
						
					var data = {caseNumber: $scope.caseNumber, amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName, hospitalId:$scope.hospitalId, contract: $scope.contract, accountSize:$scope.accountSize };
					
					var res = $http.post('amicusthome/amidraftrequest',data);
					res.success(function(data, status, headers, config) {
						$scope.caseNumber = data.caseNumber;
						$location.path('/searchRequest/draftRequests/'+data.caseNumber);
					});
					res.error(function(data, status, headers, config) {
						alert( "failure message: " + JSON.stringify({data: data}));
					});	
					
					return;
					
				}
				
				if ($scope.isSubmit()){
					$scope.saveAction == '';
					
					if ($scope.newRequestForm.$valid) {
					
						var data = {caseNumber: $scope.caseNumber,amiRequest: $scope.newRequest, userName: $scope.userName, hospitalName: $scope.hospitalName, hospitalId:$scope.hospitalId, contract: $scope.contract, accountSize:$scope.accountSize };
						
						var res = $http.post('amicusthome/amirequest',data);
						res.success(function(data, status, headers, config) {
//							$scope.newRequest.requestNumber = data.requestNumber;
							$scope.caseNumber = data.caseNumber;
							$location.path('/searchRequest/pendingRequests/'+data.caseNumber);
						});
						res.error(function(data, status, headers, config) {
							alert( "failure message: " + JSON.stringify({data: data}));
							
						});	
						
					}else{
						// here the form had errors don't do anything
						$scope.saveAction == '';
//						alert('some errors found');
					}
				}	
			   
			}
			
			$scope.hasAnimalSexChanged = false;
			$scope.animalSexChanged = function(){
				$scope.hasAnimalSexChanged = true;
			}
			$scope.requiredFieldEntered = function(){
				
				$scope.requiredFiedlForUploadMsg = "";
				
				if(!$scope.newRequest.hospitalAndClientInfo.vet || $scope.newRequest.hospitalAndClientInfo.vet.length<1){
					$scope.requiredFiedlForUploadMsg += "[Veterinarian]";
				}
				
				if(!$scope.newRequest.hospitalAndClientInfo.clientLastName || $scope.newRequest.hospitalAndClientInfo.clientLastName.length<1){
					$scope.requiredFiedlForUploadMsg += "[Client Last Name or Organization]";
				}
				
				if($scope.requiredFiedlForUploadMsg.length==0){
					return true;
				}
				
				$scope.requiredFiedlForUploadMsg ="You must fill out the following "+$scope.requiredFiedlForUploadMsg+" before you can upload images." ;
				return false;
			}
			
			$scope.validateUploadCheckBox = function(){
				$scope.cantUncheckUploadCheckBoxMsg ='';
				var checkedOffBox =  $scope.newRequest.imagesAndDocuments.hasDocumentDeliveredByUpload;
				
				if(!checkedOffBox){
					
					if($scope.fileUploads.length > 0){
						$scope.newRequest.imagesAndDocuments.hasDocumentDeliveredByUpload = true;
						$scope.cantUncheckUploadCheckBoxMsg ='You must first remove all uploaded files before you can uncheck this checkbox.';
					}
				}
			}
			
			var myModal = $modal({title: '', content: 'Please wait...', show: false});
			
			$scope.uploader = {
					
					fileAdded: function ($flow, $file, $message) {
					    $file.msg = $message;// Just display message for a convenience
					  },
				
					fileSubmitted: function ($flow, $file, $message) {
						 
						//var reqNumber = $scope.newRequest.requestNumber;
						var reqNumber = $scope.caseNumber;
						$flow.opts.query = { requestNumber: reqNumber };
					    //$file.msg = $message;// Just display message for a convenience

					},
					
					fileCompleted: function ($flow, $file, $message) {
						$flow.files = [];
					    
//					    var myRequestNumber = $scope.newRequest.requestNumber;
//					    var myRequestNumber = $scope.caseNumber;
					    var promise = animalService.getUploadedFiles($scope.caseNumber);
						
						promise.then(function(result) {
							$scope.fileUploads  = result.data;
							//$scope.$apply();
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
		    	
		    	var data = {requestNumber: $scope.caseNumber , fileName: fileName};
				
				var res = $http.post('/ami/upload/delete',data).then(function(response) {
					
					$scope.fileUploads = response.data;
					
					// if there are no more files uploaded, blank out the tool tip message
					if($scope.fileUploads!= null && $scope.fileUploads.length <1){
						$scope.cantUncheckUploadCheckBoxMsg ='';
					}
					
				},
				function(response) {
					var uploadedFileRes =  response.data.uploadedFiles;
//					alert('finaly done ' + uploadedFileRes);
				});
				
		    }
		    
		    $scope.hasBeenSaved = function(){
//				var hasBeenSaved = Boolean( $scope.newRequest.requestNumber );
				var hasBeenSaved = Boolean( $scope.caseNumber );
				return hasBeenSaved;
			}
		    
		    $scope.showTransferFileMsg = function(){
				//var hasBeenSaved = Boolean( $scope.newRequest.requestNumber );
				var hasBeenSaved = Boolean( $scope.caseNumber );
				var uploadSelected = $scope.newRequest.imagesAndDocuments.hasDocumentDeliveredByUpload
				return !hasBeenSaved && uploadSelected;
			}
		    
		});
		
		
		
		// ============ SearchRequest ===============
		app.controller('SearchRequestCtrl', function ($scope, $http, $window,$location, pendingRequests, draftRequests, amiRequestFactory, searchTypeAndFilter) {
			
			$scope.searchFilter = '';
			$scope.draftSarchFilter ='';
			$scope.pendgingSarchFilter ='';
			$scope.searchType = searchTypeAndFilter.searchType;
			
			var caseNumberToFilter = searchTypeAndFilter.caseNumber;
			if(caseNumberToFilter!= 'all'){
				if($scope.searchType=='draftRequests'){
					$scope.draftSarchFilter = caseNumberToFilter;
				}else if($scope.searchType=='pendingRequests'){
					$scope.pendgingSarchFilter = caseNumberToFilter;
				}
			}
			
			
			$scope.page = 'searchRequests';
			
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
			$scope.isDateSearch = false;
			$scope.searchClicked = false;
			
			
			
			
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

			$scope.updateSearchInputDisplay =  function(){
				$scope.searchHintMsg="";
				$scope.searchClicked = false;
				$scope.whichSearch();
				
				if($scope.searchBy.indexOf("Date") !=-1){
					$scope.isDateSearch = true;
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.searchByInput1Visible = true;
					$scope.searchByInput2Visible = true;
					$scope.isSearchAllowed = true;
					$scope.searchByPlaceHolder1 = 'Beg Date mm/dd/yyyy';
					$scope.searchByPlaceHolder2 = 'End Date mm/dd/yyyy';
				}
				else if($scope.searchBy.indexOf("Last 50") !=-1){
					$scope.isDateSearch = false;
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.isSearchAllowed = true;
					$scope.searchByInput1Visible = false;
					$scope.searchByInput2Visible = false;
					$scope.searchByPlaceHolder1 = 'Select a Search';
					$scope.searchByPlaceHolder2 = '';
				}
				else if($scope.searchBy.indexOf("Search By") !=-1){
					$scope.isDateSearch = false;
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.isSearchAllowed = false;
					$scope.searchByInput1Visible = true;
					$scope.searchByInput2Visible = false;
					$scope.searchByPlaceHolder1 = 'Select a Search';
					$scope.searchByPlaceHolder2 = '';
				}
				else{
					$scope.isDateSearch = false;
					$scope.searchByInput1 = '';
					$scope.searchByInput2 = '';
					$scope.isSearchAllowed = true;
					$scope.searchByInput1Visible = true;
					$scope.searchByInput2Visible = false;
					$scope.searchByPlaceHolder1 = 'Enter Criteria';
					$scope.searchByPlaceHolder2 = '';
				}
			}
			
			
			$scope.searchHintMsg = "";
			$scope.doSearch =  function(){
				$scope.searchClicked = true;
				$scope.searchHintMsg = "";
				
				$scope.searchResults = [];
				var requestNumber = $scope.searchByInput1;
				var date2 = $scope.searchByInput2;
				
				$scope.whichSearch();
				
				if( $scope.isSearchByRequestNumber ){
					amiRequestFactory.getAmiRequest(requestNumber).then(
	             			function(result){
	             				var myResult = result.data;
	             				if(myResult){
	             					$scope.searchResults.push(myResult);
	             				}else{
	             					$scope.searchHintMsg = "No results found";
	             				}
	             			}	
	             	);
				}
				else if( $scope.isSearchByAnimalName ){
					
					amiRequestFactory.getAmiRequestByAnimalName(requestNumber).then(
							function(result){
								var myResult = result.data;
	             				if(myResult && myResult.length>0){
	             					$scope.searchResults= myResult;
	             				}else{
	             					$scope.searchHintMsg = "No results found.";
	             				}
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
		app.controller('NewUserCtrl', function ($scope, $window,$location,$http, newUserFactory) {
			$scope.page = 'New User';
			$scope.submitted = false;
			$scope.newUser = newUserFactory.getNewUser();
			$scope.occupationOtherVisible =false;
			$scope.confirmPwd = '';
			
			$scope.cancel = function(){
				$location.path('/profile');
			}
			
			
			$scope.saveNewUser = function(){
				
				
				if ($scope.newUserForm.$valid) {
					
					var data = {newUser: $scope.newUser};
					
					var res = $http.post('/ami/amicusthome/newuser',data);
					res.success(function(data, status, headers, config) {
						$location.path('/profile');
					});
					res.error(function(data, status, headers, config) {
						alert( "failure message: " + JSON.stringify({data: data}));
					});	
					
				}else{
					
					console.log("========> found error in saving new users");
				}
			}
			
			$scope.updateOccupationOtherVisible =  function(){
				$scope.newUser.occupationOther = '';
				if($scope.newUser.occupation.indexOf("Other") !=-1){
					$scope.occupationOtherVisible = true;
				}else{
					$scope.occupationOtherVisible = false;
				}
			}
			
			$scope.doesntMatchPwd = function(){
				
				console.log("pwd        " + $scope.newUser.pwd );
				console.log("confirmPwd " + $scope.confirmPwd );
				if($scope.newUser.pwd  == $scope.confirmPwd ){
					console.log(" match" );
					return false;
				}else{
					console.log("doesn't match" );
					return true;
				}
			}
			
			
			

		});
		
		
		// ============ Help ===============
		app.controller('HelpCtrl', function ($scope, $window,$location) {
			$scope.page = 'help';

		});
})();	