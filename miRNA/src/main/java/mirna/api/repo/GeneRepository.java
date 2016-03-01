package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.Gene;
import mirna.api.model.MiRna;
import mirna.api.model.projection.OnlyName;


@RepositoryRestResource(collectionResourceRel = "gene", path = "gene", excerptProjection = OnlyName.class)

public interface GeneRepository extends PagingAndSortingRepository<Gene, Integer> {
	
	@RestResource(path = "name")
	public Page<GeneRepository> findByNameContaining(@Param("name")String name, Pageable pageable);
	
	@Query("SELECT a from Gene a, transcript_has_gene b, Transcript c, transcript_produces_protein d, Protein e "
			+ "where d.protein_pk = e.pk and d.transcript_pk = b.transcript_pk and b.transcript_pk = :pk")	
	@RestResource(path = "related_to_protein")
	public Page<Gene> findGenesRelatedToProtein(@Param("pk")int pk, Pageable pageable);
	
	
}
