package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.BiologicalProcess;
import mirna.integration.beans.Gene;
import mirna.integration.beans.ModelClass;
import mirna.integration.beans.Transcript;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 18/05/2016.
 */
public class GeneRdf extends TableRdf {

	private String className = ns + "Gene";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		Gene gene = (Gene) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"gene"+gene.getPk());
		subject.addProperty(RDF.type, className);
		
		if (gene.getAccessionumber()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"accesion_number"),
					gene.getAccessionumber());
		
		if (gene.getArm()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"arm"),
					gene.getArm());
		
		if (gene.getChromosome() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"chromosome"),
					gene.getChromosome());
		
		if (gene.getDescription() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"description"),
					gene.getDescription());
		
		if (gene.getDistance() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"distance"),
					gene.getDistance());
		
		if (gene.getEnd_strand()!=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"end_strand"),
					gene.getEnd_strand());
		
		if (gene.getEnsembl_id()  !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"ensembl_id"),
					gene.getEnsembl_id());
		
		if (gene.getExpression_site()  !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"expression_site"),
					gene.getExpression_site());
		
		if (gene.getGeneId()  !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"gene_id"),
					gene.getGeneId());
		
		if (gene.getHgnc_id() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"hgnc_id"),
					gene.getHgnc_id());
		
		if (gene.getHgnc_symbol() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"hgnc_symbol"),
					gene.getHgnc_symbol());
		
		if (gene.getKegg_id() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"kegg_id"),
					gene.getKegg_id());
		
		if (gene.getLocation() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"location"),
					gene.getLocation());
		
		if (gene.getName() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"name"),
					gene.getName());
		
		if (gene.getResource() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"resource"),
					gene.getResource());
		
		if (gene.getStart_strand() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"start_strand"),
					gene.getStart_strand());
		
		if (gene.getYear() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"year"),
					gene.getYear());
		
		
		
		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from Gene d";
	}

	@Override
	protected String getNtFile() {
		return "gene.nt";
	}

	@Override
	protected String getName() {
		return "Gene";
	}
	
	
	public static void main(String[] args) {
		
		GeneRdf geneRdf = new GeneRdf();
		geneRdf.execute();
		HibernateUtil.closeSessionFactory();
		
	}

}

