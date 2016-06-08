package mirna.api.controller;

import mirna.lucene.LuceneIndex;
import mirna.lucene.model.LuceneElement;
import mirna.lucene.model.LuceneResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Esteban on 06/06/2016.
 */
@Controller
public class FullSearchController {

	@RequestMapping(value = "/api/global_search", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Page<LuceneElement> search(@Param("term") String term, Pageable pageable) {
		int page = pageable.getPageNumber();
		int pageSize =  pageable.getPageSize();

//		System.out.println("ESTOY EN MI SEARCH... YAHOO!!!!");
//		System.out.println("Busco a "+term);
		LuceneIndex luceneIndex = new LuceneIndex();
		LuceneResult result = luceneIndex.search(term, pageSize, page);

		Page<LuceneElement> results = new PageImpl<>(result.getElements(), pageable, result.getTotalResults());

		return results;
	}


}
