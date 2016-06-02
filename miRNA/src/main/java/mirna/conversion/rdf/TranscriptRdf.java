package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.BiologicalProcess;
import mirna.integration.beans.ModelClass;
import mirna.integration.beans.Transcript;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 18/05/2016.
 */
public class TranscriptRdf extends TableRdf {

	private String className = ns + "Transcript";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Transcript transcript = (Transcript) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"transcript"+transcript.getPk());
		subject.addProperty(RDF.type, className);
		
		if (transcript.getTranscriptID()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"transcript_id"),
					transcript.getTranscriptID());
		
		if (transcript.getName()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
					transcript.getName());
		
		if (transcript.getExternalName()!=null)
		subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"external_name"),
				transcript.getExternalName());
		
		if (transcript.getIsoform()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"isoform"),
					transcript.getIsoform());
	
		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Transcript d";
	}

	@Override
	protected String getNtFile() {
		return "transcript.nt";
	}

	@Override
	protected String getName() {
		return "Transcript";
	}
	
	
	public static void main(String[] args) {
		
		TranscriptRdf transcriptRdf = new TranscriptRdf();
		transcriptRdf.execute();
		HibernateUtil.closeSessionFactory();
		
	}

}

