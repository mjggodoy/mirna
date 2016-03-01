package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.MiRna;
import mirna.api.model.Transcript;

@RepositoryRestResource(collectionResourceRel = "transcript", path = "transcript")
public interface TranscriptRepository extends PagingAndSortingRepository<Transcript, Integer> {
	
	@RestResource(path = "id")
	public Page<Transcript> findById(@Param("id")String id, Pageable pageable);
	
	@RestResource(path = "related_to_protein")
    public Page<Transcript> findByProteins_Pk(@Param("pk")int pk, Pageable pageable);
	
}
