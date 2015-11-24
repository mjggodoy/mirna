package mirna.integration.database.mirdSNP;

import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import mirna.integration.database.beans.DiseasePk;
import mirna.integration.exception.MiRnaException;


/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3DiseasePks extends MiRdSNP3 {

	public MiRdSNP3DiseasePks() throws MiRnaException {
		super();
		super.selectQuery = "select pk, TRIM(diseases) from mirna_raw.miRdSNP3_pk";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		String disease_name = rs.getString("TRIM(diseases)");
		int pk = rs.getInt("pk");
		disease_name.trim();
		
		String[] diseaseTokens = StringUtils.splitPreserveAllTokens(disease_name, ",");
		
		for (String token : diseaseTokens) {
			DiseasePk dpk = new DiseasePk();
			dpk.setRowPk(pk);
			dpk.setDiseaseName(token.trim().toLowerCase());
			session.save(dpk);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		MiRdSNP3DiseasePks mirdb = new MiRdSNP3DiseasePks();
		mirdb.insertIntoSQLModel();
	}

}