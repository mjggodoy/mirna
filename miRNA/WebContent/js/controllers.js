angular.module('mirna.controllers', [])
.controller('PagedListController',function($scope, $state, Object, elements) {
	
	$scope.page = {};
	if (!$scope.sort) {
		$scope.sort = {};
		if ($scope.sortOptions) {
			$scope.sort.field = $scope.sortOptions[0];
		}
		$scope.sort.type = "asc";
	}
	
	// Fetch all elements. Issues a GET to /api/<elements>
	$scope.loadPage = function() {
		Object.query($scope.page, $scope.sort, $scope.search, function(response) {
			$scope[elements] = response[elements] ? response[elements] : [];
			$scope.page = response.page ? response.page : {};
		});
	};
	
	$scope.nextPage = function() {
		if ($scope.page.number+1<$scope.page.totalPages) {
			$scope.page.number++;
			$scope.loadPage();
		}
	};
	
	$scope.previousPage = function() {
		if ($scope.page.number > 0) {
			$scope.page.number--;
			$scope.loadPage();
		}
	};
	
	$scope.loadPage();
  
}).controller('MirnaListController', function($scope, $controller, Mirna) {
	$scope.sortOptions = [ {value: "name", label: "Name"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Mirna, elements : 'mirna'}));

}).controller('MirnaViewController', function($scope, $stateParams, Mirna) {
	//Get a single mirna. Issues a GET to /api/mirna/:id
	Mirna.get({ id: $stateParams.id }, function(response) {
		$scope.mirna = response ? response : {};
	});

});
