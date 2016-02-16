package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.ExpressionData;

@RepositoryRestResource(collectionResourceRel = "expression_data", path = "expression_data")
public interface ExpressionDataRepository extends PagingAndSortingRepository<ExpressionData, Integer> {
	
	public Page<ExpressionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);
	
	@RestResource(path = "mirna_pk")
	public Page<ExpressionData> findByMirnas_Pk(@Param("pk")int pk, Pageable pageable);

}
