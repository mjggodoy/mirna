package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.Sequence;


@RepositoryRestResource(collectionResourceRel = "sequence", path = "sequence")

public interface SequenceRepository extends PagingAndSortingRepository<Sequence, Integer> {

}
