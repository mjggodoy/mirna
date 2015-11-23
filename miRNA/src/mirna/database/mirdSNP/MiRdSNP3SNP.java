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
		super.selectQuery = "SELECT distinct t1.snp"
				+ " FROM mirna_raw.miRdSNP3 t1"
				+ " LEFT JOIN mirna.snp t2"
				+ " ON t1.snp = t2.snp_id"
				+ " where"
				+ " t1.pk is not null and t2.pk is null;";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String snp_name = rs.getString("snp");
		SNP snp = new SNP();
		snp.setSnp_id(snp_name);
		session.save(snp);
		
		
	}
	
	public static void main(String[] args) throws Exception {
		
		MiRdSNP3SNP snp = new MiRdSNP3SNP();
		snp.insertIntoSQLModel();
		
		
	}

}