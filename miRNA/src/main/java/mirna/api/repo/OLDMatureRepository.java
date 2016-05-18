package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.OLDMature;


@RepositoryRestResource(collectionResourceRel = "mature", path = "mature_old")

public interface OLDMatureRepository extends PagingAndSortingRepository<OLDMature, Integer> {

}
