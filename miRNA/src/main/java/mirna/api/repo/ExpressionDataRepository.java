package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.ExpressionData;
import mirna.api.model.projection.ExpressionDataInfo;

@RepositoryRestResource(collectionResourceRel = "expression_data", path = "expression_data", excerptProjection=ExpressionDataInfo.class)
public interface ExpressionDataRepository extends PagingAndSortingRepository<ExpressionData, Integer> {
	
	public Page<ExpressionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);
	
	@RestResource(path = "mirna_pk")
	public Page<ExpressionData> findByMirnas_Pk(@Param("pk")int pk, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_disease_pk")
	@Query("select distinct b from  MirnaHasExpressionData a, ExpressionData b " +
			" where a.mirnaPk=:mirna_pk and b.disease.pk=:disease_pk and a.expressionDataPk=b.pk and"
		    + " (b.description is not null" 
			+ " or b.evidence is not null)")
	public Page<ExpressionData> findByMirnas_PkAndDiseasePk(@Param("mirna_pk")int mirnaPk, @Param("disease_pk")int diseasePk, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_environmental_factor_pk")
	public Page<ExpressionData> findByMirnas_PkAndEnvironmentalFactorPk(@Param("mirna_pk")int mirnaPk, @Param("environmental_factor_pk")int environmentalFactorPk, Pageable pageable);

	@RestResource(path = "related_to_pubmed_document_pk")
    public Page<ExpressionData> findByPubmedDocuments_Pk(@Param("pk")int pk, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_pubmed_document_pk")
    public Page<ExpressionData> findByMirnas_PkAndPubmedDocuments_Pk(@Param("mirna_pk")int mirnaPk, @Param("pubmed_document_pk")int pubmedDocumentPk, Pageable pageable);
	
	
	@Query("select distinct c from SnpHasGene a, SnpHasDisease b, ExpressionData c, "
			+ "InteractionData d, Disease e where "
			+ "a.snpPk = :pk and a.snpPk = b.snpPk and b.diseasePk = c.disease.pk "
			+ "and a.genePk = d.gene.pk and c.interactionData.pk = d.pk "
			+ "and c.disease.pk = e.pk")	
	@RestResource(path = "disease_related_to_snp")
	public Page<ExpressionData> findDiseasesBySNP(@Param("pk")int snpPk, Pageable pageable);
	
	
}
