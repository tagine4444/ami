(function(){
	
		var app = angular.module('AmiCustCtrl',[]);
			
		app.controller('AmiCustCtrl', function ($scope, $window) {
			 
		$scope.tabs = [
		    { title:'Dynamic Title 1', activeCss:'active' },
		    { title:'Dynamic Title 2', activeCss:'active', disabled: true },
		    { title:'Dynamic Title 3', activeCss:'active', disabled: true },
		    { title:'Dynamic Title 4', activeCss:'active', disabled: true },
		    { title:'Dynamic Title 5', activeCss:'Dynamic content 5', disabled: true },
		    { title:'Dynamic Title 6', activeCss:'Dynamic content 6', disabled: true },
		    { title:'Dynamic Title 7', activeCss:'Dynamic content 7', disabled: true }
		  ];
	});
	
})();	