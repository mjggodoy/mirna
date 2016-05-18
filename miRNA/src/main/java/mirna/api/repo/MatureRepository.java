package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.Mature;
import mirna.api.model.projection.MiRnaBasicInfo;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "mature", excerptProjection=MiRnaBasicInfo.class)
public interface MatureRepository extends PagingAndSortingRepository<Mature, Integer> { }
