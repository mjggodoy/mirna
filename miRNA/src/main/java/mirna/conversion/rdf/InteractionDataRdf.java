package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import mirna.integration.beans.InteractionData;
import mirna.integration.beans.ModelClass;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 19/05/2016.
 */
public class InteractionDataRdf extends TableRdf {

	private String className = ns + "InteractionData";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		InteractionData interactionData = (InteractionData) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"interactionData"+interactionData.getPk());
		subject.addProperty(RDF.type, className);
//		if (disease.getName()!=null)
//			subject.addProperty(
//					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
//					disease.getName());
//		if (disease.getDiseaseClass()!=null)
//			subject.addProperty(
//					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"diseaseClass"),
//					disease.getDiseaseClass());

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from InteractionData id";
	}

	@Override
	protected String getNtFile() {
		return "interaction_data.nt";
	}

	@Override
	protected String getName() {
		return "InteractionData";
	}

}
