package mirna.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
public class AppConfig extends RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		super.configureRepositoryRestConfiguration(config);
		try {
			config.setBaseUri(new URI("/api"));
			/*
			config.exposeIdsFor(Endpoint.class, SparqlQuery.class,
					Ontology.class,
					ServiceBiocatalogue.class, ServiceBiomoby.class,
					ServiceEmbrace.class, ServiceVarious.class,
					Workflow.class, Processor.class);
					*/
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
