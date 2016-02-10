package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import mirna.api.model.ExpressionData;
import mirna.api.model.OLDMiRna;


@RepositoryRestResource(collectionResourceRel = "expression_data", path = "expression_data")

public interface ExpressionDataRepository extends PagingAndSortingRepository<ExpressionData, Integer> {
	
	public Page<ExpressionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);
	public Page<ExpressionData> findBy(@Param("mirna_pk")String mirna_pk, Pageable pageable);

}
