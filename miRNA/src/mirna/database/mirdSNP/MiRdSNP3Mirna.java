package mirna.database.mirdSNP;
import java.sql.ResultSet;
import org.hibernate.Session;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3Mirna extends MiRdSNP3 {

	public MiRdSNP3Mirna() throws MiRnaException {
		super(); 
		super.selectQuery = "SELECT distinct t1.miR FROM mirna_raw.miRdSNP3"
				+ " t1 LEFT JOIN mirna.mirna t2 ON t1.microrna = t2.name"
				+ " where t1.pk is not null and t2.pk is null;";

	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String miRNAString = rs.getString("miR");
		MiRna miRna = new MiRna();
		miRna.setName(miRNAString);
		session.save(miRna); 
		
	}

}