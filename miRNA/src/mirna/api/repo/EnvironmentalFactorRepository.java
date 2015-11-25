package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.EnvironmentalFactor;


@RepositoryRestResource(collectionResourceRel = "environmental_factor", path = "environmentalfactor")

public interface EnvironmentalFactorRepository extends PagingAndSortingRepository<EnvironmentalFactor, Integer> {
	
	public Page<EnvironmentalFactor> findByNameContaining(@Param("name")String name, Pageable pageable);


	
}
