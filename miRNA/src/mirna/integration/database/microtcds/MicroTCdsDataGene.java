package mirna.integration.database.microtcds;

import java.sql.ResultSet;
import org.hibernate.Session;

import mirna.integration.exception.MiRnaException;


public class MicroTCdsDataGene extends MicroTCdsData {

	public MicroTCdsDataGene() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT(gene_id) from %s LIMIT 10";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String geneString = rs.getString("gene_id");
		System.out.println(geneString);
		//Gene gene = new Gene();
		//gene.setName(geneString);
		//session.save(gene); 
		//System.out.println(gene.getName());




	}

	public static void main(String[] args) throws Exception {

		MicroTCdsDataGene o = new MicroTCdsDataGene();
		o.insertIntoSQLModel();

	}

}
