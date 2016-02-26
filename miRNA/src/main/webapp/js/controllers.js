angular
		.module('mirna.controllers', [])
		.controller(
				'PagedListController',
				function($scope, $state, Object, elements) {

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
						Object
								.query(
										$scope.page,
										$scope.sort,
										$scope.search,
										function(response) {
											$scope[elements] = response[elements] ? response[elements]
													: [];
											$scope.page = response.page ? response.page
													: {};
										});
					};

					$scope.nextPage = function() {
						if ($scope.page.number + 1 < $scope.page.totalPages) {
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

				})
		.controller('MirnaListController',
				function($scope, $controller, Mirna) {
					$scope.sortOptions = [ {
						value : "id",
						label : "Id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Mirna,
						elements : 'mirna'
					}));

				})
		.controller('MatureListController',
				function($scope, $controller, Mature) {
					$scope.sortOptions = [ {
						value : "id",
						label : "Id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Mature,
						elements : 'mirna'
					}));

				})
		.controller('HairpinListController',
				function($scope, $controller, Hairpin) {
					$scope.sortOptions = [ {
						value : "id",
						label : "Id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Hairpin,
						elements : 'mirna'
					}));

				})
		.controller('PhenotypeListController',
				function($scope, $controller, Disease) {
					$scope.sortOptions = [ {
						value : "name",
						label : "Name"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Disease,
						elements : 'disease'
					}));

				})
		.controller('SearchByIdController',
				function($scope, $controller, $stateParams, Mirna) {
					$scope.search = {
						searchFunction : "id",
						searchFields : [ {
							key : "id",
							value : $stateParams.id
						} ]
					};
					$scope.sortOptions = [ {
						value : "id",
						label : "Id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Mirna,
						elements : 'mirna'
					}));

				})
		.controller('SearchByPhenotypeNameController',
				function($scope, $controller, $stateParams, Disease) {
					$scope.search = {
						searchFunction : "name",
						searchFields : [ {
							key : "name",
							value : $stateParams.name
						} ]
					};
					$scope.sortOptions = [ {
						value : "name",
						label : "Name"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Disease,
						elements : 'disease'
					}));

				})
		.controller(
				'SearchByEnvironmentalFactorNameController',
				function($scope, $controller, $stateParams, EnvironmentalFactor) {
					$scope.search = {
						searchFunction : "name",
						searchFields : [ {
							key : "name",
							value : $stateParams.name
						} ]
					};
					$scope.sortOptions = [ {
						value : "name",
						label : "Name"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : EnvironmentalFactor,
						elements : 'environmental_factor'
					}));

				})
		.controller('SearchByGeneNameController',
				function($scope, $controller, $stateParams, Gene) {
					$scope.search = {
						searchFunction : "name",
						searchFields : [ {
							key : "name",
							value : $stateParams.name
						} ]
					};
					$scope.sortOptions = [ {
						value : "name",
						label : "Name"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Gene,
						elements : 'gene'
					}));

				})
		.controller('SearchByBiologicalProcessNameController',
				function($scope, $controller, $stateParams, BiologicalProcess) {
					$scope.search = {
						searchFunction : "name",
						searchFields : [ {
							key : "name",
							value : $stateParams.name
						} ]
					};
					$scope.sortOptions = [ {
						value : "name",
						label : "Name"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : BiologicalProcess,
						elements : 'biological_process'
					}));

				})
		.controller('SearchByProteinIdController',
				function($scope, $controller, $stateParams, Protein) {
					$scope.search = {
						searchFunction : "id",
						searchFields : [ {
							key : "id",
							value : $stateParams.id
						} ]
					};
					$scope.sortOptions = [ {
						value : "id",
						label : "id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Protein,
						elements : 'protein'
					}));

				})
		.controller('SearchByPubmedDocumentIdController',
				function($scope, $controller, $stateParams, PubmedDocument) {
					$scope.search = {
						searchFunction : "id",
						searchFields : [ {
							key : "id",
							value : $stateParams.id
						} ]
					};
					$scope.sortOptions = [ {
						value : "id",
						label : "id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : PubmedDocument,
						elements : 'pubmed_document'
					}));

				})
		.controller('SearchByTranscriptIdController',
				function($scope, $controller, $stateParams, Transcript) {
					$scope.search = {
						searchFunction : "id",
						searchFields : [ {
							key : "id",
							value : $stateParams.id
						} ]
					};
					$scope.sortOptions = [ {
						value : "id",
						label : "id"
					} ];
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope,
						Object : Transcript,
						elements : 'transcript'
					}));

				})
		.controller(
				'MirnaViewController',
				function($scope, $controller, $stateParams, Object,
						complementary, PubmedDocument, ExpressionData) {

					Object
							.get(
									{
										id : $stateParams.id
									},
									function(response) {
										$scope.mirna = response ? response : {};
										if ($scope.mirna) {

											if (complementary) {
												Object
														.getLink(
																{
																	id : $stateParams.id,
																	link : complementary
																},
																function(
																		response) {
																	$scope.mirna[complementary] = response ? response[complementary]
																			: {};
																});
											}

											$scope.mirna.pubmed_documents = {};
											$scope.mirna.pubmed_documents.pageSize = 10;
											$scope.mirna.pubmed_documents.search = {
												searchFunction : "mirna_pk",
												searchFields : [ {
													key : "pk",
													value : $stateParams.id
												} ]
											};
											angular
													.extend(
															this,
															$controller(
																	'PagedListController',
																	{
																		$scope : $scope.mirna.pubmed_documents,
																		Object : PubmedDocument,
																		elements : 'pubmed_document'
																	}));

											$scope.expression_datas = {};
											$scope.expression_datas.pageSize = 10;
											$scope.expression_datas.projection = "inlineDisease";
											$scope.expression_datas.search = {
												searchFunction : "mirna_pk",
												searchFields : [ {
													key : "pk",
													value : $stateParams.id
												} ]
											};
											angular
													.extend(
															this,
															$controller(
																	'PagedListController',
																	{
																		$scope : $scope.expression_datas,
																		Object : ExpressionData,
																		elements : 'expression_data'
																	}));
										}
									});

				})
		.controller('MatureViewController',
				function($scope, $controller, $stateParams, Mature) {

					angular.extend(this, $controller('MirnaViewController', {
						$scope : $scope,
						Object : Mature,
						complementary : 'hairpins'
					}));

				})
		.controller('HairpinViewController',
				function($scope, $controller, $stateParams, Hairpin) {

					angular.extend(this, $controller('MirnaViewController', {
						$scope : $scope,
						Object : Hairpin,
						complementary : 'matures'
					}));

				})
		.controller('DeadMirnaViewController',
				function($scope, $controller, $stateParams, DeadMirna) {

					angular.extend(this, $controller('MirnaViewController', {
						$scope : $scope,
						Object : DeadMirna,
						complementary : null
					}));

				})
		.controller(
				'PhenotypeViewController',
				function($scope, $controller, $stateParams, Disease, Mirna,
						ExpressionData, SNP) {

					Disease.get({
						id : $stateParams.id
					}, function(response) {
						$scope.disease = response ? response : {};
						if ($scope.disease) {

							$scope.disease.related_mirnas = {};
							$scope.disease.related_mirnas.pageSize = 50;
							$scope.disease.related_mirnas.search = {
								searchFunction : "related_to_disease",
								searchFields : [ {
									key : "pk",
									value : $stateParams.id
								} ]
							};
							angular.extend(this, $controller(
									'PagedListController', {
										$scope : $scope.disease.related_mirnas,
										Object : Mirna,
										elements : 'mirna'
									}));

							$scope.snps = {};
							$scope.snps.pageSize = 10;
							$scope.snps.search = {
								searchFunction : "disease_pk",
								searchFields : [ {
									key : "pk",
									value : $stateParams.id
								} ]
							};
							angular.extend(this, $controller(
									'PagedListController', {
										$scope : $scope.snps,
										Object : SNP,
										elements : 'snp'
									}));
						}

						$scope.filterByMirna = function(mirna) {
							$scope.filtered_mirna = mirna;
							$scope.expression_datas = {};
							$scope.expression_datas.pageSize = 5;
							$scope.expression_datas.search = {
								searchFunction : "mirna_pk_and_disease_pk",
								searchFields : [ {
									key : "mirna_pk",
									value : mirna.pk
								}, {
									key : "disease_pk",
									value : $stateParams.id
								} ]
							};
							angular.extend(this, $controller(
									'PagedListController', {
										$scope : $scope.expression_datas,
										Object : ExpressionData,
										elements : 'expression_data'
									}));
						}

					});

				})
		.controller(
				'EnvironmentalFactorViewController',
				function($scope, $controller, $stateParams,
						EnvironmentalFactor, Mirna, ExpressionData) {

					EnvironmentalFactor
							.get(
									{
										id : $stateParams.id
									},
									function(response) {
										$scope.environmental_factor = response ? response
												: {};
										if ($scope.environmental_factor) {

											$scope.environmental_factor.related_mirnas = {};
											$scope.environmental_factor.related_mirnas.pageSize = 50;
											$scope.environmental_factor.related_mirnas.search = {
												searchFunction : "related_to_environmental_factor",
												searchFields : [ {
													key : "pk",
													value : $stateParams.id
												} ]
											};
											angular
													.extend(
															this,
															$controller(
																	'PagedListController',
																	{
																		$scope : $scope.environmental_factor.related_mirnas,
																		Object : Mirna,
																		elements : 'mirna'
																	}));
										}

										$scope.filterByMirna = function(mirna) {
											$scope.filtered_mirna = mirna;
											$scope.expression_datas = {};
											$scope.expression_datas.pageSize = 5;
											$scope.expression_datas.search = {
												searchFunction : "mirna_pk_and_environmental_factor_pk",
												searchFields : [
														{
															key : "mirna_pk",
															value : mirna.pk
														},
														{
															key : "environmental_factor_pk",
															value : $stateParams.id
														} ]
											};
											angular
													.extend(
															this,
															$controller(
																	'PagedListController',
																	{
																		$scope : $scope.expression_datas,
																		Object : ExpressionData,
																		elements : 'expression_data'
																	}));
										}

									});

				})
		.controller(
				'GeneViewController',
				function($scope, $controller, $stateParams, Gene, Mirna,
						InteractionData) {

					Gene.get({
						id : $stateParams.id
					}, function(response) {
						$scope.gene = response ? response : {};
						if ($scope.gene) {

							$scope.gene.related_mirnas = {};
							$scope.gene.related_mirnas.pageSize = 50;
							$scope.gene.related_mirnas.search = {
								searchFunction : "related_to_gene",
								searchFields : [ {
									key : "pk",
									value : $stateParams.id
								} ]
							};
							angular.extend(this, $controller(
									'PagedListController', {
										$scope : $scope.gene.related_mirnas,
										Object : Mirna,
										elements : 'mirna'
									}));
						}

						$scope.filterByMirna = function(mirna) {
							$scope.filtered_mirna = mirna;
							$scope.interaction_datas = {};
							$scope.interaction_datas.pageSize = 5;
							$scope.interaction_datas.search = {
								searchFunction : "mirna_pk_and_gene_pk",
								searchFields : [ {
									key : "mirna_pk",
									value : mirna.pk
								}, {
									key : "gene_pk",
									value : $stateParams.id
								} ]
							};
							angular.extend(this, $controller(
									'PagedListController', {
										$scope : $scope.interaction_datas,
										Object : InteractionData,
										elements : 'interaction_data'
									}));
						}

					});

				})
		.controller(
				'BiologicalProcessViewController',
				function($scope, $controller, $stateParams, BiologicalProcess,
						Mirna) {

					BiologicalProcess
							.get(
									{
										id : $stateParams.id
									},
									function(response) {
										$scope.biological_process = response ? response
												: {};
										if ($scope.biological_process) {

											$scope.biological_process.related_mirnas = {};
											$scope.biological_process.related_mirnas.pageSize = 50;
											$scope.biological_process.related_mirnas.search = {
												searchFunction : "biological_process_pk",
												searchFields : [ {
													key : "pk",
													value : $stateParams.id
												} ]
											};
											angular
													.extend(
															this,
															$controller(
																	'PagedListController',
																	{
																		$scope : $scope.biological_process.related_mirnas,
																		Object : Mirna,
																		elements : 'mirna'
																	}));
										}

										
									});

				}).controller('HomeController', function($scope, $state) {

			$scope.quickSearchText = 'hsa-let-7a';
			$scope.quickSearch = function() {
				if ($scope.quickSearchText) {
					$state.go('searchById', {
						id : $scope.quickSearchText
					});
				}
			};

		}).controller('SearchController', function($scope, $state) {

			$scope.findById = function() {
				if ($scope.idText) {
					$state.go('searchById', {
						id : $scope.idText
					});
				}
			};

			$scope.findByPhenotypeName = function() {
				if ($scope.phenotypeNameText) {
					$state.go('searchByPhenotypeName', {
						name : $scope.phenotypeNameText
					});
				}
			};

			$scope.findByEnvironmentalFactorName = function() {
				if ($scope.environmentalFactorNameText) {
					$state.go('searchByEnvironmentalFactorName', {
						name : $scope.environmentalFactorNameText
					});
				}
			};

			$scope.findByGeneName = function() {
				if ($scope.geneNameText) {
					$state.go('searchByGeneName', {
						name : $scope.geneNameText
					});
				}
			};

			$scope.findByBiologicalProcessName = function() {
				if ($scope.biologicalProcessNameText) {
					$state.go('searchByBiologicalProcessName', {
						name : $scope.biologicalProcessNameText
					});
				}
			};

			$scope.findByProteinId = function() {
				if ($scope.proteinIdText) {
					$state.go('searchByProteinId', {
						id : $scope.proteinIdText
					});
				}
			};

			$scope.findByPubmedDocumentId = function() {
				if ($scope.pubmedIdText) {
					$state.go('searchByPubmedDocumentId', {
						id : $scope.pubmedIdText
					});
				}
			};

			$scope.findByTranscriptId = function() {
				if ($scope.transcriptIdText) {
					$state.go('searchByTranscriptId', {
						id : $scope.transcriptIdText
					});
				}
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
		searchFields: [{
			key: "id",
			value: $stateParams.id
		}]
	};
	$scope.sortOptions = [ {value: "id", label: "Id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Mirna, elements : 'mirna'}));
	
}).controller('SearchByPhenotypeNameController', function($scope, $controller, $stateParams, Disease) {
	$scope.search = {
		searchFunction: "name",
		searchFields: [{
			key: "name",
			value: $stateParams.name
		}]
	};
	$scope.sortOptions = [ {value: "name", label: "Name"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Disease, elements : 'disease'}));
	
}).controller('SearchByEnvironmentalFactorNameController', function($scope, $controller, $stateParams, EnvironmentalFactor) {
	$scope.search = {
		searchFunction: "name",
		searchFields: [{
			key: "name",
			value: $stateParams.name
		}]
	};
	$scope.sortOptions = [ {value: "name", label: "Name"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : EnvironmentalFactor, elements : 'environmental_factor'}));

}).controller('SearchByGeneNameController', function($scope, $controller, $stateParams, Gene) {
	$scope.search = {
		searchFunction: "name",
		searchFields: [{
			key: "name",
			value: $stateParams.name
		}]
	};
	$scope.sortOptions = [ {value: "name", label: "Name"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Gene, elements : 'gene'}));

	
}).controller('SearchByProteinIdController', function($scope, $controller, $stateParams, Protein) {
	$scope.search = {
		searchFunction: "id",
		searchFields: [{
			key: "id",
			value: $stateParams.id
		}]
	};
	$scope.sortOptions = [ {value: "id", label: "id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Protein, elements : 'protein'}));
	
}).controller('SearchByPubmedDocumentIdController', function($scope, $controller, $stateParams, PubmedDocument) {
	$scope.search = {
		searchFunction: "id",
		searchFields: [{
			key: "id",
			value: $stateParams.id
		}]
	};
	$scope.sortOptions = [ {value: "id", label: "id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : PubmedDocument, elements : 'pubmed_document'}));
	
}).controller('SearchByTranscriptIdController', function($scope, $controller, $stateParams, Transcript) {
	$scope.search = {
		searchFunction: "id",
		searchFields: [{
			key: "id",
			value: $stateParams.id
		}]
	};
	$scope.sortOptions = [ {value: "id", label: "id"} ];
	angular.extend(this, $controller('PagedListController',
			{$scope: $scope, Object : Transcript, elements : 'transcript'}));
	
}).controller('MirnaViewController',
		function($scope, $controller, $stateParams, Object, complementary, PubmedDocument, ExpressionData) {
	
	Object.get({ id: $stateParams.id }, function(response) {
		$scope.mirna = response ? response : {};
		if ($scope.mirna) {
			
			if (complementary) {
				Object.getLink({ id: $stateParams.id, link: complementary }, function(response) {
					$scope.mirna[complementary] = response ? response[complementary] : {};
				});
			}
			
			$scope.mirna.pubmed_documents = {};
			$scope.mirna.pubmed_documents.pageSize = 10;
			$scope.mirna.pubmed_documents.search = {
					searchFunction: "mirna_pk",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.mirna.pubmed_documents, Object : PubmedDocument, elements : 'pubmed_document'}));
			
			$scope.expression_datas = {};
			$scope.expression_datas.pageSize = 10;
			$scope.expression_datas.projection = "inlineDisease";
			$scope.expression_datas.search = {
					searchFunction: "mirna_pk",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.expression_datas, Object : ExpressionData, elements : 'expression_data'}));
		}
	});
	
}).controller('MatureViewController', function($scope, $controller, $stateParams, Mature) {
	
	angular.extend(this, $controller('MirnaViewController',
			{$scope: $scope, Object : Mature, complementary : 'hairpins'}));
	
}).controller('HairpinViewController', function($scope, $controller, $stateParams, Hairpin) {
	
	angular.extend(this, $controller('MirnaViewController',
			{$scope: $scope, Object : Hairpin, complementary : 'matures'}));
	
}).controller('DeadMirnaViewController', function($scope, $controller, $stateParams, DeadMirna) {
	
	angular.extend(this, $controller('MirnaViewController',
			{$scope: $scope, Object : DeadMirna, complementary : null}));
	
}).controller('PhenotypeViewController', function($scope, $controller, $stateParams, Disease, Mirna, ExpressionData, SNP) {
	
	Disease.get({ id: $stateParams.id }, function(response) {
		$scope.disease = response ? response : {};
		if ($scope.disease) {
			
			$scope.disease.related_mirnas = {};
			$scope.disease.related_mirnas.pageSize = 50;
			$scope.disease.related_mirnas.search = {
					searchFunction: "related_to_disease",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.disease.related_mirnas, Object : Mirna, elements : 'mirna'}));
			
			$scope.snps = {};
			$scope.snps.pageSize = 10;
			$scope.snps.search = {
					searchFunction: "disease_pk",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.snps, Object : SNP, elements : 'snp'}));
		}
		
		$scope.filterByMirna = function(mirna) {
			$scope.filtered_mirna = mirna;
			$scope.expression_datas = {};
			$scope.expression_datas.pageSize = 5;
			$scope.expression_datas.search = {
					searchFunction: "mirna_pk_and_disease_pk",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "disease_pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.expression_datas, Object : ExpressionData, elements : 'expression_data'}));
		}
		
	});
	
}).controller('EnvironmentalFactorViewController', function($scope, $controller, $stateParams, EnvironmentalFactor, Mirna, ExpressionData) {
	
	EnvironmentalFactor.get({ id: $stateParams.id }, function(response) {
		$scope.environmental_factor = response ? response : {};
		if ($scope.environmental_factor) {
			
			$scope.environmental_factor.related_mirnas = {};
			$scope.environmental_factor.related_mirnas.pageSize = 50;
			$scope.environmental_factor.related_mirnas.search = {
					searchFunction: "related_to_environmental_factor",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.environmental_factor.related_mirnas, Object : Mirna, elements : 'mirna'}));
		}
		
		$scope.filterByMirna = function(mirna) {
			$scope.filtered_mirna = mirna;
			$scope.expression_datas = {};
			$scope.expression_datas.pageSize = 5;
			$scope.expression_datas.search = {
					searchFunction: "mirna_pk_and_environmental_factor_pk",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "environmental_factor_pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.expression_datas, Object : ExpressionData, elements : 'expression_data'}));
		}
		
	});
	
}).controller('GeneViewController', function($scope, $controller, $stateParams, Gene, Mirna, InteractionData) {
	
	Gene.get({ id: $stateParams.id }, function(response) {
		$scope.gene = response ? response : {};
		if ($scope.gene) {
			
			$scope.gene.related_mirnas = {};
			$scope.gene.related_mirnas.pageSize = 50;
			$scope.gene.related_mirnas.search = {
					searchFunction: "related_to_gene",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.gene.related_mirnas, Object : Mirna, elements : 'mirna'}));
		}
		
		$scope.filterByMirna = function(mirna) {
			$scope.filtered_mirna = mirna;
			$scope.interaction_datas = {};
			$scope.interaction_datas.pageSize = 5;
			$scope.interaction_datas.search = {
					searchFunction: "mirna_pk_and_gene_pk",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "gene_pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.interaction_datas, Object : InteractionData, elements : 'interaction_data'}));
		}
		
	});

}).controller('PubmedDocumentViewController', function($scope, $controller, $stateParams, PubmedDocument, Mirna, ExpressionData) {
	
	PubmedDocument.get({ id: $stateParams.id }, function(response) {
		$scope.pubmed_document = response ? response : {};
		if ($scope.pubmed_document) {
			
			$scope.pubmed_document.related_mirnas = {};
			$scope.pubmed_document.related_mirnas.pageSize = 50;
			$scope.pubmed_document.related_mirnas.search = {
					searchFunction: "related_to_pubmed_document",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.pubmed_document.related_mirnas, Object : Mirna, elements : 'mirna'}));
		}
		
		$scope.filterByMirna = function(mirna) {
			$scope.filtered_mirna = mirna;
			$scope.expression_datas = {};
			$scope.expression_datas.pageSize = 5;
			$scope.expression_datas.search = {
					searchFunction: "mirna_pk_and_pubmed_document_pk",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "pubmed_document_pk",
						value: $stateParams.id
					}]
				};
			angular.extend(this, $controller('PagedListController',
					{$scope: $scope.expression_datas, Object : ExpressionData, elements : 'expression_data'}));
		}
		
	});	
	
// Hasta aquí	
	
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
	
	$scope.findByEnvironmentalFactorName = function() {
		if ($scope.environmentalFactorNameText) {
			$state.go('searchByEnvironmentalFactorName', {name: $scope.environmentalFactorNameText});
		}
	};
	
	$scope.findByGeneName = function() {
		if ($scope.geneNameText) {
			$state.go('searchByGeneName', {name: $scope.geneNameText});
		}
	};
	
	$scope.findByProteinId = function() {
		if ($scope.proteinIdText) {
			$state.go('searchByProteinId', {id: $scope.proteinIdText});
		}
	};
	
	$scope.findByPubmedDocumentId = function() {
		if ($scope.pubmedIdText) {
			$state.go('searchByPubmedDocumentId', {id: $scope.pubmedIdText});
		}
	};
	
	$scope.findByTranscriptId = function() {
		if ($scope.transcriptIdText) {
			$state.go('searchByTranscriptId', {id: $scope.transcriptIdText});
		}
	};
	
});
