package mirna.database.microtv4;

import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.MiRna;
import mirna.exception.MiRnaException;

public class MicroTV4Mirna extends MicroTV4 {
	
	public MicroTV4Mirna() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT(mirna) from %s";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		String miRNA = rs.getString("miRNA");
		MiRna miRna = new MiRna();
		miRna.setName(miRNA);
		session.save(miRna);
	}
	
	public static void main(String[] args) throws Exception {
		MicroTV4Mirna microtv4 = new MicroTV4Mirna();
		microtv4.insertIntoSQLModel();
	}

}
