package mirna.integration.database.microtv4;

import java.sql.ResultSet;
import org.hibernate.Session;

import mirna.integration.beans.Gene;
import mirna.integration.exception.MiRnaException;

public class MicroTV4Gene extends MicroTV4 {

	public MicroTV4Gene() throws MiRnaException {
		super();
		super.selectQuery = "SELECT t1.gene_id FROM mirna_raw.microtv4 t1 LEFT JOIN mirna.gene t2 ON t1.gene_id = t2.name where t1.pk is not null and t2.pk is null";
	}

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		String gene_id = rs.getString("gene_id");
		Gene gene = new Gene();
		gene.setName(gene_id);
		session.save(gene);
	}

	public static void main(String[] args) throws Exception {
		MicroTV4Gene microtv4 = new MicroTV4Gene();
		microtv4.insertIntoSQLModel();
	}

}
