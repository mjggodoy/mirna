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