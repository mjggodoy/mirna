package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.EnvironmentalFactor;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import java.io.OutputStream;

public class EnvironmentFactorRdf extends TableRdf {

	private String className = ns + "EnvironmentalFactor";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		EnvironmentalFactor ef = (EnvironmentalFactor) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"environmentalFactor"+ef.getPk());
		
		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		if (ef.getName()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
					ef.getName());
	

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from EnvironmentalFactor d";
	}

	@Override
	protected String getNtFile() {
		return "environmentalFactor.nt";
	}

	@Override
	protected String getName() {
		return "EnvrionmentalFactor";
	}
	
	
	public static void main(String[] args) {
		
		EnvironmentFactorRdf efrdf = new EnvironmentFactorRdf();
		efrdf.execute();
		HibernateUtil.closeSessionFactory();
		
	}

}

