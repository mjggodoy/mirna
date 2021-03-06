package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.InteractionData;
import mirna.api.model.projection.InteractionDataBasicInfo;


@RepositoryRestResource(collectionResourceRel = "interaction_data", path = "interaction_data", excerptProjection = InteractionDataBasicInfo.class)

public interface InteractionDataRepository extends PagingAndSortingRepository<InteractionData, Integer> {
	
	
	public Page<InteractionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_gene_pk")
	public Page<InteractionData> findByMirnas_PkAndGenePk(@Param("mirna_pk")int mirnaPk, @Param("gene_pk")int genePk, Pageable pageable);
	
	@Query("SELECT distinct a from InteractionData a, TranscriptHasGene b, TranscriptProducesProtein c "
			+ "where c.proteinPk=:pk and c.transcriptPk=b.transcriptPk and b.genePk=a.gene")	
	@RestResource(path = "interaction_data_related_to_protein")
	public Page<InteractionData> findGenesRelatedToProtein(@Param("pk")int pk, Pageable pageable);
	
	@Query("SELECT distinct a from InteractionData a, TranscriptHasGene b "
			+ "where b.transcriptPk=:pk and b.genePk=a.gene")	
	@RestResource(path = "transcript_pk")
	public Page<InteractionData> findByTranscriptPk(@Param("pk")int pk, Pageable pageable);
	
	//@Query("SELECT distinct a from InteractionData a, InteractionDataHasBiologicalProcess b "
		//	+ "where b.biologicalProcessPk=:pk and "
		//	+ "b.interactionDataPk = a.pk")	
	@RestResource(path = "biological_process_pk_and_mirna_pk")
	public Page<InteractionData> findByMirnas_PkAndBiologicalProcess_Pk(@Param("mirna_pk")int mirnaPk, @Param("biological_process_pk")int biologicalProcessPk, Pageable pageable);
	
	@Query("SELECT distinct a from InteractionData a, ExpressionData b, MirnaHasExpressionData c "
			+ "where b.disease.pk = :disease_pk and c.expressionDataPk=b.pk and c.mirnaPk=:mirna_pk and "
			+ "b.interactionData.pk = a.pk")	
	@RestResource(path = "interaction_data_related_to_mirna_and_disease")
	public Page<InteractionData> findInteractionDatasrelatedtoMirnaandPhenotype(@Param("disease_pk")int diseasePk, @Param("mirna_pk")int mirnaPk, Pageable pageable);
	
	//Retrieve information from interaction data
	@Query("select distinct d from SnpHasGene a, SnpHasDisease b, ExpressionData c, "
			+ "InteractionData d where "
			+ "a.snpPk = :pk and a.snpPk = b.snpPk and b.diseasePk = c.disease.pk "
			+ "and a.genePk = d.gene.pk and c.interactionData.pk = d.pk")	
	@RestResource(path = "interaction_data_related_to_gene_and_snp")
	public Page<InteractionData> findInteractionDatasrelatedtoGeneandSNP(@Param("pk")int snpPk, Pageable pageable);
	
	

	
	
	
}
