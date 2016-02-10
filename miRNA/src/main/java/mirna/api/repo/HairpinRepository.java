package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.Hairpin;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "hairpin")

public interface HairpinRepository extends PagingAndSortingRepository<Hairpin, Integer> {

}
