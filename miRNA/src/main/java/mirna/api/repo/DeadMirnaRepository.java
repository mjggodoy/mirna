package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.DeadMirna;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "dead_mirna")

public interface DeadMirnaRepository extends PagingAndSortingRepository<DeadMirna, Integer> {

}
