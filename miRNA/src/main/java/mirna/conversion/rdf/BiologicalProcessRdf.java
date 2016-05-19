package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.BiologicalProcess;
import mirna.integration.beans.Disease;
import mirna.integration.beans.ModelClass;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 18/05/2016.
 */
public class BiologicalProcessRdf extends TableRdf {

	private String className = ns + "BiologicalProcess";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		BiologicalProcess biologicalProcess = (BiologicalProcess) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"biologicalProcess"+biologicalProcess.getPk());
		subject.addProperty(RDF.type, className);
		if (biologicalProcess.getName()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
					biologicalProcess.getName());

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from BiologicalProcess d";
	}

	@Override
	protected String getNtFile() {
		return "biological_process.nt";
	}

	@Override
	protected String getName() {
		return "BiologicalProcess";
	}

}

