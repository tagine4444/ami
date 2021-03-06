(function(){
	
	var amiAdminModule = angular.module('AmiAdminModule',[]);
	
	// ============ Controller ===============
	amiAdminModule.controller('CaseQueueCtrl', function ($scope, $http, $window,$location, pendingRequestsAllHospitals, amendedCases) {
		$scope.page = 'readCases';
		
		$scope.searchType = 'newCasesStat';
		if(pendingRequestsAllHospitals.statsAmiRequests.length ==0){
			$scope.searchType = 'newCases';
		}else{
			$scope.searchType = 'newCasesStat';
		}
		
		$scope.anAmendedCases = amendedCases;
		$scope.newCases = pendingRequestsAllHospitals.amiRequests;
		$scope.statsAmiRequests = pendingRequestsAllHospitals.statsAmiRequests;
//		$scope.caseReadyForReview = [];
		$scope.caseCompletedToday = [];
		
	});
	
		// ============ Controller ===============
		amiAdminModule.controller('AccountingCtrl', function ($scope, $http, $window,$location, casesPendingAccounting) {
			$scope.page = 'accounting';
			
			$scope.casesPendingAccounting = casesPendingAccounting;
			
			$scope.selectCaseToSee = function(amiCase){
				
				$scope.amiCase = amiCase;
				$scope.amiRequest = amiCase.amiRequest;
				
			}
			
			$scope.doAccounting = function(amiCase){
				
				
				var data = {caseNumber:amiCase.caseNumber };
				
				var res = $http.post('/ami/amiadmin/doaccounting',data).then(function successCallback(result) {
					$scope.casesPendingAccounting  = result.data;
				  }, function errorCallback(response) {
					  alert( "AccountingDone, not updated, failure message: " + JSON.stringify({data: data}));
				  });
				
			}
			
			$scope.addAmendment = function(newAmendment){ 
				
				var data = { caseNumber: $scope.amiCase.caseNumber, newAmendment: newAmendment};
				
				var res = $http.post('/ami/amiadmin/addAmendmentAdmin',data).then(
					function(response) {
						$scope.amiCase.amendments = response.data;
					},
					function(response) {
						alert('error capturing the amendment' );
					});
			}
		});
	
	
		// ============ Controller ===============
		amiAdminModule.controller('CaseReviewQueueCtrl', function ($scope, $http, $window,$location, casesPendingReview) {
			$scope.page = 'caseReview';
			
		$scope.searchType = 'newCasesStat';
		if(casesPendingReview.statsAmiRequests.length ==0){
			$scope.searchType = 'newCases';
		}else{
			$scope.searchType = 'newCasesStat';
		}
		
		$scope.newCases = casesPendingReview.amiRequests;
		$scope.statsAmiRequests = casesPendingReview.statsAmiRequests;
		
		
	
		
	});
	
	// ============ Controller ===============
	amiAdminModule.controller('CaseSearchCtrl', function ($scope, $http, $window,$location, amiadminFactory) {
		$scope.page = 'caseSearch';
		$scope.searchFilter ='';
		
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
		$scope.numOfCasesVisible = false;
		$scope.numOfCases = 50;
		
		
		$scope.updateSearchInputDisplay =  function(){
			
			if($scope.searchBy.indexOf("Date") !=-1){
				$scope.searchByInput1 = '';
				$scope.searchByInput2 = '';
				$scope.searchByInput1Visible = true;
				$scope.searchByInput2Visible = true;
				$scope.isSearchAllowed = true;
				$scope.searchByPlaceHolder1 = 'Beg Date mm/dd/yy';
				$scope.searchByPlaceHolder2 = 'End Date mm/dd/yy';
				$scope.numOfCasesVisible = false;
			}
			else if($scope.searchBy.indexOf("Last N Cases") !=-1){
				$scope.searchByInput1 = '';
				$scope.searchByInput2 = '';
				$scope.isSearchAllowed = true;
				$scope.searchByInput1Visible = false;
				$scope.searchByInput2Visible = false;
				$scope.searchByPlaceHolder1 = 'Select a Search';
				$scope.searchByPlaceHolder2 = '';
				$scope.numOfCasesVisible = true;
			}
			else if($scope.searchBy.indexOf("Search By") !=-1){
				$scope.searchByInput1 = '';
				$scope.searchByInput2 = '';
				$scope.isSearchAllowed = false;
				$scope.searchByInput1Visible = true;
				$scope.searchByInput2Visible = false;
				$scope.searchByPlaceHolder1 = 'Select a Search';
				$scope.searchByPlaceHolder2 = '';
				$scope.numOfCasesVisible = false;
			}
			else{
				$scope.searchByInput1 = '';
				$scope.searchByInput2 = '';
				$scope.isSearchAllowed = true;
				$scope.searchByInput1Visible = true;
				$scope.searchByInput2Visible = false;
				$scope.searchByPlaceHolder1 = 'Enter Criteria';
				$scope.searchByPlaceHolder2 = '';
				$scope.numOfCasesVisible = false;
				$scope.isSearchByLastNCases = false;
			}
		}
		
		
		$scope.doSearch =  function(){
			$scope.searchResults = [];
			var requestNumber = $scope.searchByInput1;
			var date2 = $scope.searchByInput2;
			
			$scope.whichSearch();
			
			if( $scope.isSearchByRequestNumber ){
				amiadminFactory.getAmiRequestForAdmin(requestNumber).then(
             			function(result){
             				var myResult = result.data;
             				$scope.searchResults.push(myResult);
             			}	
             	);
			}
			else if( $scope.isSearchByAnimalName ){
				
				amiadminFactory.getAmiRequestByAnimalName(requestNumber).then(
						function(result){
							$scope.searchResults= result.data;
						}	
				);
			}
			else if($scope.isSearchByClientLastName){
				
				amiadminFactory.getAmiRequestByClientLastName(requestNumber).then(
						function(result){
							$scope.searchResults= result.data;
						}	
				);
			}
			else if($scope.isSearchByRequestSubmittedDate){
				
				amiadminFactory.getAmiRequestBySubmittedDateRange(requestNumber,date2).then(
						function(result){
							$scope.searchResults= result.data;
						}	
				);
			}
			else if($scope.isSearchByLastNCases){
				
				amiadminFactory.getAmiRequestByLastNRecords($scope.numOfCases ).then(
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
			$scope.isSearchByLastNCases = false;
			
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
			else if($scope.searchBy.indexOf("Last N Cases") !=-1){
				$scope.isSearchByLastNCases = true;
			}
		}
		
		
		
	});
	// ============ Controller ===============
	amiAdminModule.controller('CaseProcessingCtrl', function ($scope, $http, $window,$location, myCase) {
			$scope.page = 'readCases';
			$scope.amiCase = myCase;
		$scope.amiRequest = myCase.amiRequest;
		
		$scope.saveRadiographicInterpretation = function(){
			
			var data = {caseNumber:$scope.amiCase.caseNumber , radiographicInterpretation: $scope.amiCase.radiographicInterpretation};
			
			var res = $http.post('/ami/amiadmin/updateRadiographicInterpretation',data);
			res.success(function(data, status, headers, config) {
				
			});
			res.error(function(data, status, headers, config) {
				alert( "Radiographic Interpretation not updated, failure message: " + JSON.stringify({data: data}));
			});	
			
		}
		$scope.saveRadiographicImpression = function(){
			var data = {caseNumber:$scope.amiCase.caseNumber , radiographicImpression: $scope.amiCase.radiographicImpression};
			
			var res = $http.post('/ami/amiadmin/updateRadiographicImpression',data);
			res.success(function(data, status, headers, config) {
				
			});
			res.error(function(data, status, headers, config) {
				alert( "Radiographic Impression not updated, failure message: " + JSON.stringify({data: data}));
			});	
		}
		$scope.saveRecommendation = function(){
			var data = {caseNumber:$scope.amiCase.caseNumber , recommendation: $scope.amiCase.recommendation};
			
			var res = $http.post('/ami/amiadmin/updateRecommendation',data);
			res.success(function(data, status, headers, config) {
				
			});
			res.error(function(data, status, headers, config) {
				alert( "Recommendation not updated, failure message: " + JSON.stringify({data: data}));
			});	
		}
		$scope.switchCaseToReadyForReview = function(){
			
			var data = {caseNumber:$scope.amiCase.caseNumber};
			
			var res = $http.post('/ami/amiadmin/switchcasetoreadyforreview',data);
			res.success(function(data, status, headers, config) {
				$location.path('/hospitalAdminSubmittedRequests');
			});
			res.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});	
			
		}
		
		$scope.closeCase = function(){
			
			//var data = {caseNumber:$scope.amiCase.caseNumber , radiographicInterpretation: myCase.radiographicInterpretation , radiographicImpression: myCase.radiographicImpression, recommendation: myCase.recommendation};
			var data = {caseNumber:$scope.amiCase.caseNumber , radiographicInterpretation: myCase.radiographicInterpretation , radiographicImpression: myCase.radiographicImpression, recommendation: myCase.recommendation};
			
			var res = $http.post('/ami/amiadmin/closecase',data);
			res.success(function(data, status, headers, config) {
				$location.path('/hospitalAdminSubmittedRequests');
			});
			res.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});	
			
		}
		
		$scope.addAmendment = function(newAmendment){ 
			
			var data = { caseNumber: $scope.amiCase.caseNumber, newAmendment: newAmendment};
			
			var res = $http.post('/ami/amiadmin/addAmendmentAdmin',data).then(
				function(response) {
					$scope.amiCase.amendments = response.data;
				},
				function(response) {
					alert('error capturing the amendment' );
				});
		}
	
		
	});
	
	amiAdminModule.controller('FileUploadViewCtrl', function ($scope, $http, $window,$location, $route) {
		$scope.imageName =  $route.current.params.file;
		
		
	});
	
	
	
	
	// ============ Controller ===============
	amiAdminModule.controller('HospitalAdminSearchCtrl', function ($scope, $http, $window,$location, allHospitalViews) {
		$scope.page = 'hospitalAdmin';
		$scope.allHospitalViews = allHospitalViews;
		
		$scope.goToNewHospitalPage = function(){
			$location.path('/hospitalAdminCreate');
		}

	});
	// ============ Controller ===============
	amiAdminModule.controller('HospitalAdminCreateCtrl', function ($scope, $http, $window,$location, amiadminFactory) {

		$scope.page = 'hospitalAdmin';
		
		$scope.contractList = amiadminFactory.getContractList();
		$scope.accountList = amiadminFactory.getAccountList();
		
		var masterUser = amiadminFactory.getNewMasterUser()  
		var hospital   = amiadminFactory.getNewHospital();
		
		$scope.hospital = hospital;
		$scope.masterUser = masterUser;
		
		$scope.addPhone = function(){
			var aLabel = $scope.newPhoneLabel;
			var aNumber = $scope.newPhoneNumber;
			var newPhone ={'label': aLabel, 'value': aNumber};
			$scope.hospital.phones.push(newPhone);
			
			$scope.newPhoneLabel  = '';
			$scope.newPhoneNumber = '';
		}
		
		$scope.addEmail = function(){
			var aLabel = $scope.newEmailabel;
			var anEmailValue = $scope.newEmailValue;
			var aNewEmail ={'label': aLabel, 'value': anEmailValue};
			
			$scope.hospital.emails.push(aNewEmail);
			$scope.newEmailabel  = '';
			$scope.newEmailValue  = '';
		}
		
		$scope.addAddress = function(){
			var aLabel = $scope.newAddressLabel;
			var aNewAddressValue = $scope.newAddress;
			var aNewAddress ={'label': aLabel, 'value': aNewAddressValue};
			
			$scope.hospital.addresses.push(aNewAddress);
			$scope.newAddressLabel  = '';
			$scope.newAddress  = '';
		}
		
		$scope.saveNewHospital = function(){

			var data = {hospital:$scope.hospital, masterUser: $scope.masterUser };
			
			var res = $http.post('/ami/amiadminhome/hospital/setup',data);
			res.success(function(data, status, headers, config) {
				$location.path('/hospitalAdminSearch');
			});
			res.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});	
			
		}
		$scope.cancel = function(){
			$location.path('/hospitalAdminSearch');
		}
	});
	
	// ============ Controller ===============
	amiAdminModule.controller('HospitalAdminUpdateCtrl', function ($scope, $http, $window,$location, myHospitalView, amiadminFactory) {
		$scope.page = 'hospitalAdmin';
		$scope.hospital = myHospitalView.hospital;
		$scope.hospitalUsers = myHospitalView.amiUsers;
		
		$scope.contractList = amiadminFactory.getContractList();
		$scope.accountList = amiadminFactory.getAccountList();
		
		if(myHospitalView.amiUsers){
			myHospitalView.amiUsers.forEach(function(aUser){
				if( aUser.masterUser == true){
					$scope.masterUser = aUser;
				}
			})
		}
		
		// initilize the new master user to the current, so that when it is switch we assign it the new value
		$scope.newMasterUser = $scope.masterUser.user;
		
		$scope.updateMasterUserName = function(newUserName){
			$scope.newMasterUser = newUserName;
		}
		
		$scope.cancelSwitchMasterUser = function(newUserName){
			$scope.newMasterUser = $scope.masterUser.user;
		}
		
		$scope.goToNewHospitalPage = function(){
			$location.path('/hospitalAdminCreate');
		}
		
		$scope.cancel = function(){
			$location.path('/hospitalAdminSearch');
		}
		
		
		// ========= hospital updates ========
		
		$scope.hospitalActionIsString = false;
		$scope.hospitalUpdateAction ="";
		$scope.newHospitalUpdateLabel ="";
		$scope.newHospitalUpdateValue ="";
		$scope.hospitalUpdated     = false;
		$scope.hospitalListToUpdate =[];
		
		var clearHospitalUpdateModal = function(){
			
			$scope.updateHospitalErrorMsg = "";
			$scope.hospitalUpdated     = false;
			$scope.modalLabel = "";
			$scope.hospitalUpdateAction = "";
			$scope.hospitalListToUpdate = [];
			$scope.newHospitalUpdateLabel="";
			$scope.newHospitalUpdateValue="";
			$scope.newHospitalUpdatePlaceHolderLabel="";
			$scope.newHospitalUpdatePlaceHolderValue="";
			
			$scope.hospitalActionIsString  = false;
			$scope.inputSizeSmall = false;
			$scope.hospitalStringCurrentValue = "";
			$scope.hospitalStringToUpdate = "";
			
		}
		
		$scope.updateHospital = function(){
			
			var url = '/ami/amiadminhome/hospital/updatehospital';
			
			var myActionValue ='';
			if($scope.hospitalActionIsString ){
				myActionValue = $scope.newHospitalUpdateValue;
			}else{
				myActionValue = $scope.hospitalListToUpdate;
			}
			var data = {'hospitalId': $scope.hospital.id, 'value': myActionValue, 'action': $scope.hospitalUpdateAction};
			
			var res = $http.post(url,data);
			
			res.success(function(data, status, headers, config) {
				
				if( $scope.hospitalUpdateAction == 'updatePhones'){
					$scope.hospital.phones = $scope.hospitalListToUpdate;
				}
				else if($scope.hospitalUpdateAction == 'updateEmails'){
					$scope.hospital.emails = $scope.hospitalListToUpdate;
				}
				else if($scope.hospitalUpdateAction == 'updateAddresses'){
					$scope.hospital.addresses = $scope.hospitalListToUpdate;
				}
				else if($scope.hospitalUpdateAction == 'updateAcronym'){
					$scope.hospital.acronym = $scope.newHospitalUpdateValue;
				}
				else if($scope.hospitalUpdateAction == 'updateNotes'){
					$scope.hospital.notes = $scope.newHospitalUpdateValue;
				}
				
				clearHospitalUpdateModal();
				
			});
			res.error(function(data, status, headers, config) {
				alert( $scope.masterUserUpdateAction + " failed: " + JSON.stringify({data: data}));
			});	
			
			
		}
		
		
		$scope.updateContractOrAccountSize = function(){
			
			
			var url = '/ami/amiadminhome/hospital/updatehospitalcontractoraccount';
			
		var data = {'hospitalId': $scope.hospital.id, 'value': $scope.newHospitalUpdateValue, 'action': $scope.hospitalUpdateAction};
			
			var res = $http.post(url,data);
			
			res.success(function(data, status, headers, config) {
				
				if( $scope.hospitalUpdateAction == 'updateContract'){
					$scope.hospital.contract = $scope.newHospitalUpdateValue;
				}
				else if($scope.hospitalUpdateAction == 'updateAccountSize'){
					$scope.hospital.accountSize = $scope.newHospitalUpdateValue;
				}
				
			});
			res.error(function(data, status, headers, config) {
				alert( $scope.masterUserUpdateAction + " failed: " + JSON.stringify({data: data}));
			});	
		}
		
		
		$scope.removeHospitalUpdateItem = function(aHospitalUpdate){
			$scope.hospitalUpdated     = true;
			
			var value = aHospitalUpdate.value;
			var label = aHospitalUpdate.label;
			
			for(var i = $scope.hospitalListToUpdate.length - 1; i >= 0; i--) {
			    if($scope.hospitalListToUpdate[i].value == value ) {
			    	$scope.hospitalListToUpdate.splice(i, 1);
			    }
			}
		}
		
		$scope.addHospitalUpdateItem = function(){
			
			$scope.updateHospitalErrorMsg = [];
			
			var copyOfNewValue = $scope.newHospitalUpdateValue;
			copyOfNewValue = copyOfNewValue.replace(/\s/g,"");
			if(copyOfNewValue==''){
				$scope.updateHospitalErrorMsg.push( $scope.newHospitalUpdatePlaceHolderValue +" cannot be empty");
				
			}
			
			var copyOfNewLabel = $scope.newHospitalUpdateLabel;
			copyOfNewLabel = copyOfNewLabel.replace(/\s/g,"");
			if(copyOfNewLabel==''){
				$scope.updateHospitalErrorMsg.push($scope.newHospitalUpdatePlaceHolderLabel +" cannot be empty");
			}
			
			if($scope.updateHospitalErrorMsg.length>0){
				return;
			}
			for(var i=0 ; i < $scope.hospitalListToUpdate.length ; i++) {
				var value = $scope.hospitalListToUpdate[i].value;
				var label = $scope.hospitalListToUpdate[i].label;
				
				
				if( copyOfNewValue == value.replace(/\s/g,"")){
					
					$scope.updateHospitalErrorMsg.push( "'"+value + "' already exist with label '"+ label+"'");
					return;
				}
			}
			
			$scope.hospitalUpdated     = true;
			var newValue = {'label':$scope.newHospitalUpdateLabel, 'value': $scope.newHospitalUpdateValue};
			$scope.hospitalListToUpdate.push(newValue);
			$scope.newHospitalUpdateLabel="";
			$scope.newHospitalUpdateValue="";
		}
		
		$scope.updateHospitalPhone = function(){
			$scope.hospitalActionIsString = false;
			$scope.updateHospitalErrorMsg = "";
			$scope.hospitalUpdated     = false;
			$scope.modalLabel = "Update Phone";
			$scope.hospitalUpdateAction = "updatePhones";
			$scope.hospitalListToUpdate = [];
			$scope.newHospitalUpdateLabel="";
			$scope.newHospitalUpdateValue="";
			$scope.newHospitalUpdatePlaceHolderLabel="Label";
			$scope.newHospitalUpdatePlaceHolderValue="New Phone/Fax number";
			
			for(var i=0 ; i < $scope.hospital.phones.length ; i++) {
				var value = $scope.hospital.phones[i];
				$scope.hospitalListToUpdate.push(value);
			}
		}
		
		$scope.updateHospitalEmails = function(){
			$scope.hospitalActionIsString = false;
			$scope.updateHospitalErrorMsg = "";
			$scope.hospitalUpdated     = false;
			$scope.modalLabel = "Update Emails";
			$scope.hospitalUpdateAction = "updateEmails";
			$scope.hospitalListToUpdate = [];
			$scope.newHospitalUpdateLabel="";
			$scope.newHospitalUpdateValue="";
			$scope.newHospitalUpdatePlaceHolderLabel="Email Label";
			$scope.newHospitalUpdatePlaceHolderValue="New Email";
			
			for(var i=0 ; i < $scope.hospital.emails.length ; i++) {
				var value = $scope.hospital.emails[i];
				$scope.hospitalListToUpdate.push(value);
			}
			
		}
		
		$scope.updateHospitalAddresses = function(){
			$scope.hospitalActionIsString = false;
			$scope.updateHospitalErrorMsg = "";
			$scope.hospitalUpdated     = false;
			$scope.modalLabel = "Update Address";
			$scope.hospitalUpdateAction = "updateAddresses";
			$scope.hospitalListToUpdate = [];
			$scope.newHospitalUpdateLabel="";
			$scope.newHospitalUpdateValue="";
			$scope.newHospitalUpdatePlaceHolderLabel="Label";
			$scope.newHospitalUpdatePlaceHolderValue="New Address";
			
			for(var i=0 ; i < $scope.hospital.addresses.length ; i++) {
				var value = $scope.hospital.addresses[i];
				$scope.hospitalListToUpdate.push(value);
			}
			
		}
		
		
		$scope.updateHospitalAcronym = function(){
			$scope.hospitalActionIsString = true;
			$scope.inputSizeSmall = true;
			$scope.modalLabel = "Update Acronym";
			$scope.hospitalUpdateAction = "updateAcronym";
			$scope.newHospitalUpdateValue="";
			$scope.newHospitalUpdatePlaceHolderValue="New Acronym";
			$scope.hospitalStringCurrentValue = $scope.hospital.acronym;
			$scope.hospitalStringToUpdate = $scope.hospital.acronym;
		}
		
		$scope.updateHospitalNotes = function(){
			$scope.hospitalActionIsString = true;
			$scope.inputSizeSmall = false;
			$scope.modalLabel = "Update Notes";
			$scope.hospitalUpdateAction = "updateNotes";
			$scope.newHospitalUpdateValue="";
			$scope.newHospitalUpdatePlaceHolderValue="New Notes";
			$scope.hospitalStringCurrentValue = $scope.hospital.notes;
			$scope.hospitalStringToUpdate = $scope.hospital.notes;
		}
		

		$scope.updateHospitalContract = function(){
			$scope.contractOrAcctList = $scope.contractList;
			$scope.modalLabel = "Update Contract";
			$scope.newHospitalUpdatePlaceHolderValue = $scope.hospital.name + " contract type is "+ $scope.hospital.contract +". Select the new value below and push Save";
			$scope.hospitalUpdateAction = "updateContract";
			$scope.newHospitalUpdateValue= $scope.hospital.contract;
			$scope.hospitalStringCurrentValue = $scope.hospital.contract;
			
		}
		$scope.updateHospitalAccountSize = function(){
			$scope.contractOrAcctList = $scope.accountList;
			$scope.modalLabel = "Update Account Size";
			$scope.newHospitalUpdatePlaceHolderValue = $scope.hospital.name + " account is "+ $scope.hospital.accountSize +". Select the new value below and push Save";
			$scope.hospitalUpdateAction = "updateAccountSize";
			$scope.newHospitalUpdateValue= $scope.hospital.accountSize;
			$scope.hospitalStringCurrentValue = $scope.hospital.accountSize;
			
		}
		
		
	// ========= master user updates ========	
		$scope.masterUserUpdateValue = "";
		$scope.masterUserUpdateAction ="";
		var masterUserUpdateType = {
			pwd:		{'myPwd': 'updatePwd'   },
			email:		{'myEmail': 'updateEmail' },
			firstName:	{'myFirstName': 'updateFirstName' },
			lastName:	{'myLastName': 'updateLastName' },
			occupation: {'myOccupation': 'updateOccupation'}
		}
		
		$scope.updateMasterUserPwd = function(){
			$scope.modalLabel = "Update Password";
			$scope.modalCurrentLabel = "Current Password";
			$scope.modalCurrentValue = $scope.masterUser.pwd;
			$scope.modalNewValuePlaceHolder = "New Password";
			$scope.masterUserUpdateAction = masterUserUpdateType.pwd.myPwd;
		}
		
		$scope.updateMasterUserEmail = function(){
			$scope.modalLabel = "Update Email";
			$scope.modalCurrentLabel = "Current Email";
			$scope.modalCurrentValue = $scope.masterUser.email;
			$scope.modalNewValuePlaceHolder = "New Email";
			$scope.masterUserUpdateAction = masterUserUpdateType.email.myEmail;
		}
		
		$scope.updateMasterUserFirstName = function(){
			$scope.modalLabel = "Update First Name";
			$scope.modalCurrentLabel = "Current First Name";
			$scope.modalCurrentValue = $scope.masterUser.firstName;
			$scope.modalNewValuePlaceHolder = "New First Name";
			$scope.masterUserUpdateAction = masterUserUpdateType.firstName.myFirstName;
		}
		
		$scope.updateMasterUserLastName = function(){
			$scope.modalLabel = "Update Last Name";
			$scope.modalCurrentLabel = "Current Last Name";
			$scope.modalCurrentValue = $scope.masterUser.lastName;
			$scope.modalNewValuePlaceHolder = "New Last Name";
			$scope.masterUserUpdateAction = masterUserUpdateType.lastName.myLastName;
		}
		
		$scope.updateMasterUserOccupation = function(){
			$scope.modalLabel = "Update Occupation";
			$scope.modalCurrentLabel = "Current Occupation";
			$scope.modalCurrentValue = $scope.masterUser.occupation;
			$scope.modalNewValuePlaceHolder = "New Occupation";
			$scope.masterUserUpdateAction = masterUserUpdateType.occupation.myOccupation;
		}
		
		$scope.cancelMasterUserUpdateModal = function(){
			clearModal();
		}
		
		var clearModal = function(){
			$scope.modalLabel = "";
			$scope.modalCurrentLabel = "";
			$scope.modalCurrentValue = "";
			$scope.modalNewValuePlaceHolder = "";
			$scope.masterUserUpdateAction ="";
			$scope.masterUserUpdateValue = "";
			
			
			$scope.newHospitalUpdatePlaceHolderValue = "";
			$scope.hospitalUpdateAction = "";
			$scope.newHospitalUpdateValue= "";
			$scope.hospitalStringCurrentValue ="";
		}
		
		
		$scope.isUpateMasterUserSaveBtnEnabled = function(){
			
			if ( $scope.masterUserUpdateAction == masterUserUpdateType.pwd.myPwd ){
				$scope.showEmptyFieldHintInModal = false;
				if( $scope.masterUserUpdateValue.length <5 ){
					 $scope.showPwdHintInModal = true;
					 return true;
				}
				$scope.showPwdHintInModal = false;
				return false;
			}
			
			$scope.showPwdHintInModal = false;
			if( $scope.masterUserUpdateValue.length <2 ){
				 $scope.showEmptyFieldHintInModal = true;
				 return true;
			}
			$scope.showEmptyFieldHintInModal = false;
			return false;
		}
			
		$scope.switchMasterUserAndSave = function(){
			
			var url = '/ami/amiadminhome/hospital/switchmasteruser';
			var data = {hospitalId: $scope.hospital.id, newMasterUser: $scope.newMasterUser};
			var res = $http.post(url,data);
			
			res.success(function(data, status, headers, config) {
				
				$scope.masterUser = data.amiUser; 
				
			});
			res.error(function(data, status, headers, config) {
				alert( $scope.masterUserUpdateAction + " failed: " + JSON.stringify({data: data}));
			});	
			
		}
		
		
		$scope.updateMasterUser = function(){
				
			var url = '/ami/amiadminhome/hospital/updatemasteruser';
			var data = {hospitalId: $scope.hospital.id, value: $scope.masterUserUpdateValue, 'action': $scope.masterUserUpdateAction};
			var res = $http.post(url,data);
			
			res.success(function(data, status, headers, config) {
				
				if( $scope.masterUserUpdateAction == masterUserUpdateType.pwd.myPwd){
					$scope.masterUser.pwd = $scope.masterUserUpdateValue;
				}
				else if($scope.masterUserUpdateAction == masterUserUpdateType.email.myEmail){
					$scope.masterUser.email = $scope.masterUserUpdateValue;
				}
				else if($scope.masterUserUpdateAction == masterUserUpdateType.firstName.myFirstName){
					$scope.masterUser.firstName = $scope.masterUserUpdateValue;
				}
				else if($scope.masterUserUpdateAction == masterUserUpdateType.lastName.myLastName){
					$scope.masterUser.lastName = $scope.masterUserUpdateValue;
				}
				else if($scope.masterUserUpdateAction == masterUserUpdateType.occupation.myOccupation){
					$scope.masterUser.occupation = $scope.masterUserUpdateValue;
				}
				
				clearModal();
				
			});
			res.error(function(data, status, headers, config) {
				alert( $scope.masterUserUpdateAction + " failed: " + JSON.stringify({data: data}));
			});	
			
		}
		

	});
		
		
})();