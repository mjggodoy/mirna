package mirna.database.mirdip;

import java.sql.ResultSet;

import mirna.beans.Gene;
import mirna.exception.MiRnaException;

import org.hibernate.Session;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class MirDIPGene extends MirDIP {


	public MirDIPGene() throws MiRnaException {
		super();
		super.selectQuery = "SELECT distinct t1.gene_symbol FROM mirna_raw.mirDIP t1 LEFT JOIN mirna.gene t2 ON t1.gene_symbol = t2.name where t1.pk is not null and t2.pk is null;";
	}
	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String gene_id = rs.getString("gene_symbol");
		System.out.println(gene_id);
		Gene gene = new Gene();
		gene.setName(gene_id);
		session.save(gene);

	}	

	public static void main(String[] args) throws Exception {

		MirDIPGene mirdip = new MirDIPGene();
		mirdip.insertIntoSQLModel();

	}

}