package mirna.conversion.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import mirna.integration.beans.ModelClass;
import mirna.integration.beans.SmallMolecule;
import mirna.integration.utils.HibernateUtil;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import java.io.OutputStream;

public class SmallMoleculeRdf extends TableRdf {

	private String className = ns + "SmallMolecule";

	@Override
	protected void processBean(ModelClass modelClass, OutputStream out) {

		SmallMolecule smallmolecule = (SmallMolecule) modelClass;

		Model model= ModelFactory.createDefaultModel();

		Resource subject =
				model.createResource(resourcePrefix+"smallMolecule"+ smallmolecule.getPk());
		
		Resource complexClass = model.createResource(className);
		subject.addProperty(RDF.type, complexClass);
		
		if (smallmolecule.getDb()!=null && !smallmolecule.getDb().trim().equals("null"))
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"db"),
					smallmolecule.getDb());
		
		if (smallmolecule.getFda()!=null && !smallmolecule.getFda().equals("null"))
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"fda"),
					smallmolecule.getFda());
		
		if (smallmolecule.getCid() !=null && !smallmolecule.getCid().equals("null"))
			subject.addProperty(
					VCARD.ADRPROPERTIES.getModel().createProperty(ns+"cid"),
					smallmolecule.getCid());
	

		RDFDataMgr.write(out, model, RDFFormat.TURTLE_FLAT);

	}

	@Override
	protected String getQuery() {
		return "from SmallMolecule d";
	}

	@Override
	protected String getNtFile() {
		return "SmallMolecule.nt";
	}

	@Override
	protected String getName() {
		return "SmallMolecule";
	}
	
	
	public static void main(String[] args) {
		
		SmallMoleculeRdf smallMoleculeRdf = new SmallMoleculeRdf();
		smallMoleculeRdf.execute();
		HibernateUtil.closeSessionFactory();		
	}

}

