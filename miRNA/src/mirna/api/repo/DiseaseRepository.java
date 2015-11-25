package mirna.api.repo;

import mirna.api.model.Disease;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "disease", path = "disease")

public interface DiseaseRepository extends PagingAndSortingRepository<Disease, Integer> {
	
	public Page<DiseaseRepository> findByNameContaining(@Param("name")String name, Pageable pageable);


	
}
