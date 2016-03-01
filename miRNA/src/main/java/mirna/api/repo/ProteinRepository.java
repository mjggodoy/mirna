package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.Gene;
import mirna.api.model.Protein;
import mirna.api.model.projection.OnlyName;


@RepositoryRestResource(collectionResourceRel = "protein", path = "protein")

public interface ProteinRepository extends PagingAndSortingRepository<Protein, Integer> {
	
	@RestResource(path = "id")
	public Page<Protein> findById(@Param("id")String id, Pageable pageable);
	
}
