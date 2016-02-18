package mirna.api.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import mirna.api.model.Disease;
import mirna.api.model.ExpressionData;
import mirna.api.model.Hairpin;
import mirna.api.model.InteractionData;
import mirna.api.model.Mature;
import mirna.api.model.MiRna;
import mirna.api.model.OLDMiRna;

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
					Hairpin.class,
					Mature.class,
					ExpressionData.class,
					InteractionData.class,
					Disease.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
