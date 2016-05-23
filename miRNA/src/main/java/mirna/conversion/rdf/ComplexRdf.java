package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.Complex;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import java.io.OutputStream;

public class ComplexRdf extends TableRdf {

	private String className = ns + "Complex";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Complex complex = (Complex) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"complex"+complex.getPk());
		subject.addProperty(RDF.type, className);
		
		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		if (complex.getBinding_site_pattern()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"binding_site_pattern"),
					complex.getBinding_site_pattern());
		
		if (complex.getMinimal_free_energy()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"minimal_free_energy"),
					complex.getMinimal_free_energy());

		if (complex.getNormalized_minimal_free_energy()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"normalized_minimal_free_energy"),
					complex.getNormalized_minimal_free_energy());

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Complex d";
	}

	@Override
	protected String getNtFile() {
		return "complex.nt";
	}

	@Override
	protected String getName() {
		return "Complex";
	}
	
	
	/*public static void main(String[] args) {
		
		ComplexRdf crdf = new ComplexRdf();
		crdf.execute();
		HibernateUtil.closeSessionFactory();
		
	}*/

}

