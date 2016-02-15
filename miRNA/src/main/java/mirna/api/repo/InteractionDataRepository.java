package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.InteractionData;


@RepositoryRestResource(collectionResourceRel = "interaction_data", path = "interaction_data")

public interface InteractionDataRepository extends PagingAndSortingRepository<InteractionData, Integer> {
	
	//public Page<InteractionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);

}
