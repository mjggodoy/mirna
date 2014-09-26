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
