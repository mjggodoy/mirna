package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.BiologicalProcess;
import mirna.api.model.MiRna;
import mirna.api.model.projection.OnlyName;


@RepositoryRestResource(collectionResourceRel = "biological_process", path = "biological_process", excerptProjection = OnlyName.class)

public interface BiologicalProcessRepository extends PagingAndSortingRepository<BiologicalProcess, Integer> {

	@RestResource(path = "name")
	public Page<BiologicalProcessRepository> findByNameContaining(@Param("name")String name, Pageable pageable);
	
	
	
}
