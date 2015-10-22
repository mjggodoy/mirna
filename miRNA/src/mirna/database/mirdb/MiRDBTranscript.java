package mirna.database.mirdb;

import java.sql.ResultSet;
import org.hibernate.Session;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;


/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRDBTranscript extends MiRDB {

	public MiRDBTranscript() throws MiRnaException {
		super();
		super.selectQuery = "SELECT distinct t1.target"
				+ " FROM mirna_raw.miRDB t1"
				+ " LEFT JOIN mirna.transcript t2"
				+ " ON t1.target = t2.id"
				+ " where"
				+ " t1.pk is not null and t2.pk is null";
	}

	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		
		
		String transcript_name = rs.getString("target");
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcript_name);
		session.save(transcript);
			
	}
	
	public static void main(String[] args) throws Exception {

		MiRDBTranscript mirdb = new MiRDBTranscript();
		mirdb.insertIntoSQLModel();

	}

}