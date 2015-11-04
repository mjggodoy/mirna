package mirna.database.reptar;

import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.Transcript;
import mirna.exception.MiRnaException;


public class Test extends RepTar_human {
	
	public Test() throws MiRnaException {
		super();
		super.selectQuery = "select distinct transcript_pk, gene_pk, gene_accesion FROM mirna_raw.repTar_mouse_pk";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		
		String gene_pk = rs.getString("gene_pk");
		String transcript_pk = rs.getString("transcript_pk");
		String name = rs.getString("gene_accesion");
		Transcript t = new Transcript();
		t.setPk(Integer.valueOf(transcript_pk));
		t.setTranscriptID(name);
		//t.setGeneId(Integer.valueOf(gene_pk));
		session.update(t);
		//System.out.println(gene_pk + " " + transcript_pk);

	}
	
	
	public static void main(String[] args) throws Exception {

		Test test = new Test();
		test.insertIntoSQLModel();

	}
}



	
	