package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

import mirna.integration.beans.SNP;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;


public class SNPrdf extends TableRdf {

	//	protected String ns = "http://khaos.uma.es/imirna/";
	private String className = ns + "SNP";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		SNP snp = (SNP) modelClass;

		Model model= ModelFactory.createDefaultModel(); //create default model

		//protected String resourcePrefix = ns + "resource/";
		
		Resource subject =
				model.createResource(resourcePrefix+"SNP"+snp.getPk());
		
		Resource snpClass = model.createResource(className);
		subject.addProperty(RDF.type, snpClass);

		
		if (snp.getSnp_id() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"id"),
					snp.getSnp_id());
		
		if (snp.getArticle_date() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"article_date"),
					snp.getArticle_date());
		
		if (snp.getChromosome() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"chromosome"),
					snp.getChromosome());
		
		if (snp.getOrientation() !=null)
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"orientation"),
					snp.getOrientation());
		

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}



	@Override
	protected String getQuery() {
		return "from SNP d";
	}


	@Override
	protected String getNtFile() {
		return "snp.nt";
	}

	@Override
	protected String getName() {
		return "SNP";
	}
	
	
	/*public static void main(String[] args) {
		
		SNPrdf snprdf = new SNPrdf();
		snprdf.execute();
		HibernateUtil.closeSessionFactory();

	
	}*/

}

