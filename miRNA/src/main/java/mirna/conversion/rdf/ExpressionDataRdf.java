package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.Disease;
import mirna.integration.beans.ExpressionData;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 19/05/2016.
 */

public class ExpressionDataRdf extends TableRdf {

	private String className = ns + "ExpressionData";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		ExpressionData expressionData = (ExpressionData) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"expressionData"+expressionData.getPk());
		
		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		
		if (expressionData.getCellularLine()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"cellular_line"),
					expressionData.getCellularLine());
		
		if (expressionData.getCondition() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"condition"),
					expressionData.getCondition());
		
		if (expressionData.getDataType()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"data_type"),
					expressionData.getDataType());
		
		if (expressionData.getDescription() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"description"),
					expressionData.getDescription());
		
		if (expressionData.getDifferentExpressionLocation() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"expression_location"),
					expressionData.getDifferentExpressionLocation());
		
		if (expressionData.getEvidence() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"evidence"),
				expressionData.getEvidence());
		
		if (expressionData.getFoldchangeMax() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"fold_change_max"),
				expressionData.getFoldchangeMax());
		
		if (expressionData.getFoldchangeMin() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"fold_change_min"),
				expressionData.getFoldchangeMin());
		
		if (expressionData.getMethod() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"method"),
				expressionData.getMethod());
		
		if (expressionData.getProvenance() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"provenance"),
				expressionData.getProvenance());
		
		if (expressionData.getProvenanceId() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"provenance_id"),
				expressionData.getProvenanceId());
		
		if (expressionData.getStudyDesign()!=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"study_design"),
				expressionData.getStudyDesign());
		
		if (expressionData.getTitleReference() !=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"title_reference"),
				expressionData.getTitleReference());
		
		if (expressionData.getTreatment()!=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"treatment"),
				expressionData.getTreatment());
		
		if (expressionData.getYear()!=null)
			subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"year"),
				expressionData.getYear());
	
		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from ExpressionData ed";
	}

	@Override
	protected String getNtFile() {
		return "expression_data.nt";
	}

	@Override
	protected String getName() {
		return "ExpressionData";
	}
	
	
	public static void main(String[] args) {
		
		ExpressionDataRdf expressiondatardf = new ExpressionDataRdf();
		expressiondatardf.execute();
		HibernateUtil.closeSessionFactory();
		
		
	}

}
