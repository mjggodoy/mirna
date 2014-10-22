-- BORRADO DE TODO LO ANTERIOR

DROP TABLE data_expression_involves_mirna;
DROP TABLE data_expression_related_to_disease;
DROP TABLE mirna;
DROP TABLE disease;
DROP TABLE data_expression;


-- TABLAS DEL MODELO:

CREATE TABLE mirna
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name             varchar(20) NOT NULL,
   accession_number varchar(20),
   sub_name         varchar(20),
   provenance       varchar(20),
   chromosome       varchar(20),
   version          varchar(20),
   sequence         varchar(20),
   new_name         varchar(20)
);

CREATE TABLE disease
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name             varchar(40) NOT NULL,
   disease_sub      varchar(20),
   disease_class    varchar(20),
   phenomic_id      varchar(20),
   description      varchar(20),
   pubmed_id        varchar(20),
   tissue           varchar(20)
);

CREATE TABLE data_expression
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   expression       varchar(20),
   phenomic_id      varchar(20),
   foldchange_min   varchar(20),
   foldchange_max   varchar(20),
   id               varchar(20),
   study_design     varchar(20),
   method           varchar(20),
   treatment        varchar(20),
   support          varchar(20),
   profile          varchar(20),
   pubmed_id        varchar(20),
   year             varchar(4),
   description      varchar(200),
   cellular_line    varchar(20),
   condition_        varchar(20)
);

CREATE TABLE data_expression_involves_mirna
(
   data_expression   INT(10),
   mirna             INT(10),
   PRIMARY KEY(data_expression, mirna)
);

ALTER TABLE data_expression_involves_mirna ADD CONSTRAINT `FK_data_expression_involves_mirna_1` FOREIGN KEY (data_expression) REFERENCES data_expression(pk);
ALTER TABLE data_expression_involves_mirna ADD CONSTRAINT `FK_data_expression_involves_mirna_2` FOREIGN KEY (mirna) REFERENCES mirna(pk);

CREATE TABLE data_expression_related_to_disease
(
   data_expression   INT(10),
   disease           INT(10),
   PRIMARY KEY(data_expression, disease)
);

ALTER TABLE data_expression_related_to_disease ADD CONSTRAINT `FK_data_expression_related_to_disease_1` FOREIGN KEY (data_expression) REFERENCES data_expression(pk);
ALTER TABLE data_expression_related_to_disease ADD CONSTRAINT `FK_data_expression_related_to_disease_2` FOREIGN KEY (disease) REFERENCES disease(pk);

