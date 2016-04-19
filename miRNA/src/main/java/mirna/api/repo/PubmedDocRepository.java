package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.PubmedDocument;

@RepositoryRestResource(collectionResourceRel = "pubmed_document", path = "pubmed_document")
public interface PubmedDocRepository extends PagingAndSortingRepository<PubmedDocument, Integer> {
	
	@RestResource(path = "mirna_pk")
	public Page<PubmedDocument> findByMirnas_Pk(@Param("pk")int pk, Pageable pageable);
	
	@RestResource(path = "id")
	public Page<PubmedDocument> findById(@Param("id")String id, Pageable pageable);
	
	
	@RestResource(path = "pubmed_document_related_to_phenotype_and_mirna")
	@Query("select distinct d from MirnaHasExpressionData a, ExpressionData b,"
			+ " ExpressionDataHasPubmedDocument c, PubmedDocument d "
			+ " where b.disease.pk = :disease_pk and a.mirnaPk=:mirna_pk and "
			+ " a.expressionDataPk=b.pk and b.pk = c.expressionDataPk "
			+ " and c.pubmedDocumentPk= d.pk")
	public Page<PubmedDocument> findByPhenotypeandMirna(@Param("disease_pk")int diseasePk, @Param("mirna_pk")int mirnaPk, Pageable pageable);
	

}
