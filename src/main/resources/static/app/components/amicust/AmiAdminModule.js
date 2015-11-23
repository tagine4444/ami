(function(){
	
	var amiAdminModule = angular.module('AmiAdminModule',[]);
	
	// ============ SearchRequest ===============
	amiAdminModule.controller('CaseQueueCtrl', function ($scope, $http, $window,$location) {
		$scope.page = 'caseQueue';
			
		
	});
	// ============ SearchRequest ===============
	amiAdminModule.controller('HospitalAdminCtrl', function ($scope, $http, $window,$location) {

		$scope.page = 'hospitalAdmin';
		
		var masterUser ={
				'username'   : '', 
				'pwd'   	 : '',
				'email'   	 : '',
				'firstName'  : '',
				'lastName'   : '',
				'occupation' : ''
			};
		
		var hospital ={
				'name'  	: '', //default to phone
				'address'   : [],
				'phones'	: [],
				'emails'	: []
			};
		
		$scope.hospital = hospital;
		$scope.masterUser = masterUser;
		
		$scope.addPhone = function(){
			var aLabel = $scope.newPhoneLabel;
			var aNumber = $scope.newPhoneNumber;
			var newPhone ={'label': aLabel, 'number': aNumber};
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
			
			$scope.hospital.address.push(aNewAddress);
			$scope.newAddressLabel  = '';
			$scope.newAddress  = '';
		}
		
		$scope.saveNewHospital = function(){
			
			var hosp = $scope.hospital;
			var mUser = $scope.masterUser;
		}
		$scope.cancel = function(){
			
			
		}
		
	});
		
		
})();