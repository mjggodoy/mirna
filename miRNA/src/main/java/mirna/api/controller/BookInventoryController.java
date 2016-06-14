package mirna.api.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Esteban on 14/06/2016.
 */
@RestController
public class BookInventoryController {

	@RequestMapping(value = "/api/books_pua", method = RequestMethod.GET, produces={"application/hal+json"})
	ResponseEntity<Resources<Resource>> index() {

		Link booksLink = linkTo(BookInventoryController.class).slash("/books").withSelfRel();

		// Build a list of resources and add the book.
		List<Resource> bookResources = new ArrayList<>();
		bookResources.add(buildABookResource("HATEOAS Wrapped Resource."));
		bookResources.add(buildABookResource("Another HATEOAS Wrapped Resource."));

		//Wrap your resources in a Resources object.
		Resources<Resource> resourceList = new Resources<Resource>(bookResources, booksLink);

		return new ResponseEntity<Resources<Resource>>(resourceList, HttpStatus.OK);

	}

	private Resource<HypermediaBook> buildABookResource(String title) {
		HypermediaBook book = new HypermediaBook();
		book.setTitle(title);
		book.setIsbn("XXX");
		book.setPublisher("PUA EDITOR");
		book.setBookId("123");

		// Provide a link to lookup book. Method not provided in this example.
		Link bookLink = linkTo(BookInventoryController.class).slash("/book").slash(book.bookId).withSelfRel();
		return new Resource<HypermediaBook>(book, bookLink.expand(book.bookId));
	}

}
