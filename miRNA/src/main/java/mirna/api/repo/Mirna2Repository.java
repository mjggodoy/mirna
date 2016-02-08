package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.MiRna2;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "mirna")

public interface Mirna2Repository extends PagingAndSortingRepository<MiRna2, Integer> {
	
	public Page<MiRna2> findByIdContaining(@Param("id")String id, Pageable pageable);


	
}
