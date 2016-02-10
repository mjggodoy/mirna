package mirna.api.repo;

import mirna.api.model.Gene;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "gene", path = "gene")

public interface GeneRepository extends PagingAndSortingRepository<Gene, Integer> {
	
	public Page<GeneRepository> findByNameContaining(@Param("name")String name, Pageable pageable);


	
}
