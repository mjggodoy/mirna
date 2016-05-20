package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.Disease;
import mirna.integration.beans.ModelClass;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 17/05/2016.
 */
public class PhenotypeRdf extends TableRdf {

	private String className = ns + "Phenotype";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Disease disease = (Disease) modelClass;

		Model model=ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"phenotype"+disease.getPk());
		subject.addProperty(RDF.type, className);
		if (disease.getName()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
					disease.getName());
		if (disease.getDiseaseClass()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"diseaseClass"),
					disease.getDiseaseClass());

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Disease d";
	}

	@Override
	protected String getNtFile() {
		return "phenotype.nt";
	}

	@Override
	protected String getName() {
		return "Phenotype";
	}

}
