package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.InteractionData;
import mirna.api.model.projection.InteractionDataBasicInfo;


@RepositoryRestResource(collectionResourceRel = "interaction_data", path = "interaction_data", excerptProjection = InteractionDataBasicInfo.class)

public interface InteractionDataRepository extends PagingAndSortingRepository<InteractionData, Integer> {
	
	public Page<InteractionData> findByProvenance(@Param("provenance")String provenance, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_gene_pk")
	public Page<InteractionData> findByMirnas_PkAndGenePk(@Param("mirna_pk")int mirnaPk, @Param("gene_pk")int genePk, Pageable pageable);
	
	@RestResource(path = "mirna_pk_and_biological_process_pk")
	public Page<InteractionData> findByMirnas_PkAndBiologicalProcessPk(@Param("mirna_pk")int mirnaPk, Pageable pageable);
	
}
