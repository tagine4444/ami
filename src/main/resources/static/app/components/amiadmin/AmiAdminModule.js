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
		
		if(myHospitalView.amiUsers){
			myHospitalView.amiUsers.forEach(function(aUser){
				if( aUser.masterUser == true){
					$scope.masterUser = aUser;
				}
			})
		}
		
		$scope.goToNewHospitalPage = function(){
			$location.path('/hospitalAdminCreate');
		}
		
		$scope.cancel = function(){
			$location.path('/hospitalAdminSearch');
		}

	});
		
		
})();