package mirna.database.microtv4;

import java.sql.ResultSet;
import mirna.exception.MiRnaException;
import org.hibernate.Session;

public class MicroTCdsDataMiRNAInteractionData extends MicroTV4 {


	public MicroTCdsDataMiRNAInteractionData() throws MiRnaException {

		super();
		super.selectQuery = "SELECT transcript_id, gene_id, miRNA, region, chromosome, coordinates, miTG_score from %s LIMIT 10";

	}


	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {

		String transcriptId = rs.getString("transcript_id");
		String geneName = rs.getString("gene_id");
		String miRnaName = rs.getString("miRNA");

		System.out.println(transcriptId + " " + geneName + " " + miRnaName);

		String region = rs.getString("region");
		String chromosome = rs.getString("chromosome");
		String coordinates = rs.getString("coordinates");
		String miTG_score = rs.getString("miTG_score");

		System.out.println(region + " " + chromosome + " " + coordinates + " " + miTG_score);

		/*Target target = new Target();
		target.setRegion(region);
		target.setChromosome(chromosome);
		target.setCoordinates(coordinates);

		//InteractionData

		InteractionData id = new InteractionData();
		id.setMiTG_score(miTG_score);
		id.setProvenance("microT-CDS");*/

		/*// Gene
		Object oldGene = session.createCriteria(Gene.class)
				.add(Restrictions.eq("name",geneName))
				.uniqueResult();

		Gene gene = (Gene) oldGene;

		// mirna
		Object oldmiRna = session.createCriteria(MiRna.class)
				.add(Restrictions.eq("name",miRnaName ))
				.uniqueResult();

		MiRna miRna = (MiRna) oldmiRna;

		// Transcript
		Object oldTranscript = session.createCriteria(Transcript.class)
				.add(Restrictions.eq("transcriptID", transcriptId))
				.uniqueResult();

		Transcript transcript= (Transcript) oldTranscript;

		// Inserta Target
		target.setTranscript_pk(transcript.getPk());
		session.save(target);
		session.flush(); // to get the PK

		//Inserta nueva interactionData
		// (y la relaciona con el MiRna y Target correspondiente)

		id.setTarget_pk(target.getPk());
		id.setMirna_pk(miRna.getPk());
		id.setGene_pk(gene.getPk());
		session.save(id);
		 */
	}

	public static void main(String[] args) throws Exception {

		MicroTCdsDataMiRNAInteractionData o = new MicroTCdsDataMiRNAInteractionData();
		o.insertIntoSQLModel();

	}

}
