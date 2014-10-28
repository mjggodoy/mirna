-- BORRADO DE TODO LO ANTERIOR

DROP TABLE mirna.expression_data_involves_mirna;
DROP TABLE mirna.expression_data_related_to_disease;
DROP TABLE mirna.mirna;
DROP TABLE mirna.disease;
DROP TABLE mirna.expression_data;


-- TABLAS DEL MODELO:

CREATE TABLE mirna.mirna
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name             varchar(20) NOT NULL,
   accession_number varchar(20),
   sequence         varchar(20),
   resource         varchar(20)
);

CREATE TABLE mirna.disease
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name             varchar(40) NOT NULL,
   disease_class    varchar(20)
);

CREATE TABLE mirna.expression_data
(
   pk               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   title_reference  varchar(20),
   foldchange_min   varchar(20),
   foldchange_max   varchar(20),
   provenance_id    varchar(20),
   provenance       varchar(20),
   study_design     varchar(20),
   method           varchar(40),
   treatment        varchar(20),
   evidence         varchar(80),
   pubmed_id        varchar(20),
   year             varchar(4),
   description      varchar(1600),
   cellular_line    varchar(20),
   condition_       varchar(20)
);

CREATE TABLE mirna.expression_data_involves_mirna
(
   expression_data   INT(10),
   mirna             INT(10),
   PRIMARY KEY(expression_data, mirna)
);

ALTER TABLE mirna.expression_data_involves_mirna ADD CONSTRAINT `FK_expression_data_involves_mirna_1` FOREIGN KEY (expression_data) REFERENCES mirna.expression_data(pk);
ALTER TABLE mirna.expression_data_involves_mirna ADD CONSTRAINT `FK_expression_data_involves_mirna_2` FOREIGN KEY (mirna) REFERENCES mirna.mirna(pk);

CREATE TABLE mirna.expression_data_related_to_disease
(
   expression_data   INT(10),
   disease           INT(10),
   PRIMARY KEY(expression_data, disease)
);

ALTER TABLE mirna.expression_data_related_to_disease ADD CONSTRAINT `FK_expression_data_related_to_disease_1` FOREIGN KEY (expression_data) REFERENCES mirna.expression_data(pk);
ALTER TABLE mirna.expression_data_related_to_disease ADD CONSTRAINT `FK_expression_data_related_to_disease_2` FOREIGN KEY (disease) REFERENCES mirna.disease(pk);

