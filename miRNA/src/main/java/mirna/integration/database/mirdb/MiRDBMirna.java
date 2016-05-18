package mirna.integration.database.mirdb;

import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.integration.beans.MiRna;
import mirna.integration.exception.MiRnaException;


/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRDBMirna extends MiRDB {

	public MiRDBMirna() throws MiRnaException {
		super();
		super.selectQuery = "SELECT distinct t1.mir" +
				" FROM mirna_raw.miRDB t1" +
				" LEFT JOIN mirna.mirna t2" +
				" ON t1.mir = t2.name" +
				" where" +
				" t1.pk is not null" +
				" and t2.pk is null";
	}

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		
		String mirna_name = rs.getString("mir");
		MiRna miRna = new MiRna();
		miRna.setName(mirna_name);
		session.save(miRna);
			
	}
	
	public static void main(String[] args) throws Exception {

		MiRDBMirna mirdb = new MiRDBMirna();
		mirdb.insertIntoSQLModel();

	}

}