angular.module('mirna.services', []).factory('AbstractFactory', function($http, SpringDataRestAdapter) {
	function AbstractFactory(elements) {
		this.elements = elements;
		this.HATEOAS_URL = 'http://localhost:8080/mirna/api/' + elements + '/';
	}
	
	AbstractFactory.prototype = {
		query: function(pageData, sortData, searchData, callback) {
			var page = pageData.number||0;
			var elems = this.elements;
			var url = this.HATEOAS_URL;
			if (searchData) {
				url += "search/"+searchData.searchFunction;
				url += "?"+searchData.searchField+"="+searchData.searchValue;
				url += "&page="+page;
			} else {
				url += "?page="+page;
			}
			if (pageData.size) url += "&size=" + pageData.size;
			if (sortData.field && sortData.type)
				url += "&sort=" + sortData.field.value + "," + sortData.type;
			var deferred = $http.get(url);
			
			SpringDataRestAdapter.process(deferred).then(function(data) {
				var res = {};
				res[elems] = [];
				angular.forEach(data._embeddedItems, function (o, key) {
					res[elems].push(o);
				});
				res.page = data.page;
				callback && callback(res);
			});
		},
		
		get: function(params, callback) {
			var deferred = $http.get(this.HATEOAS_URL + params.id);
			SpringDataRestAdapter.process(deferred).then(function(data) {
				callback(new Object(data));
			});
		},
		
		getLink: function(params, callback) {
			var deferred = $http.get(this.HATEOAS_URL + params.id + "/" + params.link);
			SpringDataRestAdapter.process(deferred).then(function(data) {
				var res = {};
				res[params.link] = [];
				angular.forEach(data._embeddedItems, function (o, key) {
					res[params.link].push(o);
				});
				callback && callback(res);
			});
		}
	
	}
	
	return AbstractFactory;
  
}).factory('Mirna', function(AbstractFactory) {
	var extended = new AbstractFactory('mirna');
	return extended;
});