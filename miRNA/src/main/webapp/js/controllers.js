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
	$scope.sortOptions = [ {value: "id", label: "Id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Mirna, elements : 'mirna'}));

}).controller('MatureListController', function($scope, $controller, Mature) {
	$scope.sortOptions = [ {value: "id", label: "Id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Mature, elements : 'mirna'}));
	
}).controller('HairpinListController', function($scope, $controller, Hairpin) {
	$scope.sortOptions = [ {value: "id", label: "Id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Hairpin, elements : 'mirna'}));

}).controller('SearchByIdController', function($scope, $controller, $stateParams, Mirna) {
	$scope.search = {
		searchFunction: "findByIdContaining",
		searchField: "id",
		searchValue: $stateParams.id
	};
	$scope.sortOptions = [ {value: "id", label: "Id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Mirna, elements : 'mirna'}));

//}).controller('MirnaViewController', function($scope, $stateParams, Mirna) {
//	//Get a single mirna. Issues a GET to /api/mirna/:id
//	Mirna.get({ id: $stateParams.id }, function(response) {
//		$scope.mirna = response ? response : {};
////		if ($scope.mirna) {
////			if ($scope.mirna.mature) {
////				Mirna.getLink({ id: $stateParams.id, link: "hairpins" }, function(response) {
////					$scope.mirna.hairpins = response ? response.hairpins : {};
////				});
////			} else {
////				Mirna.getLink({ id: $stateParams.id, link: "matures" }, function(response) {
////					$scope.mirna.matures = response ? response.matures : {};
////				});
////			}
////		}
//	});
	
}).controller('MatureViewController', function($scope, $stateParams, Mature) {
	//Get a single mature. Issues a GET to /api/mature/:id
	Mature.get({ id: $stateParams.id }, function(response) {
		$scope.mirna = response ? response : {};
		if ($scope.mirna) {
			Mature.getLink({ id: $stateParams.id, link: "hairpins" }, function(response) {
				$scope.mirna.hairpins = response ? response.hairpins : {};
			});
		}
	});
	
}).controller('HairpinViewController', function($scope, $stateParams, Hairpin) {
	//Get a single hairpin. Issues a GET to /api/hairpin/:id
	Hairpin.get({ id: $stateParams.id }, function(response) {
		$scope.mirna = response ? response : {};
		if ($scope.mirna) {
			Hairpin.getLink({ id: $stateParams.id, link: "matures" }, function(response) {
				$scope.mirna.matures = response ? response.matures : {};
			});
		}
	});

}).controller('HomeController', function($scope, $state){
	
	$scope.quickSearchText = 'hsa-let-7a';
	
	$scope.quickSearch = function() {
		if ($scope.quickSearchText) {
			$state.go('searchById', {id: $scope.quickSearchText});
		}
	};
}).controller('SearchController', function($scope, $state){
	
	$scope.findById = function() {
		if ($scope.idText) {
			$state.go('searchById', {id: $scope.idText});
		}
	};
});
