-- HMDD

select
ed.pk,
ed.provenance,
ed.provenance_id,
m.name,
d.name,
pd.id,
ed.description

from
mirna.expression_data as ed,
mirna.mirna as m,
mirna.pubmed_document as pd,
mirna.disease as d,
mirna.expression_data_has_pubmed_document as edhpd,
mirna.mirna_has_pubmed_document as mhpd

where m.pk=ed.mirna_pk
and d.pk=ed.disease_pk
and edhpd.expression_data_pk=ed.pk
and edhpd.pubmed_document_pk=pd.pk
and mhpd.pubmed_document_pk=pd.pk
and mhpd.mirna_pk=m.pk;


-- Phenomir

select
ed.pk,
ed.provenance,
ed.provenance_id,
pd.id,
d.name,
d.disease_class,
m.name,
m.accession_number

from
mirna.expression_data as ed,
mirna.mirna as m,
mirna.pubmed_document as pd,
mirna.disease as d,
mirna.expression_data_has_pubmed_document as edhpd,
mirna.mirna_has_pubmed_document as mhpd

where m.pk=ed.mirna_pk
and d.pk=ed.disease_pk
and edhpd.expression_data_pk=ed.pk
and edhpd.pubmed_document_pk=pd.pk
and mhpd.pubmed_document_pk=pd.pk
and mhpd.mirna_pk=m.pk;


-- VirmiRNA1

SELECT
ed.pk,
ed.provenance_id,
ed.provenance,
ed.cellular_line,
ed.method,
m.pk,
m.name,
m.gc_proportion,
m.length,
seq1.pk,
seq1.sequence,
o.name,
o.resource,
g.pk,
g.arm,
h.pk,
h.name,
seq2.pk,
seq2.sequence

from
mirna.expression_data as ed,
mirna.organism as o,
mirna.pubmed_document as pd,
mirna.mirna as m,
mirna.sequence as seq1,
mirna.sequence as seq2,
mirna.gene as g,
mirna.hairpin as h,
mirna.expression_data_has_pubmed_document as edhpd,
mirna.mirna_has_pubmed_document as mhpd

where m.pk=ed.mirna_pk
and seq1.pk=m.sequence_pk
and seq2.pk=h.sequence_pk
and h.mirna_pk=m.pk
and edhpd.expression_data_pk=ed.pk
and edhpd.pubmed_document_pk=pd.pk
and mhpd.pubmed_document_pk=pd.pk
and mhpd.mirna_pk=m.pk
and g.organism_pk=o.pk
and m.organism_pk=o.pk;