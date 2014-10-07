CREATE TABLE miRCancer
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mirId            varchar(20) NOT NULL,
   cancer           varchar(40) NOT NULL,
   profile          varchar(20) NOT NULL,
   pubmed_article   varchar(200) NOT NULL
);

CREATE TABLE phenomir
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   phenomicid       varchar(20) NOT NULL,
   pmid             varchar(20) NOT NULL,
   disease          varchar(20) NOT NULL,
   diseasesub_id    varchar(20) NOT NULL,
   class            varchar(20) NOT NULL,
   miRNA            varchar(20) NOT NULL,
   accession        varchar(20) NOT NULL,
   expression       varchar(20) NOT NULL,
   foldchangemin    varchar(20) NOT NULL,
   foldchangemax    varchar(20) NOT NULL,
   id               varchar(20) NOT NULL,
   name             varchar(20) NOT NULL,
   method           varchar(20) NOT NULL
);

CREATE TABLE hmdd
(
   pk              int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mir             varchar(20) NOT NULL,
   disease         varchar(80) NOT NULL
);

CREATE TABLE microcosm_homo_sapiens
(
   pk              int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   group_          varchar(20) NOT NULL,
   seq             varchar(20) NOT NULL,
   method          varchar(20) NOT NULL,
   feature         varchar(20) NOT NULL,
   chr             varchar(20) NOT NULL,
   start           varchar(20) NOT NULL,
   end             varchar(20) NOT NULL,
   strand          varchar(20) NOT NULL,
   phase           varchar(20) NOT NULL,
   score           varchar(20) NOT NULL,
   pvalue_og       varchar(20) NOT NULL,
   transcript_id   varchar(20) NOT NULL,
   external_name   varchar(20) NOT NULL
);

CREATE TABLE mir2disease
(
   pk              int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mirna           varchar(20) NOT NULL,
   disease         varchar(80) NOT NULL,
   expression      varchar(80) NOT NULL,
   method          varchar(40) NOT NULL,
   date            varchar(20) NOT NULL,
   reference       varchar(300) NOT NULL
);

CREATE TABLE sm2mir2n
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mirna            varchar(20) NOT NULL,
   mirbase          varchar(20) NOT NULL,
   small_molecule   varchar(80) NOT NULL,
   fda              varchar(20) NOT NULL,
   db               varchar(20) NOT NULL,
   cid              varchar(20) NOT NULL,
   method           varchar(40) NOT NULL,
   species          varchar(40) NOT NULL,
   condition_       varchar(200) NOT NULL,
   pmid             varchar(20) NOT NULL,
   year             varchar(20) NOT NULL,
   reference        varchar(300) NOT NULL,
   support          varchar(500) NOT NULL,
   expression       varchar(20) NOT NULL
);

CREATE TABLE miREnvironment
(
   pk              int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mir             varchar(20) NOT NULL,
   name            varchar(20) NOT NULL,
   name2           varchar(20) NOT NULL,
   name3           varchar(20) NOT NULL,
   disease         varchar(200) NOT NULL,
   enviromentalFactor      varchar(200) NOT NULL,
   treatment       varchar(400) NOT NULL,
   cellularLine    varchar(400) NOT NULL,
   specie          varchar(40) NOT NULL,
   description     varchar(1400) NOT NULL,
   pubmedId        varchar(20) NOT NULL
);

CREATE TABLE miRDB
(
   pk              int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mir             varchar(20) NOT NULL,
   target          varchar(20) NOT NULL,
   score           varchar(20) NOT NULL
);

CREATE TABLE mirDIP
(
   pk              int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   accesionnumber  varchar(20) NOT NULL,
   rank            varchar(20) NOT NULL,
   number_of_sources       varchar(20) NOT NULL,
   provenance      varchar(400) NOT NULL
);

CREATE TABLE tarBase
(
 	pk     int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id varchar(20) NOT NULL,
	id_v4 varchar(20) NOT NULL,
	data_type varchar(20) NOT NULL,
	support_type varchar(20) NOT NULL,
	organism varchar(20) NOT NULL,
	miRNA varchar(20) NOT NULL,
	hgnc_symbol varchar(20) NOT NULL,
	gene varchar(40) NOT NULL,
	isoform varchar(20) NOT NULL,
	ensembl varchar(20) NOT NULL,
	chr_loc varchar(40) NOT NULL,
	mre varchar(20) NOT NULL,
	s_s_s varchar(20) NOT NULL,
	i_s varchar(200) NOT NULL,
	d_s varchar(200) NOT NULL,
	validation varchar(20) NOT NULL,
	paper varchar(200) NOT NULL,
	target_seq varchar(200) NOT NULL,
	mirna_seq varchar(40) NOT NULL,
	seq_location varchar(40) NOT NULL,
	pmid varchar(20) NOT NULL,
	kegg varchar(20) NOT NULL,
	protein_type varchar(80) NOT NULL,
	dif_expr_in varchar(80) NOT NULL,
	pathology_or_event varchar(80) NOT NULL,
	mis_regulation varchar(20) NOT NULL,
	gene_expression varchar(200) NOT NULL,
	tumour_involv varchar(200) NOT NULL,
	bib varchar(800) NOT NULL,
	cell_line_used varchar(80) NOT NULL,
	hgnc_id varchar(20) NOT NULL,
	swiss_prot varchar(40) NOT NULL,
	aux varchar(80) NOT NULL
);

CREATE TABLE plant_mirna_mature_mirna
(
 	pk     int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	specie varchar(20) NOT NULL,
	mirna_id varchar(20) NOT NULL,
	sequence varchar(40) NOT NULL
);

CREATE TABLE plant_mirna_stem_loop
(
 	pk     int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	specie varchar(20) NOT NULL,
	mirna_id varchar(20) NOT NULL,
	sequence varchar(1000) NOT NULL
);

CREATE TABLE miRdSNP1
(
 	pk     int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	pubmed_id varchar(20) NOT NULL,
	year varchar(20) NOT NULL,
	month varchar(20) NOT NULL,
	article_date varchar(20) NOT NULL,
	journal varchar(200) NOT NULL,
	title varchar(400) NOT NULL,
	snp_id varchar(120) NOT NULL,
	disease varchar(80) NOT NULL,
	link varchar(80) NOT NULL
);

CREATE TABLE miRdSNP2
(
   pk           int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   gene      	varchar(20) NOT NULL,
   refseq       varchar(20) NOT NULL,
   mirna        varchar(10000) NOT NULL,
   snp_id   	varchar(2000) NOT NULL,
   disease      varchar(200) NOT NULL
);

CREATE TABLE miRdSNP3
(
   pk           int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   gene      	varchar(20) NOT NULL,
   refseq       	varchar(20) NOT NULL,
   miR       	varchar(20) NOT NULL,
   snp   		varchar(20) NOT NULL,
   diseases      varchar(200) NOT NULL,
   distance varchar(20) NOT NULL,
   expConf varchar(20) NOT NULL
);

CREATE TABLE miRdSNP4
(
   pk           int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   gene      	varchar(20) NOT NULL,
   refseq       	varchar(20) NOT NULL,
   miR       	varchar(20) NOT NULL,
   snp   		varchar(20) NOT NULL,
   diseases      varchar(200) NOT NULL,
   distance varchar(20) NOT NULL,
   expConf varchar(20) NOT NULL
);

CREATE TABLE miRdSNP5
(
   pk           int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   chromosome   varchar(20) NOT NULL,
   start       	varchar(20) NOT NULL,
   end       	varchar(20) NOT NULL,
   snp   		varchar(20) NOT NULL,
   disease      varchar(200) NOT NULL
);

CREATE TABLE virmirna1
(
   pk           int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   id_virus   varchar(20) NOT NULL,
   virus_name   varchar(20) NOT NULL,
   virus_full_name       	varchar(80) NOT NULL,
   link_virus       	varchar(80) NOT NULL,
   mirna   		varchar(20) NOT NULL,
   mirna_seq   		varchar(40) NOT NULL,
   length   		varchar(20) NOT NULL,
      gc_proportion   		varchar(20) NOT NULL,
      arm   		varchar(20) NOT NULL,
      pre_mirna   		varchar(20) NOT NULL,
      pre_mirna_seq   		varchar(200) NOT NULL,
      cell_line   		varchar(80) NOT NULL,
      method   		varchar(80) NOT NULL,
      pubmed   		varchar(80) NOT NULL
);

CREATE TABLE virmirna2
(
   pk           int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   avm_id   varchar(20) NOT NULL,
   mirna   varchar(20) NOT NULL,
   mirna_sequence   varchar(40) NOT NULL,
   mirbase_id   varchar(80) NOT NULL,
   specie   varchar(40) NOT NULL,
   virus   varchar(20) NOT NULL,
   virus_full_name   varchar(80) NOT NULL,
   taxonomy   varchar(80) NOT NULL,
   target   varchar(20) NOT NULL,
   uniprot   varchar(20) NOT NULL,
   target_process   varchar(40) NOT NULL,
   method   varchar(80) NOT NULL,
   cell_line   varchar(40) NOT NULL,
   target_sequence   varchar(40) NOT NULL,
   target_region   varchar(20) NOT NULL,
   target_coords   varchar(40) NOT NULL,
   seed_match   varchar(20) NOT NULL,
   target_ref   varchar(20) NOT NULL,
   pubmed_id   varchar(200) NOT NULL
);

