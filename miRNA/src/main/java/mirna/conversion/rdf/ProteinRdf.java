package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.Protein;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;


public class ProteinRdf extends TableRdf {

	private String className = ns + "Protein";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Protein protein = (Protein) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"protein"+protein.getPk());

		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		
		if (protein.getUniprot_id()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"id"),
					protein.getUniprot_id());
		
		if (protein.getType()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"type"),
					protein.getType());

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Protein d";
	}

	@Override
	protected String getNtFile() {
		return "protein.nt";
	}

	@Override
	protected String getName() {
		return "Protein";
	}
	
	
	public static void main(String[] args) {
		
		ProteinRdf proteinrdf = new ProteinRdf();
		proteinrdf.execute();
		HibernateUtil.closeSession();
		
	}

}

