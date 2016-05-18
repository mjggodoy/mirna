package mirna.integration.database.reptar;

import java.sql.ResultSet;

import mirna.integration.beans.MiRna;
import mirna.integration.exception.MiRnaException;

import org.hibernate.Session;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class RepTarmiRNAM extends RepTar_mouse {


	public RepTarmiRNAM() throws MiRnaException {
		super();
		super.selectQuery = "SELECT distinct t1.mirna"+
				" FROM mirna_raw.repTar_mouse t1" +
				" LEFT JOIN mirna.mirna t2" +
				" ON t1.mirna = t2.name" +
				" where" +
				" t1.pk is not null" +
				" and t2.pk is null;";
	}
	
	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String mirna_name = rs.getString("mirna");
		MiRna mirna = new MiRna();
		mirna.setName(mirna_name);
		session.save(mirna);

	}	

	public static void main(String[] args) throws Exception {

		RepTarmiRNAM mirdip = new RepTarmiRNAM();
		mirdip.insertIntoSQLModel();

	}

}