package mirna.api.config;

import java.net.URI;
import java.net.URISyntaxException;

import mirna.api.model.*;
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
			config.exposeIdsFor(
					OLDMiRna.class,
					MiRna.class,
					MiRnaMinified.class,
					Hairpin.class,
					HairpinMinified.class,
					Mature.class,
					MatureMinified.class,
					ExpressionData.class,
					InteractionData.class,
					Disease.class,
					Protein.class,
					Transcript.class,
					PubmedDocument.class,
					SNP.class,
					Organism.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
