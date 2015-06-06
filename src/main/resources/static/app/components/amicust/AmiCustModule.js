(function(){
	
		var app = angular.module('AmiCustModule',[]);
		
		
		// ============ NewRequest ===============
		app.controller('NewRequestCtrl', function ($scope, $window,$location) {
			$scope.page = 'newRequest';
			$scope.labs = ['PCL', 'IDEXX', 'Antech'];
			
			$scope.species =['Canine','Feline','Bovine','Birds'];
			
			$scope.breeds = ['Greman Sheppard','Chiwawa','Boxer','Poodle'];
			

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