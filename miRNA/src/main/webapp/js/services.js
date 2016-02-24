angular.module('mirna.services', []).factory('AbstractFactory', function($http, SpringDataRestAdapter) {
	
	function AbstractFactory(elements, path) {
		if (path==undefined) path = elements;
		this.elements = elements;
		this.HATEOAS_URL = 'http://localhost:8080/mirna/api/' + path + '/';
	}
	
	AbstractFactory.prototype = {
		query: function(pageData, sortData, searchData, callback) {
			var page = pageData.number||0;
			var elems = this.elements;
			var url = this.HATEOAS_URL;
			if (searchData) {
				url += "search/"+searchData.searchFunction;
				url += "?page="+page;
				for (var i in searchData.searchFields) {
					var par = searchData.searchFields[i];
					url += "&"+par.key+"="+par.value;
				}
			} else {
				url += "?page="+page;
			}
			if (pageData.projection) url += "&projection="+pageData.projection;
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
			}, function(error) {
				console.log(error);
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
}).factory('Mature', function(AbstractFactory) {
	var extended = new AbstractFactory('mirna', 'mature');
	return extended;
}).factory('Hairpin', function(AbstractFactory) {
	var extended = new AbstractFactory('mirna', 'hairpin');
	return extended;
}).factory('DeadMirna', function(AbstractFactory) {
	var extended = new AbstractFactory('mirna', 'dead_mirna');
	return extended;
}).factory('PubmedDocument', function(AbstractFactory) {
	var extended = new AbstractFactory('pubmed_document');
	return extended;
}).factory('ExpressionData', function(AbstractFactory) {
	var extended = new AbstractFactory('expression_data');
	return extended;
}).factory('InteractionData', function(AbstractFactory) {
	var extended = new AbstractFactory('interaction_data');
	return extended;
}).factory('Disease', function(AbstractFactory) {
	var extended = new AbstractFactory('disease');
	return extended;
}).factory('SNP', function(AbstractFactory) {
	var extended = new AbstractFactory('snp');
	return extended;
}).factory('EnvironmentalFactor', function(AbstractFactory) {
	var extended = new AbstractFactory('environmental_factor');
	return extended;
}).factory('Gene', function(AbstractFactory) {
	var extended = new AbstractFactory('gene');
	return extended;
});