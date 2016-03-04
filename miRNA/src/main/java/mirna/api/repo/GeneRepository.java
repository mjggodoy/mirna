package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.Gene;
import mirna.api.model.projection.OnlyName;


@RepositoryRestResource(collectionResourceRel = "gene", path = "gene", excerptProjection = OnlyName.class)

public interface GeneRepository extends PagingAndSortingRepository<Gene, Integer> {
	
	@RestResource(path = "name")
	public Page<GeneRepository> findByNameContaining(@Param("name")String name, Pageable pageable);
	
	@Query("SELECT distinct a from Gene a, TranscriptHasGene b, TranscriptProducesProtein c "
			+ "where c.proteinPk=:pk and c.transcriptPk=b.transcriptPk and b.genePk=a.pk")	
	@RestResource(path = "related_to_protein")
	public Page<Gene> findGenesRelatedToProtein(@Param("pk")int pk, Pageable pageable);
	
	@Query("SELECT distinct a from Gene a, TranscriptHasGene b "
			+ "where b.transcriptPk=:pk and b.transcriptPk=b.genePk and b.genePk=a.pk")	
	@RestResource(path = "related_to_transcript")
	public Page<Gene> findGenesRelatedToTranscript(@Param("pk")int pk, Pageable pageable);
	
	
}
