package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.PubmedDocument;


@RepositoryRestResource(collectionResourceRel = "pubmed_document", path = "pubmed_document")

public interface PubmedDocRepository extends PagingAndSortingRepository<PubmedDocument, Integer> {
	
	public Page<PubmedDocument> findBy(@Param("id")String name, Pageable pageable);
	
}
