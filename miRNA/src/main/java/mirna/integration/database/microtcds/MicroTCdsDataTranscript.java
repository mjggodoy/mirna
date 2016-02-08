package mirna.integration.database.microtcds;

import java.sql.ResultSet;
import org.hibernate.Session;

import mirna.integration.exception.MiRnaException;


public class MicroTCdsDataTranscript extends MicroTCdsData {

	public MicroTCdsDataTranscript() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT(transcript_id) from %s";
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String transcriptId = rs.getString("transcript_id");
		System.out.println(transcriptId);
		//Transcript transcript = new Transcript();
		//transcript.setTranscriptID(transcriptId);
		//session.save(transcript);

	}

	public static void main(String[] args) throws Exception {

		MicroTCdsDataTranscript o = new MicroTCdsDataTranscript();
		o.insertIntoSQLModel();

	}

}
