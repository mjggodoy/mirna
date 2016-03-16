package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.MiRna;
import mirna.api.model.Target;
import mirna.api.model.Transcript;
import mirna.api.model.projection.InteractionDataBasicInfo;

@RepositoryRestResource(collectionResourceRel = "target", path = "target")
public interface TargetRepository extends PagingAndSortingRepository<Target, Integer> {
	
	

}
