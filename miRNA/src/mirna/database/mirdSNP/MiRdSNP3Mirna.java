package mirna.database.mirdSNP;
import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.MiRna;
import mirna.beans.Transcript;
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
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String miRNAString = rs.getString("miR");
		MiRna miRna = new MiRna();
		miRna.setName(miRNAString);
		session.save(miRna); 
		
	}

}