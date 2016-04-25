package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.Disease;

@RepositoryRestResource(collectionResourceRel = "disease", path = "disease")
public interface DiseaseRepository extends PagingAndSortingRepository<Disease, Integer> {
	
	@RestResource(path = "name")
	public Page<DiseaseRepository> findByNameContaining(
			@Param("name")String name, Pageable pageable);
	
}
