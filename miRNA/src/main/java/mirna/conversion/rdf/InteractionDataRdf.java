package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.InteractionData;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;

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
		
		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		if (interactionData.getCellular_line()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"cellular_line"),
			interactionData.getCellular_line());
		
		if (interactionData.getFeature()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"feature"),
			interactionData.getFeature());
		
		if (interactionData.getMethod()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"method"),
			interactionData.getMethod());
		
		if (interactionData.getMiTG_score()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"MiTG_score"),
			interactionData.getMiTG_score());
		
		if (interactionData.getPhase()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"phase"),
			interactionData.getPhase());
		
		if (interactionData.getProvenance()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"provenance"),
			interactionData.getProvenance());
		
		if (interactionData.getPvalue_log()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"pvalue_log"),
			interactionData.getPvalue_log());
		
		if (interactionData.getRank()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"rank"),
			interactionData.getRank());
		
		if (interactionData.getReference()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"reference"),
			interactionData.getReference());
		
		if (interactionData.getScore()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"score"),
			interactionData.getScore());
		
		if (interactionData.getType()!=null)
			subject.addProperty(
			VCARD.ADRPROPERTIES.getModel().createProperty(ns+"type"),
			interactionData.getType());
		
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
	
	public static void main(String[] args) {
		
		InteractionDataRdf interactionrdf = new InteractionDataRdf();
		interactionrdf.execute();
		HibernateUtil.getSessionFactory();
		
		
	}

}
