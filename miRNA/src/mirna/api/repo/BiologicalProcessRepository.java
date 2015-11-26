package mirna.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.BiologicalProcess;


@RepositoryRestResource(collectionResourceRel = "biological_process", path = "biological_process")

public interface BiologicalProcessRepository extends PagingAndSortingRepository<BiologicalProcess, Integer> {

}
