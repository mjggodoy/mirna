package mirna.integration.database.microtv4;

import java.sql.ResultSet;

import org.hibernate.Session;

import mirna.integration.beans.Transcript;
import mirna.integration.exception.MiRnaException;

public class MicroTV4Transcript extends MicroTV4 {
	
	public MicroTV4Transcript() throws MiRnaException {
		super();
		super.selectQuery = "SELECT t1.transcript_id FROM mirna_raw.microtv4 t1 LEFT JOIN mirna.transcript t2 ON t1.transcript_id = t2.id where t1.pk is not null and t2.pk is null;";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		String transcript_id = rs.getString("transcript_id");
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcript_id);
		session.save(transcript);
	}
	
	public static void main(String[] args) throws Exception {
		MicroTV4Transcript microtv4 = new MicroTV4Transcript();
		microtv4.insertIntoSQLModel();
	}

}
