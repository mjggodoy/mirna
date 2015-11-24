package mirna.integration.database.microtcds;

import java.sql.ResultSet;
import org.hibernate.Session;

import mirna.integration.beans.MiRna;
import mirna.integration.exception.MiRnaException;

public class MicroTCdsDataMiRNA extends MicroTCdsData {

	public MicroTCdsDataMiRNA() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT(mirna) from %s";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String miRNAString = rs.getString("miRNA");
		System.out.println(miRNAString);
		MiRna miRna = new MiRna();
		miRna.setName(miRNAString);
		session.save(miRna); 
		System.out.println(miRna.getName());
				
	}
	
	public static void main(String[] args) throws Exception {

		MicroTCdsDataMiRNA o = new MicroTCdsDataMiRNA();
		o.insertIntoSQLModel();

	}

}
