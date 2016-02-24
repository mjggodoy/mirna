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
	}).state('home', { //home state
		url: '/home',
		templateUrl: 'partials/home.html',
		controller: 'HomeController'
	});

}).run(function($state) {
	$state.go('home'); //make a transition to movies state when app starts
});
