package mirna.database.mirdSNP;
import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.Gene;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3Gene extends MiRdSNP3 {

	public MiRdSNP3Gene() throws MiRnaException {
		super(); 
		super.selectQuery = "SELECT distinct t1.gene FROM mirna_raw.miRdSNP3"
				+ " t1 LEFT JOIN mirna.gene t2 ON t1.gene = t2.name"
				+ " where t1.pk is not null and t2.pk is null;";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String gene_id = rs.getString("gene");
		System.out.println(gene_id);
		Gene gene = new Gene();
		gene.setName(gene_id);
		session.save(gene);
		
	}
	
	public static void main(String[] args) throws Exception {
		
		MiRdSNP3Gene gene = new MiRdSNP3Gene();
		gene.insertIntoSQLModel();
		
	}

}