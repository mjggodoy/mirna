package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.PubmedDocument;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;


public class PubmeDocumentRdf extends TableRdf {

	//	protected String ns = "http://khaos.uma.es/imirna/";
	private String className = ns + "PubmedDocument";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		PubmedDocument pubmedDocument = (PubmedDocument) modelClass;

		Model model= ModelFactory.createDefaultModel(); //create default model

		//protected String resourcePrefix = ns + "resource/";
		
		Resource subject =
				model.createResource(resourcePrefix+"PubmedDocument"+pubmedDocument.getPk());
		
		Resource pubmedDocumentClass = model.createResource(className);
		subject.addProperty(RDF.type, pubmedDocumentClass);

		
		if (pubmedDocument.getId() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"id"),
					pubmedDocument.getId());
		
		if (pubmedDocument.getDescription() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"description"),
					pubmedDocument.getDescription());
		
		Resource linkClass = model.createResource(pubmedDocument.getResource());
		if (pubmedDocument.getResource() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"resource"),
					linkClass);

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}



	@Override
	protected String getQuery() {
		return "from PubmedDocument d";
	}


	@Override
	protected String getNtFile() {
		return "pubmedDocument.nt";
	}

	@Override
	protected String getName() {
		return "PubmedDocument";
	}
	
	
	public static void main(String[] args) {
		
		PubmeDocumentRdf pubmedDocumentRdf = new PubmeDocumentRdf();
		pubmedDocumentRdf.execute();
		HibernateUtil.closeSessionFactory();

	
	}

}

