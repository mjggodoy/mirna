package mirna.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.Disease;

@RepositoryRestResource(collectionResourceRel = "disease", path = "disease")
public interface DiseaseRepository extends PagingAndSortingRepository<Disease, Integer> {
	
	@RestResource(path = "name")
	public Page<DiseaseRepository> findByNameContaining(
			@Param("name")String name, Pageable pageable);
	
	@Query("select distinct e from SnpHasGene a, SnpHasDisease b, ExpressionData c, "
			+ "InteractionData d, Disease e where "
			+ "a.snpPk = :pk and a.snpPk = b.snpPk and b.diseasePk = c.disease.pk "
			+ "and a.genePk = d.gene.pk and c.interactionData.pk = d.pk "
			+ "and c.disease.pk = e.pk")	
	@RestResource(path = "disease_related_to_snp")
	public Page<DiseaseRepository> findDiseasesBySNP(@Param("pk")int snpPk, Pageable pageable);
	
}
