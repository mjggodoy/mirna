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
	}).state('search', { //search state
		url: '/search',
		templateUrl: 'partials/search.html',
		controller: 'SearchController'
	}).state('searchById', { //results state
		url: '/search/id/:id',
		templateUrl: 'partials/mirna-list.html',
		controller: 'SearchByIdController'
	}).state('home', { //home state
		url: '/home',
		templateUrl: 'partials/home.html',
		controller: 'HomeController'
	});

}).run(function($state) {
	$state.go('home'); //make a transition to movies state when app starts
});
