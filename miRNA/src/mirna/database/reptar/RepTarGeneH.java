package mirna.database.reptar;

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
public class RepTarGeneH extends RepTar_human {

	public RepTarGeneH() throws MiRnaException {
		super();
		super.selectQuery = "SELECT distinct t1.gene_symbol" +
				" FROM mirna_raw.repTar_mouse t1" +
				" LEFT JOIN mirna.gene t2" +
				" ON t1.gene_symbol = t2.name" +
				" where" +
				" t1.pk is not null" +
				" and t2.pk is null";
	}
	
	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String gene_symbol = rs.getString("gene_symbol");
		Gene gene = new Gene();
		gene.setName(gene_symbol);
		session.save(gene);

	}	

	public static void main(String[] args) throws Exception {

		RepTarGeneH mirdip = new RepTarGeneH();
		mirdip.insertIntoSQLModel();

	}

}