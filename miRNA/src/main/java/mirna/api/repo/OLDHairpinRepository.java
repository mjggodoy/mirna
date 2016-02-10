package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.OLDHairpin;


@RepositoryRestResource(collectionResourceRel = "hairpin", path = "hairpin_old")

public interface OLDHairpinRepository extends PagingAndSortingRepository<OLDHairpin, Integer> {

}
