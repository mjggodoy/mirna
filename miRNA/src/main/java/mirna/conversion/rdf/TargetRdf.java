package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.BiologicalProcess;
import mirna.integration.beans.ModelClass;
import mirna.integration.beans.Target;
import mirna.integration.beans.Transcript;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 18/05/2016.
 */
public class TargetRdf extends TableRdf {

	private String className = ns + "Target";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Target target = (Target) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"target"+target.getPk());
		subject.addProperty(RDF.type, className);
		
		if (target.getBinding_site_end() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"binding_site_end"),
					target.getBinding_site_end());
		
		if (target.getBinding_site_start()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"binding_site_start"),
					target.getBinding_site_start());
		
		if (target.getCds_start()!=null)
		subject.addProperty(
				VCARD.ADRPROPERTIES.getModel().createProperty(ns+"cds_start"),
				target.getCds_start());
		
		if (target.getCds_end()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"cds_end"),
					target.getCds_end());
		
		if (target.getChromosome()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"chromosome"),
					target.getChromosome());
		
		if (target.getCoordinates()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"coordinates"),
					target.getCoordinates());
		
		if (target.getGc_proportion()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"gc_proportion"),
					target.getGc_proportion());
		
		if (target.getGu_proportion()  !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"gu_proportion"),
					target.getGu_proportion());
		
		if (target.getPolarity() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"polarity"),
					target.getPolarity());
		
		if (target.getRegion() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"region"),
					target.getRegion());
		
		if (target.getRepeated_motifs() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"repeated_motifs"),
					target.getRepeated_motifs());
		
		if (target.getSeed_match() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"seed_match"),
					target.getSeed_match());
		
		if (target.getSite_conservation_score() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"site_conservation_score"),
					target.getSite_conservation_score());
		
		if (target.getStrand_end() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"strand_end"),
					target.getStrand_end());
		
		if (target.getStrand_start() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"strand_start"),
					target.getStrand_start());
		
		if (target.getTarget_ref() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"target_ref"),
					target.getTarget_ref());
		
		if (target.getUtr3_conservation_score() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"utr3_conservation_score"),
					target.getUtr3_conservation_score());
	
		if (target.getUtr3_end() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"utr3_end"),
					target.getUtr3_end());

		if (target.getUtr3_start() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"utr3_start"),
					target.getUtr3_start());
	
		
	
		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Target d";
	}

	@Override
	protected String getNtFile() {
		return "target.nt";
	}

	@Override
	protected String getName() {
		return "Target";
	}
	
	
	public static void main(String[] args) {
		
		TargetRdf targetRdf = new TargetRdf();
		targetRdf.execute();
		HibernateUtil.closeSessionFactory();
		
	}

}

