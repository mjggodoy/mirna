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
		super.selectQuery = "SELECT DISTINCT(gene_symbol) from %s LIMIT 10";
	}

	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String gene_id = rs.getString("gene_symbol");
		System.out.println(gene_id);
		/*Gene gene = new Gene();
		gene.setName(gene_id);
		session.save(gene);*/

	}	

	public static void main(String[] args) throws Exception {

		MirDIPGene mirdip = new MirDIPGene();
		mirdip.insertIntoSQLModel();

	}

}