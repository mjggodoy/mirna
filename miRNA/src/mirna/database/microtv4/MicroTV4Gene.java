package mirna.database.microtv4;

import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.Gene;
import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

public class MicroTV4Gene extends MicroTV4 {
	
	public MicroTV4Gene() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT(mirna) from %s";
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
