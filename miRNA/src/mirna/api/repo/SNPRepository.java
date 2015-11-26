package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.SNP;


@RepositoryRestResource(collectionResourceRel = "snp", path = "snp")

public interface SNPRepository extends PagingAndSortingRepository<SNP, Integer> {
	
	public Page<SNP> findBy(@Param("snp_id")String name, Pageable pageable);


	
}
