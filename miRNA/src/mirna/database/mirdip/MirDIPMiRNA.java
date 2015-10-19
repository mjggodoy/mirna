package mirna.database.mirdip;

import java.sql.ResultSet;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;
import org.hibernate.Session;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class MirDIPMiRNA extends MirDIP {


	public MirDIPMiRNA() throws MiRnaException {
		super();
		super.selectQuery = "SELECT count(distinct t1.microrna) FROM mirna_raw.mirDIP t1 LEFT JOIN mirna.mirna t2 ON t1.microrna = t2.name where t1.pk is not null and t2.pk is null;";
	}

	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String miRNAString = rs.getString("microrna");
		System.out.println(miRNAString);
		MiRna miRna = new MiRna();
		miRna.setName(miRNAString);
		session.save(miRna); 
		//System.out.println(miRna.getName());*/

	}	

	public static void main(String[] args) throws Exception {

		MirDIPMiRNA mirdip = new MirDIPMiRNA();
		mirdip.insertIntoSQLModel();

	}

}