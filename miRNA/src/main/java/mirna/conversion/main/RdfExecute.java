package mirna.conversion.main;

import mirna.conversion.rdf.BiologicalProcessRdf;
import mirna.conversion.rdf.ExpressionDataRdf;
import mirna.conversion.rdf.InteractionDataRdf;
import mirna.conversion.rdf.PhenotypeRdf;
import mirna.integration.utils.HibernateUtil;

/**
 * Created by Esteban on 17/05/2016.
 */
public class RdfExecute {
	public static void main(String[] args) {
//		(new InteractionDataRdf()).execute();
		(new ExpressionDataRdf()).execute();
//		(new PhenotypeRdf()).execute();
//		(new BiologicalProcessRdf()).execute();
		HibernateUtil.closeSessionFactory();
	}
}
