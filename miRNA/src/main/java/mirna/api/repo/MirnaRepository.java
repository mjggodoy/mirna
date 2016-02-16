package mirna.api.repo;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mirna.api.model.MiRna;

@RepositoryRestResource(collectionResourceRel = "mirna", path = "mirna")
public interface MirnaRepository extends PagingAndSortingRepository<MiRna, Integer> {
	
	@RestResource(path = "id")
	public Page<MiRna> findByIdContainingIgnoreCase(@Param("id")String id, Pageable pageable);
	
	@Query("SELECT a from MiRna a, MirnaPkTranslation b, ExpressionData c where c.mirnaPk=b.oldPk and b.newPk=a.pk and c.disease.pk=:key")	
	@RestResource(path = "related_to_disease")
	public Set<MiRna> findMirnasRelatedToDisease(@Param("key")int pk);
	
}
