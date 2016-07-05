package mirna.api.controller;

import mirna.lucene.LuceneIndex;
import mirna.lucene.model.LuceneElement;
import mirna.lucene.model.LuceneResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Esteban on 06/06/2016.
 */
@Controller
public class FullSearchController {

	@RequestMapping(value = "/api/global/search/all", method = RequestMethod.GET, produces={"application/hal+json"})
	public ResponseEntity<PagedResources<Resource>> index(
			@RequestParam(value = "term") String term, Pageable pageable, PagedResourcesAssembler assembler) {

		int page = pageable.getPageNumber();
		int pageSize =  pageable.getPageSize();

		LuceneIndex luceneIndex = new LuceneIndex();
		LuceneResult result = luceneIndex.search(term, pageSize, page);

		Page<LuceneElement> results = new PageImpl<>(result.getElements(), pageable, result.getTotalResults());

		PagedResources<Resource> resourceList =	assembler.toResource(
				results,
				linkTo(methodOn(FullSearchController.class).index(term,
						pageable,
						assembler)).withSelfRel());

		return new ResponseEntity<>(resourceList, HttpStatus.OK);

	}

}
