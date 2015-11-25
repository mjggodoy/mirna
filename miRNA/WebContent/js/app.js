angular.module('mirna', ['ui.router', 'ngResource', 'spring-data-rest', 'mirna.controllers', 'mirna.services']);

angular.module('mirna').config(function($stateProvider, $locationProvider) {
  
  $stateProvider.state('listMirna', { // state for showing all mirnas
    url: '/mirna',
    templateUrl: 'partials/mirna-list.html',
    controller: 'MirnaListController'
  }).state('viewMirna', { //state for showing single mirna
    url: '/mirna/:id',
    templateUrl: 'partials/mirna-view.html',
    controller: 'MirnaViewController'
  }).state('home', { //home state
    url: '/home',
    templateUrl: 'partials/home.html'
  });
    
}).run(function($state) {
  $state.go('home'); //make a transition to movies state when app starts
  //console.log($state);
});
