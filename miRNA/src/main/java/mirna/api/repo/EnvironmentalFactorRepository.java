package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.EnvironmentalFactor;
import mirna.api.model.projection.OnlyName;

@RepositoryRestResource(collectionResourceRel = "environmental_factor", path = "environmental_factor", excerptProjection = OnlyName.class)
public interface EnvironmentalFactorRepository extends PagingAndSortingRepository<EnvironmentalFactor, Integer> {
	
	@RestResource(path = "name")
	public Page<EnvironmentalFactor> findByNameContaining(@Param("name")String name, Pageable pageable);
	
}
