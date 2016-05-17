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
		url: '/acc/:acc',
		resolve: {
			"count" : function($stateParams, $http, $state) {
				var url = 'api/mirna/search/acc?acc='+$stateParams.acc;
				return $http({method: 'GET', url: url}).then (function (data) {
					
					if (data.data.page.totalElements==1) {
						var id = data.data._embedded.mirna[0].pk;
						var type = data.data._embedded.mirna[0].type;
						if (type=='hairpin') {
							$state.go('viewHairpin', {id: id});
						} else if (type=='mature') {
							$state.go('viewMature', {id: id});
						} else if (type=='dead') {
							$state.go('viewDeadMirna', {id: id});
						}
					} else {
						$state.go('searchByAcc', {acc: $stateParams.acc});
					}
					return false;
				}, function(error) {
					console.log(error);
					return false;
				});
			}
		}
	}).state('searchByAcc', { // state for searching mirna by accession number
		url: '/search/acc/:acc',
		templateUrl: 'partials/mirna-list.html',
		controller: 'SearchByAccController'
	}).state('listMature', { // state for showing all matures
		url: '/mirna/mature',
		templateUrl: 'partials/mirna-list.html',
		controller: 'MatureListController'
	}).state('viewMature', { //state for showing single mature
		url: '/mirna/mature/:id',
		templateUrl: 'partials/mature-view.html',
		controller: 'MatureViewController'
	}).state('listHairpin', { // state for showing all hairpins
		url: '/mirna/hairpin',
		templateUrl: 'partials/mirna-list.html',
		controller: 'HairpinListController'
	}).state('viewHairpin', { //state for showing single hairpin
		url: '/mirna/hairpin/:id',
		templateUrl: 'partials/hairpin-view.html',
		controller: 'HairpinViewController'
	}).state('viewDeadMirna', { //state for showing single dead mirna
		url: '/mirna/dead_mirna/:id',
		templateUrl: 'partials/dead-mirna-view.html',
		controller: 'DeadMirnaViewController'
	}).state('listPhenotype', { // state for showing all phenotypes (diseases para los amigos)
		url: '/phenotype',
		templateUrl: 'partials/phenotype-list.html',
		controller: 'PhenotypeListController'
	}).state('listBiologicalProcess', { // state for showing all phenotypes (diseases para los amigos)
		url: '/biologicalProcess',
		templateUrl: 'partials/biological-process-list.html',
		controller: 'BiologicalProcessListController'
	}).state('listProtein', { // state for showing all phenotypes (diseases para los amigos)
		url: '/protein',
		templateUrl: 'partials/protein-list.html',
		controller: 'ProteinListController'	
	}).state('listGene', { // state for showing all phenotypes (diseases para los amigos)
		url: '/gene',
		templateUrl: 'partials/gene-list.html',
		controller: 'GeneListController'	
	}).state('listSNP', { // state for showing all phenotypes (diseases para los amigos)
		url: '/snp',
		templateUrl: 'partials/snp-list.html',
		controller: 'SNPListController'	
	}).state('listPubmedDocument', { // state for showing all phenotypes (diseases para los amigos)
		url: '/PubmedDocument',
		templateUrl: 'partials/pubmed-list.html',
		controller: 'PubmedDocumentListController'
	}).state('listTranscript', { // state for showing all phenotypes (diseases para los amigos)
		url: '/transcript',
		templateUrl: 'partials/transcript-list.html',
		controller: 'TranscriptListController'	
	}).state('listEnvironmentalFactor', { // state for showing all phenotypes (diseases para los amigos)
		url: '/environmental_factor',
		templateUrl: 'partials/environmental-factor-list.html',
		controller: 'EnvironmentalFactorListController'			
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
	}).state('viewBiologicalProcess', { //state for showing single small molecule / environmental factor
		url: '/biological_process/:id',
		templateUrl: 'partials/biological-process-view.html',
		controller: 'BiologicalProcessViewController'
	}).state('viewProtein', { //state for showing single gene
		url: '/protein/:id',
		templateUrl: 'partials/protein-view.html',
		controller: 'ProteinViewController'
	}).state('viewPubmedDocument', { //results state 4
		url: '/pubmed_document/:id',
		templateUrl: 'partials/pubmed-view.html',
		controller: 'PubmedDocumentViewController'
	}).state('viewTranscript', { //results state 4
		url: '/transcript_id/:id',
		templateUrl: 'partials/transcript-view.html',
		controller: 'TranscriptViewController'
	}).state('viewTarget', { //results state 4
		url: '/target_id/:id',
		templateUrl: 'partials/target-view.html',
		controller: 'TargetViewController'
	}).state('viewSNP', { //view for SNP
		url: '/snp_id/:id',
		templateUrl: 'partials/snp-view.html',
		controller: 'SNPViewController'
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
	}).state('searchByBiologicalProcessName', { //results state 5
		url: '/search/biological_process/:name',
		templateUrl: 'partials/biological-process-list.html',
		controller: 'SearchByBiologicalProcessNameController'
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
	}).state('searchBySnpId', { //results state snp
		url: '/search/snp_id/:id',
		templateUrl: 'partials/snp-list.html',
		controller: 'SearchBySnpIdController'
	}).state('home', { //home state
		url: '/home',
		templateUrl: 'partials/home.html',
		controller: 'HomeController'
	});

}).run(function($state, $rootScope, Util) {
	$rootScope.Util = Util;
	$rootScope.$state = $state;
	$state.go('home'); //make a transition to home state when app starts
});
