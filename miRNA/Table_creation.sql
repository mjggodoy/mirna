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

CREATE TABLE miREnvironment(


 id nt(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 mir			varchar(20) NOT NULL,
 name       	varchar(20) NOT NULL,
 disease		varchar(20) NOT NULL,
 enviromenentalFactor		varchar(20) NOT NULL,
 treatment		varchar(20) NOT NULL,
 cellularLine	varchar(20) NOT NULL,
 specie			varchar(20) NOT NULL,
 description	varchar(20) NOT NULL,
 pubmedId		varchar(20) NOT NULL
 
)