package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.Mature;


@RepositoryRestResource(collectionResourceRel = "mature", path = "mature")

public interface MatureRepository extends PagingAndSortingRepository<Mature, Integer> {

}
