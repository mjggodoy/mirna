angular.module('mirna.controllers', [])
.controller('PagedListController',function($scope, $state, Object, elements) {
	
	$scope.page = {};
	if ($scope.pageSize) {
		$scope.page.size = $scope.pageSize;
	}
	if ($scope.projection) {
		$scope.page.projection = $scope.projection;
	}
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
	
}).controller('PhenotypeListController', function($scope, $controller, Disease) {
	$scope.sortOptions = [ {value: "name", label: "Name"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Disease, elements : 'disease'}));

}).controller('SearchByIdController', function($scope, $controller, $stateParams, Mirna) {
	$scope.search = {
		searchFunction: "id",
		searchField: "id",
		searchValue: $stateParams.id
	};
	$scope.sortOptions = [ {value: "id", label: "Id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Mirna, elements : 'mirna'}));
	
}).controller('SearchByPhenotypeNameController', function($scope, $controller, $stateParams, Disease) {
	$scope.search = {
		searchFunction: "name",
		searchField: "name",
		searchValue: $stateParams.name
	};
	$scope.sortOptions = [ {value: "name", label: "Name"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Disease, elements : 'disease'}));

}).controller('MirnaViewController',
		function($scope, $controller, $stateParams, Object, complementary, PubmedDocument, ExpressionData) {
	
	Object.get({ id: $stateParams.id }, function(response) {
		$scope.mirna = response ? response : {};
		if ($scope.mirna) {
			
			Object.getLink({ id: $stateParams.id, link: complementary }, function(response) {
				$scope.mirna[complementary] = response ? response[complementary] : {};
			});
			
			$scope.mirna.pubmed_documents = {};
			$scope.mirna.pubmed_documents.pageSize = 10;
			$scope.mirna.pubmed_documents.search = {
					searchFunction: "mirna_pk",
					searchField: "pk",
					searchValue: $stateParams.id
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.mirna.pubmed_documents, Object : PubmedDocument, elements : 'pubmed_document'}));
			
			$scope.mirna.expression_datas = {};
			$scope.mirna.expression_datas.pageSize = 10;
			$scope.mirna.expression_datas.projection = "inlineDisease";
			$scope.mirna.expression_datas.search = {
					searchFunction: "mirna_pk",
					searchField: "pk",
					searchValue: $stateParams.id
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.mirna.expression_datas, Object : ExpressionData, elements : 'expression_data'}));
		}
	});
	
}).controller('MatureViewController', function($scope, $controller, $stateParams, Mature) {
	
	angular.extend(this, $controller('MirnaViewController',
			{$scope: $scope, Object : Mature, complementary : 'hairpins'}));
	
}).controller('HairpinViewController', function($scope, $controller, $stateParams, Hairpin) {
	
	angular.extend(this, $controller('MirnaViewController',
			{$scope: $scope, Object : Hairpin, complementary : 'matures'}));
	
}).controller('PhenotypeViewController', function($scope, $controller, $stateParams, Disease) {
	
	Disease.get({ id: $stateParams.id }, function(response) {
		$scope.disease = response ? response : {};
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
	
	$scope.findByPhenotypeName = function() {
		if ($scope.phenotypeNameText) {
			$state.go('searchByPhenotypeName', {name: $scope.phenotypeNameText});
		}
	};
	
});
