package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.Organism;
import mirna.api.model.SNP;


@RepositoryRestResource(collectionResourceRel = "organism", path = "organism")

public interface OrganismRepository extends PagingAndSortingRepository<Organism, Integer> {
	
	public Page<Organism> findByName(@Param("name")String name, Pageable pageable);


	
}
