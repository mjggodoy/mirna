angular.module('mirna', ['ui.router', 'ngResource', 'spring-data-rest', 'mirna.controllers', 'mirna.services']);

angular.module('mirna').config(function($stateProvider, $locationProvider) {
	
	$stateProvider.state('listMirna', { // state for showing all mirnas
		url: '/mirna',
		templateUrl: 'partials/mirna-list.html',
		controller: 'MirnaListController'
//	}).state('viewMirna', { //state for showing single mirna (redirect to mature or hairpin)
//		url: '/mirna/:id',
//		//templateUrl: '<ui-view>',
//		onEnter: function($state, id) {
//			console.log(id);
//			$state.go('viewMature', {id: '102654'});
//		}
//		//controller: 'MatureViewController'
			
	}).state('goToAcc', { // state to redirect to mirna by accession number
		url: '/puacc/:acc',
		controller: 'GoToAccController',
		resolve: {
			"count" : function($stateParams, Mirna) {
				
				console.log("RESOLVING!");
				var searchData = {
					searchFunction: "acc",
					searchFields: [{
					key: "acc",
						value: $stateParams.acc
					}]
				};
				console.log(searchData);
				
				console.log("CAMBIO2!");
				
				console.log(Mirna);
				
				Mirna.query(null, null, searchData, 
//				function(response) {
//					console.log("RESPONSE!");
//					console.log(response);
//					return response;
////					$scope[elements] = response[elements] ? response[elements] : [];
////					$scope.page = response.page ? response.page : {};
//				}
				null).then (function (data) {
					console.log(data);
	                   return doSomeStuffFirst(data);
	               });
				
				console.log("FIN?");
				
			}
		}
		
		
		
	}).state('searchByAcc', { // state for seaching mirna by accession number
		url: '/search/acc/:acc',
		templateUrl: 'partials/mirna-list.html',
		controller: 'SearchByAccController'
//		controller: 'SearchByAccController',
//		resolve: {
//			"check": function() {
//				
//				console.log("AQUI ESTOY!");
//				
////				$scope.search = {
////					searchFunction: "acc",
////					searchFields: [{
////						key: "acc",
////						value: $stateParams.acc
////					}]
////				};
////				console.log();
////				$scope.sortOptions = [ {value: "id", label: "Id"} ];
////				angular.extend(this, $controller('PagedListController',
////						{$scope: $scope, Object : Mirna, elements : 'mirna'}));
////				
////				console.log($scope);
//				
//				return true;
//				
//				
////				console.log("PUA!");
////				if (false) {
////					console.log("ESTOY AQUI!");
////					//Do something
////				} else {
////					$state.go('home');
////					//$location.path('/search');    //redirect user to home.
////					console.log("You don't have access here");
////				}
//			}
//		}
	}).state('listMature', { // state for showing all matures
		url: '/mature',
		templateUrl: 'partials/mirna-list.html',
		controller: 'MatureListController'
	}).state('viewMature', { //state for showing single mature
		url: '/mature/:id',
		templateUrl: 'partials/mature-view.html',
		controller: 'MatureViewController'
	}).state('listHairpin', { // state for showing all hairpins
		url: '/hairpin',
		templateUrl: 'partials/mirna-list.html',
		controller: 'HairpinListController'
	}).state('viewHairpin', { //state for showing single hairpin
		url: '/hairpin/:id',
		templateUrl: 'partials/hairpin-view.html',
		controller: 'HairpinViewController'
	}).state('viewDeadMirna', { //state for showing single dead mirna
		url: '/dead_mirna/:id',
		templateUrl: 'partials/dead-mirna-view.html',
		controller: 'DeadMirnaViewController'
	}).state('listPhenotype', { // state for showing all phenotypes (diseases para los amigos)
		url: '/phenotype',
		templateUrl: 'partials/phenotype-list.html',
		controller: 'PhenotypeListController'
	}).state('viewPhenotype', { //state for showing single phenotype
		url: '/phenotype/:id',
		templateUrl: 'partials/phenotype-view.html',
		controller: 'PhenotypeViewController'
	}).state('viewEnvironmentalFactor', { //state for showing single small molecule / environmental factor
		url: '/environmental_factor/:id',
		templateUrl: 'partials/environmental-factor-view.html',
		controller: 'EnvironmentalFactorViewController'
	}).state('viewGene', { //state for showing single gene
		url: '/gene/:id',
		templateUrl: 'partials/gene-view.html',
		controller: 'GeneViewController'
	}).state('viewProtein', { //state for showing single gene
		url: '/protein/:id',
		templateUrl: 'partials/protein-view.html',
		controller: 'ProteinViewController'
	}).state('viewPubmedDocument', { //results state 4
		url: '/pubmedDocument_id/:id',
		templateUrl: 'partials/pubmed-view.html',
		controller: 'PubmedDocumentViewController'
	}).state('search', { //search state
		url: '/search',
		templateUrl: 'partials/search.html',
		controller: 'SearchController'
	}).state('searchById', { //results state 1
		url: '/search/id/:id',
		templateUrl: 'partials/mirna-list.html',
		controller: 'SearchByIdController'
	}).state('searchByPhenotypeName', { //results state 2
		url: '/search/phenotype_name/:name',
		templateUrl: 'partials/phenotype-list.html',
		controller: 'SearchByPhenotypeNameController'
	}).state('searchByEnvironmentalFactorName', { //results state 3
		url: '/search/environmental_factor_name/:name',
		templateUrl: 'partials/environmental-factor-list.html',
		controller: 'SearchByEnvironmentalFactorNameController'
	}).state('searchByGeneName', { //results state 4
		url: '/search/gene_name/:name',
		templateUrl: 'partials/gene-list.html',
		controller: 'SearchByGeneNameController'
	}).state('searchByProteinId', { //results state 4
		url: '/search/protein_id/:id',
		templateUrl: 'partials/protein-list.html',
		controller: 'SearchByProteinIdController'
	}).state('searchByPubmedDocumentId', { //results state 4
		url: '/search/pubmed_document_id/:id',
		templateUrl: 'partials/pubmed-list.html',
		controller: 'SearchByPubmedDocumentIdController'
	}).state('searchByTranscriptId', { //results state 4
		url: '/search/transcript_id/:id',
		templateUrl: 'partials/transcript-list.html',
		controller: 'SearchByTranscriptIdController'
	}).state('home', { //home state
		url: '/home',
		templateUrl: 'partials/home.html',
		controller: 'HomeController'
	});

}).run(function($state) {
	$state.go('home'); //make a transition to movies state when app starts
});
