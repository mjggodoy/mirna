package mirna.database.mirdSNP;
import java.sql.ResultSet;

import org.hibernate.Session;
import mirna.beans.SNP;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3SNP extends MiRdSNP3 {

	public MiRdSNP3SNP() throws MiRnaException {
		super(); 
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String snp_name = rs.getString("snp");
		SNP snp = new SNP();
		snp.setSnp_id(snp_name);
		session.save(snp);
		
		
	}

}