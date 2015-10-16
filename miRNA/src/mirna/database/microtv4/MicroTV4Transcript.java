package mirna.database.microtv4;

import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.beans.Gene;
import mirna.beans.MiRna;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

public class MicroTV4Transcript extends MicroTV4 {
	
	public MicroTV4Transcript() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT(transcript_id) from %s";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		String transcript_id = rs.getString("transcript_id");
		Transcript transcript = new Transcript();
		transcript.setName(transcript_id);
		session.save(transcript);
	}
	
	public static void main(String[] args) throws Exception {
		MicroTV4Transcript microtv4 = new MicroTV4Transcript();
		microtv4.insertIntoSQLModel();
	}

}
