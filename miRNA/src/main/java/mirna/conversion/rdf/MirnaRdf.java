package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.MiRNA2;
import mirna.integration.beans.ModelClass;
import mirna.integration.utils.HibernateUtil;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

/**
 * Created by Esteban on 20/05/2016.
 */
public class MirnaRdf extends TableRdf {

	private String className = ns + "MiRNA";
	private String hairpinClassName = ns + "Hairpin";
	private String matureClassName = ns + "Mature";
	private String deadClassName = ns + "Dead";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		MiRNA2 mirna = (MiRNA2) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"miRNA"+mirna.getPk());

		Resource mirnaClass = model.createResource(className);
		Resource hairpinClass = model.createResource(hairpinClassName);
		Resource matureClass = model.createResource(matureClassName);
		Resource deadClass = model.createResource(deadClassName);
		subject.addProperty(RDF.type, mirnaClass);
		if (mirna.getType().equals("hairpin")) {
			subject.addProperty(RDF.type, hairpinClass);
		} else if (mirna.getType().equals("mature")) {
			subject.addProperty(RDF.type, matureClass);
		} else if (mirna.getType().equals("dead")) {
			subject.addProperty(RDF.type, deadClass);
		}

		if (mirna.getId()!=null)
			subject.addLiteral(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"id"),
					mirna.getId());
		if (mirna.getAccessionNumber()!=null)
			subject.addLiteral(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"accessionNumber"),
					mirna.getAccessionNumber());

		// TODO: PREVIOUS_ID

		// TODO: MIRBASE EXTRA INFO

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from MiRNA2 m";
	}

	@Override
	protected String getNtFile() {
		return "mirna.nt";
	}

	@Override
	protected String getName() {
		return "MiRNA";
	}

	public static void main(String[] args) {
		(new MirnaRdf()).execute();
		HibernateUtil.closeSessionFactory();
	}

}
