CREATE TABLE MiRnaCancer
(
   id               int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
   mirId	        varchar(20) NOT NULL,
   cancer           varchar(40) NOT NULL,
   profile          varchar(20) NOT NULL,
   pubmed_article   varchar(200) NOT NULL
);