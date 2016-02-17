package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.ExpressionData;
import mirna.api.model.projection.ExpressionDataInfo;

@RepositoryRestResource(collectionResourceRel = "expression_data", path = "expression_data", excerptProjection=ExpressionDataInfo.class)
public interface ExpressionDataRepository extends PagingAndSortingRepository<ExpressionData, Integer> {
	
	public Page<ExpressionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);
	
	@RestResource(path = "mirna_pk")
	public Page<ExpressionData> findByMirnas_Pk(@Param("pk")int pk, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_disease_pk")
	public Page<ExpressionData> findByMirnas_PkAndDiseasePk(@Param("mirna_pk")int mirnaPk, @Param("disease_pk")int diseasePk, Pageable pageable);

}
