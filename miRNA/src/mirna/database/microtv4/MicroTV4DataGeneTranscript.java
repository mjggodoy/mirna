package mirna.database.microtv4;

import java.sql.ResultSet;
import mirna.exception.MiRnaException;
import org.hibernate.Session;


public class MicroTV4DataGeneTranscript extends MicroTV4 {

	public MicroTV4DataGeneTranscript() throws MiRnaException {
		super();
		super.selectQuery = "SELECT DISTINCT transcript_id, gene_id from %s LIMIT 10";
	}
		
	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String transcriptId = rs.getString("transcript_id");
		String geneName = rs.getString("gene_id");

		System.out.println(geneName + " " + transcriptId);
		
		/*Transcript transcript = new Transcript();
		transcript.setTranscriptID(transcriptId);

		//  Recupera su id. si ya existe
		
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name", geneName))
				.uniqueResult();
		
		Gene gene = (Gene) oldGene;
					
		transcript.setGeneId(gene.getPk());
		session.save(transcript);*/
			
	}
	

	public static void main(String[] args) throws Exception {
		
		MicroTV4DataGeneTranscript o = new MicroTV4DataGeneTranscript();
		o.insertIntoSQLModel();
		
		
		
	}

}
