package mirna.database.mirdSNP;
import java.sql.ResultSet;
import org.hibernate.Session;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class MiRdSNP3Transcript extends MiRdSNP3 {

	public MiRdSNP3Transcript() throws MiRnaException {
		super(); 
		super.selectQuery = "SELECT distinct t1.refseq" 
				+ " FROM mirna_raw.miRdSNP3 t1"
				+ " LEFT JOIN mirna.transcript t2"
				+ " ON t1.refseq = t2.id"
				+ " where "
				+ " t1.pk is not null and t2.pk is null;";
				
	}
	
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception{

		String transcript_name = rs.getString("refseq");
		System.out.println(transcript_name);
		Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcript_name);
		session.save(transcript);
		
	}
	
	public static void main(String[] args) throws Exception {
		
		MiRdSNP3Transcript transcript = new MiRdSNP3Transcript();
		transcript.insertIntoSQLModel();
		
		
	}
	

}