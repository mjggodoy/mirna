var module = angular.module('mirna.controllers', []);


/****************************************
 * Parent controllers
 ****************************************/

module.controller('PagedListController', function($scope, $state, Object, elements) {

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
			console.log(response);
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

});


/****************************************
 * View controllers
 ****************************************/

module.controller('MirnaViewController',
	function($scope, $controller, $stateParams, Object,	complementary, PubmedDocument, ExpressionData) {
		Object.get({
			id : $stateParams.id
		},
		function(response) {
			$scope.mirna = response ? response : {};
			if ($scope.mirna) {
				if (complementary) {
					Object.getLink({
						id : $stateParams.id,
						link : complementary
					},
					function(response) {
						$scope.mirna[complementary] = response ? response[complementary] : {};
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
				angular.extend(this, $controller('PagedListController',	{
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
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.expression_datas,
					Object : ExpressionData,
					elements : 'expression_data'
				}));
			}
		});
	}
);
		
module.controller('MatureViewController',
	function($scope, $controller, $stateParams, Mature) {
		angular.extend(this, $controller('MirnaViewController', {
			$scope : $scope,
			Object : Mature,
			complementary : 'hairpins'
		}));
	}
);
		
module.controller('HairpinViewController',
	function($scope, $controller, $stateParams, Hairpin) {
		angular.extend(this, $controller('MirnaViewController', {
			$scope : $scope,
			Object : Hairpin,
			complementary : 'matures'
		}));
	}
);

module.controller('DeadMirnaViewController',
	function($scope, $controller, $stateParams, DeadMirna) {
		angular.extend(this, $controller('MirnaViewController', {
			$scope : $scope,
			Object : DeadMirna,
			complementary : null
		}));
	}
);

module.controller('PhenotypeViewController',
	function($scope, $controller, $stateParams, Disease, Mirna, ExpressionData, InteractionData, SNP, PubmedDocument) {
		Disease.get({ id: $stateParams.id }, function(response) {
			$scope.disease = response ? response : {};
			if ($scope.disease) {
				$scope.related_mirnas = {};
				$scope.related_mirnas.pageSize = 48;
				$scope.related_mirnas.search = {
					searchFunction: "related_to_disease",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController',
						{$scope: $scope.related_mirnas, Object : Mirna, elements : 'mirna'}));
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
				$scope.interaction_datas = {};
				$scope.interaction_datas.pageSize = 5;
				$scope.interaction_datas.search = {
					searchFunction: "interaction_data_related_to_mirna_and_disease",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "disease_pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController',
						{$scope: $scope.interaction_datas, Object : InteractionData, elements : 'interaction_data'}));
				$scope.pubmed_documents = {};
				$scope.pubmed_documents.pageSize = 5;
				$scope.pubmed_documents.search = {
					searchFunction: "pubmed_document_related_to_phenotype_and_mirna_and_expression_data",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "disease_pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController',
						{$scope: $scope.pubmed_documents, Object : PubmedDocument, elements : 'pubmed_document'}));
			}
		});
	}
);

module.controller('EnvironmentalFactorViewController',
	function($scope, $controller, $stateParams,	EnvironmentalFactor, Mirna, ExpressionData) {
		EnvironmentalFactor.get({
			id : $stateParams.id
		},
		function(response) {
			$scope.environmental_factor = response ? response : {};
			if ($scope.environmental_factor) {
				$scope.related_mirnas = {};
				$scope.related_mirnas.pageSize = 50;
				$scope.related_mirnas.search = {
					searchFunction : "related_to_environmental_factor",
					searchFields : [ {
						key : "pk",
						value : $stateParams.id
					} ]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.related_mirnas,
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
					searchFields : [ {
						key : "mirna_pk",
						value : mirna.pk
					},
					{
						key : "environmental_factor_pk",
						value : $stateParams.id
					} ]
				};
				angular.extend(this, $controller('PagedListController',	{
					$scope : $scope.expression_datas,
					Object : ExpressionData,
					elements : 'expression_data'
				}));
			}
		});
	}
);

module.controller('GeneViewController',
	function($scope, $controller, $stateParams, Gene, Mirna, InteractionData) {
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
				angular.extend(this, $controller('PagedListController', {
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
					searchFunction: "mirna_pk_and_gene_pk",
					searchFields: [{
						key: "mirna_pk",
						value: mirna.pk
					},{
						key: "gene_pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.interaction_datas,
					Object : InteractionData,
					elements : 'interaction_data'
				}));
			}
		});
	}
);

module.controller('BiologicalProcessViewController',
		function($scope, $controller, $stateParams, BiologicalProcess, Mirna, InteractionData) {
	BiologicalProcess.get(
		{
			id : $stateParams.id
		},
		function(response) {
			$scope.biological_process = response ? response : {};
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
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.biological_process.related_mirnas,
					Object : Mirna,
					elements : 'mirna'
				}));
				
				$scope.filterByMirna = function(mirna) {
					$scope.filtered_mirna = mirna;
					$scope.interaction_datas = {};
					$scope.interaction_datas.pageSize = 5;
					$scope.interaction_datas.search = {
						searchFunction : "biological_process_pk_and_mirna_pk",
						searchFields : [ {
							key : "mirna_pk",
							value : mirna.pk
						}, {
							key : "biological_process_pk",
							value : $stateParams.id
						} ]
							
					};
					angular.extend(this, $controller('PagedListController', {
						$scope : $scope.interaction_datas,
						Object : InteractionData,
						elements : 'interaction_data'
					}));
				};
			}
		}
	);
});

module.controller('ProteinViewController',
	function($scope, $controller, $stateParams, Protein, Transcript, Gene, InteractionData) {
		Protein.get({
			id : $stateParams.id
		}, function(response) {
			$scope.protein = response ? response : {};
			if ($scope.protein) {
				$scope.protein.related_genes = {};
				$scope.protein.related_genes.pageSize = 50;
				$scope.protein.related_genes.search = {
					searchFunction : "related_to_protein",
					searchFields : [ {
						key : "pk",
						value : $stateParams.id
					} ]
				};
				angular.extend(this, $controller('PagedListController',	{
					$scope : $scope.protein.related_genes,
					Object : Gene,
					elements : 'gene'
				}));
				$scope.protein.related_transcript = {};
				$scope.protein.related_transcript.pageSize = 50;
				$scope.protein.related_transcript.search = {
					searchFunction : "related_transcript",
					searchFields : [ {
						key : "pk",
						value : $stateParams.id
					} ]
				};
				angular.extend(this, $controller('PagedListController',	{
					$scope : $scope.protein.related_transcript,
					Object : Transcript,
					elements : 'transcript'
				}));
			}
			$scope.filterByGene = function(protein) {
				$scope.filtered_protein = protein;
				$scope.interaction_datas = {};
				$scope.interaction_datas.pageSize = 5;
				$scope.interaction_datas.search = {
					searchFunction : "interaction_data_related_to_protein",
					searchFields : [ {
						key : "pk",
						value : $stateParams.id
					} ]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.interaction_datas,
					Object : InteractionData,
					elements : 'interaction_data'
				}));
			}
		});
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
			angular.extend(this, $controller('PagedListController', {
				$scope : $scope.interaction_datas,
				Object : InteractionData,
				elements : 'interaction_data'
			}));
		};
	}
);

module.controller('PubmedDocumentViewController',
	function($scope, $controller, $stateParams, PubmedDocument, Mirna, ExpressionData) {
		PubmedDocument.get({ id: $stateParams.id }, function(response) {
			$scope.pubmed_document = response ? response : {};
			if ($scope.pubmed_document) {
				$scope.related_mirnas = {};
				$scope.related_mirnas.pageSize = 12;
				$scope.related_mirnas.search = {
					searchFunction: "related_to_pubmed_document",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.related_mirnas,
					Object : Mirna,
					elements : 'mirna'
				}));
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
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.expression_datas,
					Object : ExpressionData,
					elements : 'expression_data'
				}));
			}
		});	
	}
);

module.controller('SNPViewController',
	function($scope, $controller, $stateParams, SNP, Gene, InteractionData, Disease) {
		SNP.get({ id: $stateParams.id }, function(response) {
			$scope.snp = response ? response : {};
	        if ($scope.snp) {	
	        	$scope.snp.related_genes = {};
				$scope.snp.related_genes.pageSize = 50;
				$scope.snp.related_genes.search = {
					searchFunction: "genes_related_to_snp",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.snp.related_genes,
					Object : Gene,
					elements : 'gene'
				}));
			} 
	        $scope.filterByGene = function(gene) {
				$scope.filtered_gene = gene;
				$scope.interaction_datas = {};
				$scope.interaction_datas.pageSize = 5;
				$scope.interaction_datas.search = {
					searchFunction: "interaction_data_related_to_gene_and_snp",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.interaction_datas,
					Object : InteractionData,
					elements : 'interaction_data'
				}));
				$scope.diseases = {};
				$scope.diseases.pageSize = 5;
				$scope.diseases.search = {
					searchFunction: "disease_related_to_snp",
					searchFields: [{
						key: "pk",
						value: $stateParams.id
					}]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.diseases,
					Object : Disease,
					elements : 'disease'
				}));
	        }
		});	
	}
);

module.controller('TranscriptViewController',
		function($scope, $controller, $stateParams, Transcript, Gene, InteractionData) {
	Transcript.get({
		id : $stateParams.id
	}, function(response) {
		$scope.transcript = response ? response : {};
		if ($scope.transcript) {
			$scope.transcript.related_genes = {};
			$scope.transcript.related_genes.pageSize = 50;
			$scope.transcript.related_genes.search = {
					searchFunction : "related_to_transcript",
					searchFields : [ {
						key : "pk",
						value : $stateParams.id
					} ]
			};
			angular.extend(this, $controller('PagedListController', {
				$scope : $scope.transcript.related_genes,
				Object : Gene,
				elements : 'gene'
			}));
			$scope.filterByGene = function(transcript) {
				$scope.filtered_transcript = transcript;
				$scope.interaction_datas = {};
				$scope.interaction_datas.pageSize = 5;
				$scope.interaction_datas.search = {
						searchFunction : "interaction_data_related_to_transcript",
						searchFields : [ {
							key : "pk",
							value : $stateParams.id
						} ]
				};
				angular.extend(this, $controller('PagedListController', {
					$scope : $scope.interaction_datas,
					Object : InteractionData,
					elements : 'interaction_data'
				}));
			}
		}
	});
});

module.controller('TargetViewController',
		function($scope, $controller, $stateParams, Target, Organism) {
	Target.get({
		id : $stateParams.id
	}, function(response) {
		$scope.target = response ? response : {};
		if ($scope.target) {
			$scope.target.related_organism = {};
			$scope.target.related_organism.pageSize = 50;
			$scope.target.related_organism.search = {
					searchFunction : "related_to_organism",
					searchFields : [ {
						key : "pk",
						value : $stateParams.id
					} ]
			};
			angular.extend(this, $controller('PagedListController', {
				$scope : $scope.target.related_organism,
				Object : Organism,
				elements : 'organism'
			}));
		}
	});
});


/****************************************
 * List controllers
 ****************************************/

module.controller('MirnaListController', function($scope, $controller, Mirna) {
	$scope.sortOptions = [ {
		value : "id",
		label : "Id"
	} ];
	angular.extend(this, $controller('PagedListController', {
		$scope : $scope,
		Object : Mirna,
		elements : 'mirna'
	}));
});

module.controller('MatureListController', function($scope, $controller, Mature) {
	$scope.sortOptions = [ {
		value : "id",
		label : "Id"
	} ];
	angular.extend(this, $controller('PagedListController', {
		$scope : $scope,
		Object : Mature,
		elements : 'mirna'
	}));
});
		
module.controller('HairpinListController', function($scope, $controller, Hairpin) {
	$scope.sortOptions = [ {
		value : "id",
		label : "Id"
	} ];
	angular.extend(this, $controller('PagedListController', {
		$scope : $scope,
		Object : Hairpin,
		elements : 'mirna'
	}));
});

module.controller('PhenotypeListController', function($scope, $controller, Disease) {
	$scope.sortOptions = [ {
		value : "name",
		label : "Name"
	} ];
	angular.extend(this, $controller('PagedListController', {
		$scope : $scope,
		Object : Disease,
		elements : 'disease'
	}));
});


/****************************************
 * Search controllers
 ****************************************/
		
module.controller('SearchByIdController', function($scope, $controller, $stateParams, Mirna) {
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
});

module.controller('SearchByAccController', function($scope, $controller, $stateParams, Mirna) {
	$scope.search = {
		searchFunction: "acc",
		searchFields: [ {
			key: "acc",
			value: $stateParams.acc
		} ]
	};
	$scope.sortOptions = [ {
		value: "id",
		label: "Id"
	} ];
	angular.extend(this, $controller('PagedListController', {
		$scope: $scope,
		Object : Mirna,
		elements : 'mirna'
	}));
});
		
module.controller('SearchByPhenotypeNameController', function($scope, $controller, $stateParams, Disease) {
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
});
		
module.controller('SearchByEnvironmentalFactorNameController', function($scope, $controller, $stateParams, EnvironmentalFactor) {
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
});
		
module.controller('SearchByGeneNameController',	function($scope, $controller, $stateParams, Gene) {
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
});
		
module.controller('SearchByBiologicalProcessNameController',
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
	}
);

module.controller('SearchByProteinIdController',
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
	}
);
		
module.controller('SearchByPubmedDocumentIdController',
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
	}
);

module.controller('SearchBySnpIdController',
	function($scope, $controller, $stateParams, SNP) {
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
			Object : SNP,
			elements : 'snp'
		}));
	}
);
		
module.controller('SearchByTranscriptIdController',
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
	}
);


/****************************************
 * Main pages controllers
 ****************************************/

module.controller('HomeController', function($scope, $state) {
	$scope.quickSearchText = 'hsa-let-7a';
	$scope.quickSearch = function() {
		if ($scope.quickSearchText) {
			$state.go('searchById', {
				id : $scope.quickSearchText
			});
		}
	};
});

module.controller('SearchController', function($scope, $state) {

	$scope.findById = function() {
		if ($scope.idText) {
			$state.go('searchById', {
				id : $scope.idText
			});
		}
	};
	
	$scope.findByAcc = function() {
		if ($scope.accText) {
			$state.go('searchByAcc', {
				acc: $scope.accText
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
	
	$scope.findBySnpId = function() {
		if ($scope.SNPIdText) {
			$state.go('searchBySNPId', {
				id : $scope.SNPIdText
			});
		}
	};
  
});
