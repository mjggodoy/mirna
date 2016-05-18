package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.OLDMiRna;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "mirna_old")

public interface OLDMirnaRepository extends PagingAndSortingRepository<OLDMiRna, Integer> {
	
	public Page<OLDMiRna> findByNameContaining(@Param("name")String name, Pageable pageable);


	
}
