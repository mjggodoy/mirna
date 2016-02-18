package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.Gene;


@RepositoryRestResource(collectionResourceRel = "gene", path = "gene")//, excerptProjection = OnlyName.class)

public interface GeneRepository extends PagingAndSortingRepository<Gene, Integer> {
	
	@RestResource(path = "name")
	public Page<GeneRepository> findByNameContaining(@Param("name")String name, Pageable pageable);
	
}
