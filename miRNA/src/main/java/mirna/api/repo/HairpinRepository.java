package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.Hairpin;
import mirna.api.model.projection.MiRnaBasicInfo;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "hairpin", excerptProjection=MiRnaBasicInfo.class)
public interface HairpinRepository extends PagingAndSortingRepository<Hairpin, Integer> { }
