package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.DeadMirna;
import mirna.api.model.projection.MiRnaBasicInfo;


@RepositoryRestResource(collectionResourceRel = "mirna", path = "dead_mirna", excerptProjection=MiRnaBasicInfo.class)
public interface DeadMirnaRepository extends PagingAndSortingRepository<DeadMirna, Integer> { }
