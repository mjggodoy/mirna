package mirna.database.reptar;

import java.sql.ResultSet;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;
import org.hibernate.Session;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class RepTarTranscript extends RepTar {

	public RepTarTranscript() throws MiRnaException {
		super();
		super.selectQuery = "SELECT distinct t1.gene_accesion" +
				" FROM mirna_raw.repTar_human t1" +
				" LEFT JOIN mirna.transcript t2" +
				" ON t1.gene_accesion = t2.id" +
				" where" + 
				" t1.pk is not null" +
				" and t2.pk is null;";
	}
	
	@Override
	public void processRow(Session session, ResultSet rs) throws Exception {

		String transcript_name = rs.getString("gene_accesion");
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcript_name);
		session.save(transcript);

	}	

	public static void main(String[] args) throws Exception {

		RepTarTranscript mirdip = new RepTarTranscript();
		mirdip.insertIntoSQLModel();

	}

}