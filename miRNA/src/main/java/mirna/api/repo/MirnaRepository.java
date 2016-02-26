package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.BiologicalProcess;
import mirna.api.model.MiRna;

@RepositoryRestResource(collectionResourceRel = "mirna", path = "mirna")
public interface MirnaRepository extends PagingAndSortingRepository<MiRna, Integer> {
	
	@RestResource(path = "id")
	public Page<MiRna> findByIdContainingIgnoreCase(@Param("id")String id, Pageable pageable);
	
	@Query("SELECT a from MiRna a, MirnaPkTranslation b, ExpressionData c where c.mirnaPk=b.oldPk and b.newPk=a.pk and c.disease.pk=:pk")	
	@RestResource(path = "related_to_disease")
	public Page<MiRna> findMirnasRelatedToDisease(@Param("pk")int pk, Pageable pageable);
	
	@Query("SELECT a from MiRna a, MirnaPkTranslation b, ExpressionData c where c.mirnaPk=b.oldPk and b.newPk=a.pk and c.environmentalFactorPk=:pk")	
	@RestResource(path = "related_to_environmental_factor")
	public Page<MiRna> findMirnasRelatedToEnvironmentalFactor(@Param("pk")int pk, Pageable pageable);
	
	@Query("SELECT a from MiRna a, MirnaPkTranslation b, InteractionData c where c.mirnaPk=b.oldPk and b.newPk=a.pk and c.gene.pk=:pk")	
	@RestResource(path = "related_to_gene")
	public Page<MiRna> findMirnasRelatedToGene(@Param("pk")int pk, Pageable pageable);
	
	@RestResource(path = "biological_process_pk")
	public Page<MiRna> findByBiologicalProcess_Pk(@Param("pk")int pk, Pageable pageable);
	
	@RestResource(path = "related_to_pubmed_document")
    public Page<MiRna> findByPubmedDocuments_Pk(@Param("pk")int pk, Pageable pageable);
	
//	@Query("SELECT a from MiRna a, MirnaPkTranslation b, PubmedDocument c where c.mirnaPk=b.oldPk and b.newPk=a.pk and c.pubmedDocument.pk=:pk")	
//	@RestResource(path = "related_to_pubmed_document")
//	public Page<MiRna> findMirnasRelatedToPubmedDocument(@Param("pk")int pk, Pageable pageable);

	
	
	

	
}
