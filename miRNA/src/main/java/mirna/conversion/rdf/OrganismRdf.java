package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.Organism;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import java.io.OutputStream;

public class OrganismRdf extends TableRdf {

	private String className = ns + "Organism";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Organism organism = (Organism) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"organism"+organism.getPk());
		
		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		if (organism.getName()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
					organism.getName());
		
		if (organism.getSpecie()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"specie"),
					organism.getSpecie());

		if (organism.getResource()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"resource"),
					organism.getResource());

		if (organism.getShortName()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"short_name"),
					organism.getShortName());

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Organism d";
	}

	@Override
	protected String getNtFile() {
		return "organism.nt";
	}

	@Override
	protected String getName() {
		return "Organism";
	}
	
	
	/*public static void main(String[] args) {
		
		OrganismRdf organismrdf = new OrganismRdf();
		organismrdf.execute();
		HibernateUtil.closeSessionFactory();
		
	}
*/
}

