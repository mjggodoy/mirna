package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.PubmedDocument;
import mirna.api.model.SNP;

@RepositoryRestResource(collectionResourceRel = "snp", path = "snp")
public interface SNPRepository extends PagingAndSortingRepository<SNP, Integer> {
	
	@RestResource(path = "id")
	public Page<SNP> findById(@Param("id")String id, Pageable pageable);
	
	@RestResource(path = "disease_pk")
	public Page<SNP> findByDiseases_Pk(@Param("pk")int pk, Pageable pageable);
	
	/*
	@RestResource(path = "pubmed_document_related_to_snp")
	@Query("select distinct b from SnpHasPubmedDocument a, PubmedDocument b "
			+ "where a.snpPk = :snp_pk and a.pubmedDocumentPk = b.pk ")
	public Page<PubmedDocument> findPubmedDocumentBySNP(@Param("snp_pk")int snpPk, Pageable pageable);
	*/
}
