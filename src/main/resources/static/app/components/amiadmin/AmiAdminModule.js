(function(){
	
	var amiAdminModule = angular.module('AmiAdminModule',[]);
	
	// ============ Controller ===============
	amiAdminModule.controller('CaseQueueCtrl', function ($scope, $http, $window,$location) {
		$scope.page = 'caseQueue';
			
		
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
	amiAdminModule.controller('HospitalAdminUpdateCtrl', function ($scope, $http, $window,$location, myHospitalView) {
		$scope.page = 'hospitalAdmin';
		$scope.hospital = myHospitalView.hospital;
		$scope.hospitalUsers = myHospitalView.amiUsers;
		
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