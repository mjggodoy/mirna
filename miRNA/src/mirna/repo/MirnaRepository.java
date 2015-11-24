package mirna.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import mirna.beans.MiRna;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "mirna")

public interface MirnaRepository extends PagingAndSortingRepository<MiRna, Integer> {
	
	public Page<MiRna> findByNameContaining(@Param("name")String name, Pageable pageable);


	
}
